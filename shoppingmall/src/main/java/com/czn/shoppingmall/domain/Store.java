package com.czn.shoppingmall.domain;

import java.util.Date;

public class Store {
    private Integer id;

    private Integer userId;

    private Integer categoryId;

    private String storeName;

    private Integer status;

    private String desc;

    private Date createTime;

    private Date updateTime;

    public Store(Integer id, Integer userId, Integer categoryId, String storeName, Integer status, String desc, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.storeName = storeName;
        this.status = status;
        this.desc = desc;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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