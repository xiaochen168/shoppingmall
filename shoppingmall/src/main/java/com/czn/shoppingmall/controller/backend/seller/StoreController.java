package com.czn.shoppingmall.controller.backend.seller;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.Store;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IStoreService;
import com.czn.shoppingmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/seller/store/")
public class StoreController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IStoreService iStoreService;

    @RequestMapping("add")
    public ServerResponse add(Integer categoryId, String storeName, String detail, HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("非商家用户,不能开店");
        }
        if (null == categoryId || StringUtils.isBlank(storeName) || StringUtils.isBlank(detail)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iStoreService.add(categoryId, storeName, detail, currentUser.getId());
    }

    @RequestMapping("store_detail")
    public ServerResponse getStoreDetail(Integer storeId, HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("非商家用户,不具有查看权限");
        }
        if (null == storeId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iStoreService.getStoreDetail(storeId, currentUser.getId());
    }

    @RequestMapping("list_store")
    public ServerResponse listStore( HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("非商家用户,不具有查看权限");
        }
        return iStoreService.listStore(currentUser.getId());
    }

    @RequestMapping("set_store_name")
    public ServerResponse setStoreName(Integer storeId, String storeName, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("非商家用户,不具有修改权限");
        }
        if (null == storeId || StringUtils.isBlank(storeName)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iStoreService.setStoreName(storeId, storeName, currentUser.getId());
    }

    @RequestMapping("close_store")
    public ServerResponse closeStore(Integer storeId, HttpSession session) {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin();
        }
        if (!iUserService.checkRole(currentUser.getRole(), Const.Role.ROLE_SELLER)) {
            return ServerResponse.createByErrorMessage("非商家用户,不能关闭店铺");
        }
        if (null == storeId) {
            return ServerResponse.createByIllegalArgument();
        }
        return iStoreService.closeStore(storeId, currentUser.getId());
    }

}
