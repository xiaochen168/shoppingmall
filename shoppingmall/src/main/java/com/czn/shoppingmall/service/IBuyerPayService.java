package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;

import java.util.Map;

public interface  IBuyerPayService {

    public ServerResponse payOrder(Long orderNo, Integer buyerId, String uploadPath);

    public ServerResponse alipayCallback(Map<String,String> params);

}
