package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByNameAndCategoryIdList(@Param("name") String name,@Param("categoryIdList") List<Integer> categoryIdList);

    List<Product> selectByStoreIdList(@Param("storeIdList") List<Integer> storeIdList);

}