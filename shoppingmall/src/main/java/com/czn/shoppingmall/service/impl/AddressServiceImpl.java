package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.AddressMapper;
import com.czn.shoppingmall.domain.Address;
import com.czn.shoppingmall.service.IAddressService;
import com.czn.shoppingmall.service.IUserService;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("iAddressService")
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    public ServerResponse add(Address address) {
        int rowCount = addressMapper.insert(address);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加收货地址成功");
        }
        return ServerResponse.createByErrorMessage("添加收货地址失败");
    }

    public ServerResponse select(Integer addressId, Integer userId) {
        Address address = addressMapper.selectByIdAndUserId(addressId, userId);
        if (null == address) {
            return ServerResponse.createByErrorMessage("为查询到与该用户对应的地址");
        }
        return ServerResponse.createBySuccessData(address);
    }

    public ServerResponse list(Integer userId) {
        List<Address> addressList = addressMapper.selectByUserId(userId);
        return ServerResponse.createBySuccessData(addressList);
    }

    public ServerResponse update(Address address) {
        int rowCount = addressMapper.updateByAddress(address);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("修改地址失败");
    }

    public ServerResponse delete(Integer addressId, Integer role, Integer userId) {
        int rowCount = addressMapper.deleteByIdAndRoleUserId(addressId, role, userId);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }



}
