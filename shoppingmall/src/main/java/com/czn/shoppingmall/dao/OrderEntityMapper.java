package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.OrderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    OrderEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);

    int batchInsert(@Param("orderEntityList") List<OrderEntity> orderEntityList);

    List<OrderEntity> selectByOrderNo(Long orderNo);
}