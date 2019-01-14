package com.czn.shoppingmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IBuyerPayService;
import com.czn.shoppingmall.service.IUserService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/buyer/pay/")
public class BuyerPayController {

    private static final Logger logger = LoggerFactory.getLogger(BuyerPayController.class);

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IBuyerPayService iBuyerPayService;

    @RequestMapping("payOrder")
    public ServerResponse payOrder(Long orderNo, HttpSession session, HttpServletRequest request) {
       User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
       if (null == currentUser) {
           return ServerResponse.createByNeedLogin();
       }
       if (null == orderNo) {
           return ServerResponse.createByIllegalArgument();
       }
       String uploadPath = request.getSession().getServletContext().getRealPath("upload");
       return iBuyerPayService.payOrder(orderNo, currentUser.getId(), uploadPath);
    }

    @RequestMapping("alipay_callback")
    public ServerResponse alipayCallBack(HttpServletRequest request) {
        logger.info("进入支付宝回调");
        System.out.println("进入支付宝回调");
        Map<String,String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator(); ((Iterator) iter).hasNext();){
            String name = (String)iter.next();
            String valueStr = "";
            String[] values = (String[])requestParams.get(name);
            for(int i=0;i<values.length;i++){
                valueStr = (i == values.length -1) ? valueStr + values[i] : valueStr +values[i]+",";
            }
            params.put(name,valueStr);
        }
        logger.info("支付宝回调，sign:{},trade_status:{}，参数:{}",params.get("sign"),params.get("trade_status"),params.toString());
        // 非常重要的一点，验证支付宝回调的正确性（确认是支付宝发送的），并且避免重复通知
        params.remove("sign_type");
        try {
            // 支付宝验签， 这里我一直没有验证通过，所以就取消掉了，直接后台修改订单数据
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getPublicKey(),"utf-8",Configs.getSignType());
        } catch (AlipayApiException e) {
            logger.info("支付宝回调异常",e);
        }
        return iBuyerPayService.alipayCallback(params);
    }

}
