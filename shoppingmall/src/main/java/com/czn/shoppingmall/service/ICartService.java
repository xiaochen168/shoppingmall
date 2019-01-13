package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;

public interface ICartService {
    public ServerResponse add(Integer productId, Integer count, Integer buyerId);

    public ServerResponse delete(Integer cartId, Integer buyerId);

    public ServerResponse list(Integer buyerId);

    public ServerResponse setCheckStatus(Integer cartId, Integer buyerId, Integer checkStatus);

    public ServerResponse setAllCheckStatus(Integer buyerId, Integer checkStatus);

}
