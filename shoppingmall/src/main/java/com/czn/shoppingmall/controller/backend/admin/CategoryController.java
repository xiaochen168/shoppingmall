package com.czn.shoppingmall.controller.backend.admin;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Category;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.ICategoryService;
import com.czn.shoppingmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin/category/")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("add")
    public ServerResponse add(Category category, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createByErrorMessage("不是商城管理员,不具有添加分类权限");
        }
        return iCategoryService.add(category);
    }

    @RequestMapping("set_category_name")
    public ServerResponse setCategoryName(Integer categoryId, String categoryName, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createByErrorMessage("不是商城管理员,不具有添加分类权限");
        }
        if (StringUtils.isBlank(categoryName) || null == categoryId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCategoryService.setCategoryName(categoryId, categoryName);
    }

    @RequestMapping("get_category_detail")
    public ServerResponse getCategoryDetail(Integer categoryId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createByErrorMessage("不是商城管理员,不具有添加分类权限");
        }
        if (null == categoryId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCategoryService.getCategoryDetail(categoryId);
    }

    @RequestMapping("direct_category_child")
    public ServerResponse getCategoryChildByParentId(Integer parentId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createByErrorMessage("不是商城管理员,不具有添加分类权限");
        }
        if (null == parentId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCategoryService.getCategoryChildByParentId(parentId);
    }

    @RequestMapping("all_category_child")
    public ServerResponse getAllCategoryChildByParentId(Integer parentId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_ADMIN)) {
            return ServerResponse.createByErrorMessage("不是商城管理员,不具有添加分类权限");
        }
        if (null == parentId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iCategoryService.getAllChildCategoryIdByParentId(parentId);
    }





}
