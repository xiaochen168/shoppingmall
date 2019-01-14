package com.czn.shoppingmall.controller.backend.seller;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IOrderService;
import com.czn.shoppingmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/seller/order/")
public class SellerOrderController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;


    @RequestMapping("send_goods")
    public ServerResponse sendGoods(Long orderNo, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (null == orderNo) {
            return ServerResponse.createByIllegalArgument();
        }
        ServerResponse response = iOrderService.setOrderStatusByOrderNo(orderNo, Const.OrderStatusEnum.SEND.getCode());
        if (response.isSuccess()) {
            return ServerResponse.createBySuccessMessage("订单发货成功");
        }
        return ServerResponse.createByErrorMessage("订单发货失败");
    }



}
