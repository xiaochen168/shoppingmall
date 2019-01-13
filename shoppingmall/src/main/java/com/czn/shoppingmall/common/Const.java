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

    public interface CartCheck {
        Integer CART_CHECK = 1;
        Integer CART_UNCHECK = 0;
    }

    public interface ProductStatus {
        int ON_SALE = 0; // 在售
    }

    public interface CartLimit {
        Integer LIMIT_SUCCESS = 1; // 限购成功
        Integer LIMIT_FAIL = 0; // 限购失败
    }

    public interface ProductOrder{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc","price_desc");
    }


}
