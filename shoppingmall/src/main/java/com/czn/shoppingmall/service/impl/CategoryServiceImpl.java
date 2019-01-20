package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.dao.CategoryMapper;
import com.czn.shoppingmall.domain.Category;
import com.czn.shoppingmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("ICategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse add(Category category) {
        category.setStatus(1);
        int rowCount  = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加分类成功");
        }
        return ServerResponse.createByErrorMessage("添加分类失败");
    }

    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount  = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("分类名更新成功");
        }
        return ServerResponse.createByErrorMessage("分类名更新失败");
    }

    public ServerResponse getCategoryDetail(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (null == category) {
           return ServerResponse.createByErrorMessage("获取该分类的信息失败");
        }
        return ServerResponse.createBySuccessData(category);
    }

    public ServerResponse getCategoryChildByParentId(Integer parentId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            return ServerResponse.createByErrorMessage("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccessData(categoryList);
    }

    /**
     * 获取该分类下所有子类的Id
     * @param parentId
     * @return
     */
    public ServerResponse getAllChildCategoryIdByParentId(Integer parentId) {
        Set<Category> categorySet = Sets.newHashSet();
        deepCategoryIdByParentId(categorySet, parentId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(categorySet)) {
            for(Category category: categorySet){
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccessData(categoryIdList);
    }

    /**
     * 递归查找该分类下的所有子类
     * @param categorySet
     * @param categoryId
     */
    public void deepCategoryIdByParentId(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (null != category) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);
        for (int i = 0; i < categoryList.size(); i++) {
            deepCategoryIdByParentId(categorySet, categoryList.get(i).getId());
        }
    }


}
