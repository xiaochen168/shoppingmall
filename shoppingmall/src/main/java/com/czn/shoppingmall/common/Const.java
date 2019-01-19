package com.czn.shoppingmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 项目用到的一些常量定义再此类中
 */
public class Const {
    /**
     * 当前用户，作为session的key，value是当前用户对象
     */
    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    // 用户常量接口,里面定义了本商城的三种用户
    public interface Role{
        int ROLE_BUYER = 0; // 买家
        int ROLE_SELLER = 1; // 卖家或者商家
        int ROLE_ADMIN = 2; // 商城的管理员
    }

    /**
     * 支付类型枚举了，目前只支持线上支付
     */
    public enum PaymentTypeEnum {
        ONLINE_PAY(1,"在线支付");

        private int code;
        private String value;
        PaymentTypeEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        public static PaymentTypeEnum codeOf(int code) {
            for (PaymentTypeEnum paymentType : values()) {
                if(paymentType.getCode() == code) {
                    return paymentType;
                }
            }
            throw new RuntimeException("未找到对于应的枚举类");
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    public interface AlipayCallBack{

        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     * 订单状态枚举类
     */
    public enum OrderStatusEnum {
        CANCELED(0,"已取消"),
        NO_PAY(10,"未付款"),
        PAID(20,"已付款"),
        SEND(30,"已发货"),
        RECEIVED(40,"收货成功"),
        CLOSE(50,"交易关闭");

        private int code;
        private String value;
        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }
        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum : values()) {
                if(orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("未找到对于应的枚举类");
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 支付平台枚举类，目前只支持支付宝
     */
    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");
        private int code;
        private String value;
        PayPlatformEnum(int code, String value){
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * 购物车商品状态 0未选中 1选中
     */
    public interface CartCheck {
        Integer CART_CHECK = 1;
        Integer CART_UNCHECK = 0;
    }

    /**
     * 商品状态
     */
    public interface ProductStatus {
        int ON_SALE = 1; // 在售
    }

    /**
     * 添加购物车数量限制
     */
    public interface CartLimit {
        Integer LIMIT_SUCCESS = 1; // 限购成功
        Integer LIMIT_FAIL = 0; // 限购失败
    }

    /**
     * 商品排序，搜索到的商品按价格升序、降序
     */
    public interface ProductOrder{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc","price_desc");
    }

}
