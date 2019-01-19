package com.czn.shoppingmall.controller.portal;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/buyer/")
public class BuyerController {

    @Autowired
    private IUserService iUserService;

    /**
     * 登陆接口
     * @param username
     * @param password
     * @param session
     * @return
     */
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
        if (iUserService.checkRole(user.getRole(), Const.Role.ROLE_BUYER)) {
            // 用户登陆成功，将其信息放入session中
            session.setAttribute(Const.CURRENT_USER, user);
            return response;
        }
        return ServerResponse.createByErrorMessage("非买家用户");
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ServerResponse register(User user){
        if (null == user) {
            return ServerResponse.createByIllegalArgument();
        }
        user.setRole(Const.Role.ROLE_BUYER);
        return iUserService.register(user);
    }

    /**
     * 更新个人信息接口
     * @param user
     * @param session
     * @return
     */
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

    /**
     * 获取个人信息接口
     * @param session
     * @return
     */
    @RequestMapping("get_information")
    public ServerResponse getUserInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin("用户未登录,请先登陆");
        }
        return iUserService.getUserInformation(currentUser.getId());
    }

    /**
     * 接茬用户名/邮箱接口，检查是否已经被人使用注册过
     * @param loginName
     * @param loginType
     * @return
     */
    @RequestMapping("check_valid")
    public ServerResponse checkValid(String loginName, String loginType) {
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(loginType)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iUserService.checkValid(loginName, loginType);
    }

    @RequestMapping("get_question")
    public ServerResponse forgetGetQuestion(String username) {
        if (StringUtils.isBlank(username)) {
           return ServerResponse.createByIllegalArgument();
        }
        return iUserService.forgetGetQuestion(username);
    }

    @RequestMapping("check_answer")
    public ServerResponse checkAnswer(String username, String question, String answer) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(question) || StringUtils.isBlank(answer)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iUserService.checkAnswer(username, question, answer);
    }

    @RequestMapping("forget_reset_password")
    public ServerResponse resetPasswordByToken(String username, String newPassword, String token) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(token)) {
            return ServerResponse.createByIllegalArgument();
        }
        return iUserService.resetPasswordByToken(username, newPassword, token);
    }

    @RequestMapping("reset_password")
    public ServerResponse resetPassword(String password, String newPassword,HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByNeedLogin("用户未登录,请先登陆");
        }
        return iUserService.resetPassword(password, newPassword, currentUser.getId());
    }




}
