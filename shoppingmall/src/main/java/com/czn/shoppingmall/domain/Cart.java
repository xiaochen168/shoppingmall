package com.czn.shoppingmall.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Cart {
    private Integer id;

    private Integer productId;

    private Integer buyerId;

    private Integer storeId;

    private BigDecimal price;

    private Integer quantity;

    private Date createTime;

    private Date updateTime;

    public Cart(Integer id, Integer productId, Integer buyerId, Integer storeId, BigDecimal price, Integer quantity, Date createTime, Date updateTime) {
        this.id = id;
        this.productId = productId;
        this.buyerId = buyerId;
        this.storeId = storeId;
        this.price = price;
        this.quantity = quantity;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Cart() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}