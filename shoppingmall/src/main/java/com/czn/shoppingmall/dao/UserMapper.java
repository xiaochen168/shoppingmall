package com.czn.shoppingmall.dao;

import com.czn.shoppingmall.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkExistByUsername(String username);

    int checkExistByEmail(String email);

    User selectLogin(@Param("username") String username,@Param("password") String password);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}