package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.Store;

import java.util.List;

public interface StoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

    List<Store> selectBySellerId(Integer sellerId);

    List<Integer> selectStoreIdByName(String name);

    Integer selectSellerIdByStoreId(Integer storeId);

    List<Integer> selectStoreIdBySellerId(Integer sellerId);
}