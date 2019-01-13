package com.czn.shoppingmall.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartListVo {
    List<CartProductVo> cartProductVoList;
    BigDecimal cartTotalPrice;
    Integer allChecked;
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Integer getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Integer allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
