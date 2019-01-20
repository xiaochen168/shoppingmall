package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Category;

public interface ICategoryService {

    public ServerResponse add(Category category);

    public ServerResponse setCategoryName(Integer categoryId, String categoryName);

    public ServerResponse getCategoryDetail(Integer categoryId);

    public ServerResponse getCategoryChildByParentId(Integer parentId);

    public ServerResponse getAllChildCategoryIdByParentId(Integer parentId);

}
