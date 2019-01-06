package com.czn.shoppingmall.service;

import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.domain.User;

public interface IUserService {

    public ServerResponse login(String username, String password);

    public ServerResponse register(User user);

    public boolean checkRole(int userRoleType, int roleType);

    public ServerResponse update(User user);

    public ServerResponse getUserInformation(Integer userId);

    public ServerResponse checkValid(String loginName, String loginType);

    public ServerResponse<String> forgetGetQuestion(String username);

    public ServerResponse checkAnswer(String username, String question, String answer);

    public ServerResponse resetPasswordByToken(String username, String newPassword, String token);

    public ServerResponse resetPassword(String password, String newPassword, Integer userId);


}
