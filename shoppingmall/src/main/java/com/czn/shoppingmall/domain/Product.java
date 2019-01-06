package com.czn.shoppingmall.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;

    private Integer categoryId;

    private Integer storeId;

    private String name;

    private String subtitle;

    private String detail;

    private String mainImageUrl;

    private String subImagesUrl;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Product(Integer id, Integer categoryId, Integer storeId, String name, String subtitle, String detail, String mainImageUrl, String subImagesUrl, BigDecimal price, Integer stock, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.storeId = storeId;
        this.name = name;
        this.subtitle = subtitle;
        this.detail = detail;
        this.mainImageUrl = mainImageUrl;
        this.subImagesUrl = subImagesUrl;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Product() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl == null ? null : mainImageUrl.trim();
    }

    public String getSubImagesUrl() {
        return subImagesUrl;
    }

    public void setSubImagesUrl(String subImagesUrl) {
        this.subImagesUrl = subImagesUrl == null ? null : subImagesUrl.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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