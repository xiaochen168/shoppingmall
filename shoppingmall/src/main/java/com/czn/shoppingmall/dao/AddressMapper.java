package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    Address selectByIdAndUserId(@Param("addressId") Integer addressId, @Param("userId") Integer userId);

    List<Address> selectByUserId(Integer userId);

    int updateByAddress(Address record);

    int deleteByIdAndRoleUserId(@Param("addressId") Integer addressId,@Param("role") Integer role,@Param("userId") Integer userId);
}