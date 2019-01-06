package com.czn.shoppingmall.controller.backend.admin;

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
@RequestMapping("/admin/")
public class AdminController {

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
        if (iUserService.checkRole(user.getRole(), Const.Role.ROLE_ADMIN)) {
            // 用户登陆成功，将其信息放入session中
            session.setAttribute(Const.CURRENT_USER, user);
            return response;
        }
        return ServerResponse.createByErrorMessage("非管理员用户");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse register(User user){
        if (null == user) {
            return ServerResponse.createByIllegalArgument();
        }
        user.setRole(Const.Role.ROLE_ADMIN);
        return iUserService.register(user);
    }
}
