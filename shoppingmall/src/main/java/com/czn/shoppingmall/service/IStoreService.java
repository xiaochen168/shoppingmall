package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;

public interface IStoreService {

    public ServerResponse add(Integer categoryId, String storeName, String detail, Integer sellerId);

    public ServerResponse setStoreName(Integer storeId, String storeName, Integer sellerId);

    public ServerResponse closeStore(Integer storeId, Integer sellerId);

    public ServerResponse getStoreDetail(Integer storeId, Integer sellerId);

    public ServerResponse listStore( Integer sellerId);


}
