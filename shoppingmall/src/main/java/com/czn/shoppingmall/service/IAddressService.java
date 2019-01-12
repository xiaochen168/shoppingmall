package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Address;

public interface IAddressService {

    public ServerResponse add(Address address);

    public ServerResponse update(Address address);

    public ServerResponse delete(Integer addressId, Integer role, Integer userId);

    public ServerResponse select(Integer addressId, Integer userId);

    public ServerResponse list(Integer userId);
}
