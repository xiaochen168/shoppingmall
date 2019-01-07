package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.CategoryMapper;
import com.czn.shoppingmall.dao.StoreMapper;
import com.czn.shoppingmall.domain.Category;
import com.czn.shoppingmall.domain.Store;
import com.czn.shoppingmall.service.IStoreService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service("iStoreService")
public class StoreServiceImpl implements IStoreService {

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse add(Integer categoryId, String storeName, String detail, Integer sellerId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (null == category) {
            return ServerResponse.createByErrorMessage("添加分类不存在");
        }
        Store store = new Store();
        store.setSellerId(sellerId);
        store.setCategoryId(categoryId);
        store.setStoreName(storeName);
        store.setStatus(1);
        store.setDetail(detail);
        int rowCount = storeMapper.insert(store);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("开店成功,好运连连，祝你生意兴隆");
        }
        return ServerResponse.createByErrorMessage("开店失败");
    }

    public ServerResponse setStoreName(Integer storeId, String storeName, Integer sellerId) {
        // 检验一下店铺是否存在
        Store store = storeMapper.selectByPrimaryKey(storeId);
        if (null == store) {
            return ServerResponse.createByErrorMessage("店铺不存在");
        }
        // 检查店铺的属主是不是当前用户
        if (sellerId != store.getSellerId()) {
            return ServerResponse.createByErrorMessage("不是您的用户,不具有修改权限");
        }
        store.setStoreName(storeName);
        int rowCount = storeMapper.updateByPrimaryKeySelective(store);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("店铺名修改成功");
        }
        return ServerResponse.createByErrorMessage("店铺名修改失败");
    }

    public ServerResponse closeStore(Integer storeId, Integer sellerId) {
        // 检验一下店铺是否存在
        Store store = storeMapper.selectByPrimaryKey(storeId);
        if (null == store) {
            return ServerResponse.createByErrorMessage("店铺不存在");
        }
        // 检查店铺的属主是不是当前用户
        if (sellerId != store.getSellerId()) {
            return ServerResponse.createByErrorMessage("不是您的店铺,不具有修改权限");
        }
        store.setStatus(0);
        int rowCount = storeMapper.updateByPrimaryKeySelective(store);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("店铺关闭成功");
        }
        return ServerResponse.createByErrorMessage("店铺关闭失败");
    }

    public ServerResponse getStoreDetail(Integer storeId, Integer sellerId) {
        // 检验一下店铺是否存在
        Store store = storeMapper.selectByPrimaryKey(storeId);
        if (null == store) {
            return ServerResponse.createByErrorMessage("店铺不存在");
        }
        // 检查店铺的属主是不是当前用户
        if (sellerId != store.getSellerId()) {
            return ServerResponse.createByErrorMessage("不是您的店铺,不具有查阅权限");
        }
        return ServerResponse.createBySuccessData(store);
    }

    public ServerResponse listStore( Integer sellerId) {
        List<Store> storeList = storeMapper.selectBySellerId(sellerId);
        if (CollectionUtils.isNotEmpty(storeList)) {
            return ServerResponse.createBySuccessData(storeList);
        }
        return ServerResponse.createBySuccessData(Lists.newArrayList());
    }
}
