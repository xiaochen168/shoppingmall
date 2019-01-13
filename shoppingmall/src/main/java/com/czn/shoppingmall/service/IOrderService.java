package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;

public interface IOrderService {

    public ServerResponse createOrder(Integer addressId, Integer buyerId);

    public ServerResponse cancel(Long orderNo, Integer buyerId);

    public ServerResponse query(Long orderNo);

    public ServerResponse list(Integer buyerId,Integer pageNum, Integer pageSize);

}
