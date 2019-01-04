package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.TradeInfo;

public interface TradeInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeInfo record);

    int insertSelective(TradeInfo record);

    TradeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeInfo record);

    int updateByPrimaryKey(TradeInfo record);
}