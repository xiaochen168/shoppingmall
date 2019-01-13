package com.czn.shoppingmall.vo;

import com.czn.shoppingmall.domain.Address;

import java.math.BigDecimal;
import java.util.List;

public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer payType;

    private String payTypeDesc;

    private Integer postage;

    private Integer status;

    private String statusDesc;

    private String payTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    // 图片服务器网址
    private String imageHost;
    // 地址
    private Address address;
    // 订单明细
    List<OrderEntityVo> orderEntityVoList;

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderEntityVo> getOrderEntityVoList() {
        return orderEntityVoList;
    }

    public void setOrderEntityVoList(List<OrderEntityVo> orderEntityVoList) {
        this.orderEntityVoList = orderEntityVoList;
    }
}
