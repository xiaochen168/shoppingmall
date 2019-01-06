package com.czn.shoppingmall.controller.backend.seller;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/seller/")
public class SellerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login")
    public ServerResponse login(String username, String password, HttpSession session){
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return ServerResponse.createByIllegalArgument();
        }
        ServerResponse response = iUserService.login(username, password);
        if (!response.isSuccess()) {
            return response;
        }
        User user = (User)response.getData();
        if (iUserService.checkRole(user.getRole(), Const.Role.ROLE_SELLER)) {
            // 用户登陆成功，将其信息放入session中
            session.setAttribute(Const.CURRENT_USER, user);
            return response;
        }
        return ServerResponse.createByErrorMessage("非商家用户");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse register(User user){
        if (null == user) {
            return ServerResponse.createByIllegalArgument();
        }
        user.setRole(Const.Role.ROLE_SELLER);
        return iUserService.register(user);
    }

    @RequestMapping(value = "update_information", method = RequestMethod.POST)
    public ServerResponse update(User user,HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin("用户未登录,请先登陆");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        user.setRole(currentUser.getRole());
        ServerResponse response = iUserService.update(user);
        if (!response.isSuccess()) {
            return response;
        }
        // 更新session中存储的个人信息
        User updateUser = (User)response.getData();
        updateUser.setUsername(currentUser.getUsername());
        updateUser.setRole(currentUser.getRole());
        session.setAttribute(Const.CURRENT_USER, updateUser);
        return response;
    }

    @RequestMapping("get_information")
    public ServerResponse getUserInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin("用户未登录,请先登陆");
        }
        return iUserService.getUserInformation(currentUser.getId());
    }

}
