package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int cancelOrderByOrderNoAndBuyerId(@Param("orderNo") Long order, @Param("buyerId") Integer buyerId, @Param("status") Integer status);

    Order selectByOrderNoAndBuyerId(@Param("orderNo") Long order, @Param("buyerId") Integer buyerId);

    Order selectByOrderNo(Long orderNo);

    List<Order> selectByBuyerId(Integer buyerId);
    
}