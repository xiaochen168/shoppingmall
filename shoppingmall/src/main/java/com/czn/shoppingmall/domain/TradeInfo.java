package com.czn.shoppingmall.domain;

import java.util.Date;

public class TradeInfo {
    private Integer id;

    private Integer buyerId;

    private Integer sellerId;

    private Integer tradePlatform;

    private String tradeNumber;

    private Integer tradeStatus;

    private Date createTime;

    private Date updateTime;

    public TradeInfo(Integer id, Integer buyerId, Integer sellerId, Integer tradePlatform, String tradeNumber, Integer tradeStatus, Date createTime, Date updateTime) {
        this.id = id;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.tradePlatform = tradePlatform;
        this.tradeNumber = tradeNumber;
        this.tradeStatus = tradeStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TradeInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getTradePlatform() {
        return tradePlatform;
    }

    public void setTradePlatform(Integer tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber == null ? null : tradeNumber.trim();
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
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