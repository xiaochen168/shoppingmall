package com.czn.shoppingmall.service.impl;



import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.OrderEntityMapper;
import com.czn.shoppingmall.dao.OrderMapper;
import com.czn.shoppingmall.dao.TradeInfoMapper;
import com.czn.shoppingmall.domain.Order;
import com.czn.shoppingmall.domain.OrderEntity;
import com.czn.shoppingmall.domain.TradeInfo;
import com.czn.shoppingmall.service.IBuyerPayService;
import com.czn.shoppingmall.service.IFtpService;
import com.czn.shoppingmall.util.BigDecimalUtil;
import com.czn.shoppingmall.util.DateTimeUtil;
import com.czn.shoppingmall.util.PropertiesUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("iBuyerPayService")
public class BuyerPayServiceImpl implements IBuyerPayService {

    Logger logger = LoggerFactory.getLogger(BuyerPayServiceImpl.class);
    /**
     *  支付宝交易服务
     */
    private static AlipayTradeService tradeService;
    static{
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");
        /** 使用Configs提供的默认参数
         AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderEntityMapper orderEntityMapper;
    @Autowired
    private IFtpService iFtpService;
    @Autowired
    private TradeInfoMapper tradeInfoMapper;

    public ServerResponse payOrder(Long orderNo, Integer buyerId, String uploadPath) {
        Map<String,String> resultMap = Maps.newHashMap();
        Order order = orderMapper.selectByOrderNoAndBuyerId(orderNo, buyerId);
        if (null == order) {
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        resultMap.put("orderNo",orderNo.toString());
        // (必填)商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线
        String outTradeNo = orderNo.toString();
        // (必填)订单标题，粗略描述订单支付的信息
        String subject = "shoppingmall 商城订单，支付宝在线支付，订单号：" + orderNo.toString();
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(order.getPayment()).append("元").toString();
        // (必填)订单的金额
        String totalAmount = order.getPayment().toString();
        // (付款条码)
        String authCode = "用户自己的支付宝付款码";
        // 不可折扣金额
        String unDiscountAmount = "0.0";
        // 商家Id
        String sellerId = "";
        // 店铺Id
        String storeId = "test_store_id";
        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "120m";
        List<OrderEntity> orderEntityList = orderEntityMapper.selectByOrderNo(orderNo);
        // 给支付宝发送的商品信息List
        List<GoodsDetail> goodsDetailList = Lists.newArrayList();
        for(OrderEntity orderEntity : orderEntityList){
            GoodsDetail goodsDetail = GoodsDetail.newInstance(orderEntity.getId().toString(),orderEntity.getProductName(),
                    BigDecimalUtil.mul(new BigDecimal(orderEntity.getPrice().toString()).doubleValue(),new BigDecimal("100").doubleValue()).longValue(),orderEntity.getQuantity());
            goodsDetailList.add(goodsDetail);
        }
        String appAuthToken = "应用授权令牌";//根据真实值填写

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(unDiscountAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                // 打印支付宝基本响应信息
                dumpResponse(response);
                File folder = new File(uploadPath);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }
                // 需要修改为运行机器上的路径
                String filePath = String.format(uploadPath+"/qr-%s.png", response.getOutTradeNo());
                String fileName = String.format("qr-%s.png", response.getOutTradeNo());
                logger.info("filePath:" + filePath);
                logger.info("fileName"+fileName);
                // 持久化二维码到本地
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                File targetFile = new File(uploadPath,fileName);
                List<File> fileList = Lists.newArrayList(targetFile);
                try {
                    // 上传二维码到FTP服务器
                    iFtpService.upload(fileList);
                    resultMap.put("qrUrl",PropertiesUtil.getProperty("ftp.server.http.prefix")+fileName);
                } catch (IOException e) {
                    logger.info("上传二维码失败",e);
                }
                return ServerResponse.createBySuccessData(resultMap);
            case FAILED:
                logger.error("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
        }
        return ServerResponse.createByErrorMessage("不支持的交易状态,交易返回异常");
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }

    public ServerResponse alipayCallback(Map<String,String> params){
        Long orderNo = Long.parseLong(params.get("out_trade_no"));
        String tradeNo = params.get("trade_no");
        String tradeStatus = params.get("trade_status");
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(null == order){
            return ServerResponse.createByErrorMessage("非本商城订单,请忽略");
        }
        if(order.getStatus() >= Const.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccessMessage("支付宝重复调用");
        }
        if(Const.AlipayCallBack.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
            order.setPayTime(DateTimeUtil.strToDate(params.get("gmt_payment")));
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKey(order);
        }
        OrderEntity orderEntity = orderEntityMapper.selectOneByOrderNo(orderNo);
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setOrderNo(orderNo);
        tradeInfo.setBuyerId(order.getBuyerId());
        tradeInfo.setSellerId(orderEntity.getSellerId());
        tradeInfo.setTradePlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        tradeInfo.setTradeNumber(tradeNo);
        tradeInfo.setTradeStatus(tradeStatus);
        tradeInfoMapper.insert(tradeInfo);
        return ServerResponse.createBySuccess();
    }




}
