package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.common.Const;
import com.czn.shoppingmall.common.ServerResponse;
import com.czn.shoppingmall.common.TokenCache;
import com.czn.shoppingmall.dao.UserMapper;
import com.czn.shoppingmall.domain.User;
import com.czn.shoppingmall.service.IUserService;
import com.czn.shoppingmall.util.EncryptUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Service("iUserService")
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse login(String username, String password){
        int rowCount = userMapper.checkExistByUsername(username);
        if (rowCount <= 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String encryptPassword = EncryptUtil.encoderUTF8(password);
        User user = userMapper.selectLogin(username, encryptPassword);
        if (null == user) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        // 用户密码属于敏感数据,不能返回给前端，要进行脱敏或者直接置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessData(user);
    }

    public ServerResponse register(User user){
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setPassword(EncryptUtil.encoderUTF8(user.getPassword()));
        int rowCount = userMapper.insert(user);
        if(rowCount > 0) {
            return ServerResponse.createBySuccessMessage("注册成功");
        }
        return ServerResponse.createByErrorMessage("注册失败，请检查");
    }

    public ServerResponse checkValid(String loginName, String loginType) {
        if (StringUtils.isBlank(loginType)) {
            return ServerResponse.createByErrorMessage("未设置校验类型");
        }
        if (Const.USERNAME.equals(loginType)) {
            int rowCount = userMapper.checkExistByUsername(loginName);
            if (rowCount > 0) {
                return ServerResponse.createByErrorMessage("用户名已存在");
            }
        }
        if (Const.EMAIL.equals(loginType)) {
            int rowCount = userMapper.checkExistByEmail(loginName);
            if (rowCount > 0) {
                return ServerResponse.createByErrorMessage("邮箱已存在");
            }
        }
        return ServerResponse.createBySuccess();
    }

    public ServerResponse update(User user) {
        ServerResponse validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("更新失败,该邮箱已经被人使用");
        }
        // 注意 用户名 密码 角色 不能更改
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setRealname(user.getRealname());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessageAndData("个人信息修改成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("个人信息修改失败");
    }

    public ServerResponse getUserInformation(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return ServerResponse.createByErrorMessage("找不到该用户的个人信息");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessData(user);
    }

    public ServerResponse<String> forgetGetQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)) {
            return ServerResponse.createByErrorMessage("该用户未设置密码找回问题");
        }
        return ServerResponse.createBySuccessMessage(question);
    }

    public ServerResponse checkAnswer(String username, String question, String answer) {
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        int rowCount  = userMapper.checkAnswer(username, question, answer);
        if (rowCount > 0) {
            Map resultMap = Maps.newHashMap();
            String answerToken = UUID.randomUUID().toString();
            // token令牌返回给用户
            resultMap.put("answerToken",answerToken);

            // 把token令牌放进本地缓存里面
            String tokenKey = TokenCache.TOKEN_PREFIX + username;
            TokenCache.setToken(tokenKey,answerToken);
            return ServerResponse.createBySuccessData(resultMap);
        }
        return ServerResponse.createByErrorMessage("密码找回答案回答错误");
    }

    public ServerResponse resetPasswordByToken(String username, String newPassword, String token) {
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String cacheToken = TokenCache.getToken(TokenCache.TOKEN_PREFIX+username);
        if (StringUtils.isBlank(cacheToken)) {
            return ServerResponse.createByErrorMessage("token无效或过期");
        }
        if (!StringUtils.equals(token,cacheToken)) {
            return ServerResponse.createByErrorMessage("token错误,请重新获取token");
        }
        String newEncryptPassword = EncryptUtil.encoderUTF8(newPassword);
        int rowCount  = userMapper.updatePasswordByUsername(username, newEncryptPassword);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("重置密码成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }

    public ServerResponse resetPassword(String password, String newPassword, Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return ServerResponse.createByErrorMessage("无法找到当前用户的个人信息");
        }
        String encryptPassword = EncryptUtil.encoderUTF8(password);
        if (!StringUtils.equals(encryptPassword,user.getPassword())) {
            return ServerResponse.createByErrorMessage("原密码错误");
        }
        String newEncryptPassword = EncryptUtil.encoderUTF8(newPassword);
        int rowCount = userMapper.updatePasswordByUsername(user.getUsername(),newEncryptPassword);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("重置密码成功");
        }
        return ServerResponse.createByErrorMessage("重置密码失败");
    }

    public boolean checkRole(int userRoleType, int roleType) {
        return userRoleType == roleType;
    }


}
