package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    Cart selectByProductIdAndBuyerId(@Param("productId") Integer productId, @Param("buyerId") Integer buyerId);

    int deleteByCartIdAndBuyerId(@Param("cartId") Integer cartId, @Param("buyerId") Integer buyerId);

    List<Cart> selectByBuyerId(Integer buyerId);

    int setCheckStatus(@Param("cartId")Integer cartId,@Param("buyerId") Integer buyerId,@Param("checkStatus") Integer checkStatus);

    int setAllCheckStatus(@Param("buyerId") Integer buyerId,@Param("checkStatus") Integer checkStatus);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}