package com.czn.shoppingmall.domain;

import java.util.Date;

public class Store {
    private Integer id;

    private Integer sellerId;

    private Integer categoryId;

    private String storeName;

    private Integer status;

    private String detail;

    private Date createTime;

    private Date updateTime;

    public Store(Integer id, Integer sellerId, Integer categoryId, String storeName, Integer status, String detail, Date createTime, Date updateTime) {
        this.id = id;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.storeName = storeName;
        this.status = status;
        this.detail = detail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Store() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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