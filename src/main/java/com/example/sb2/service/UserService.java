package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface UserService {
    //修改用户状态
    BaseResponse modifyUserState(Integer userId, Integer userState);

    //根据用户Id选取用户
    BaseResponse searchUserByUserId(Integer userId);

    //根据状态选取用户
    BaseResponse searchUsersByState(Integer userState);

    //用户注册
    BaseResponse register(String mail, String name,String pwd);

    //用户登录
    BaseResponse login(String mail, String pwd);

    //修改信息
    BaseResponse modifyUserInfo(Integer userId, String mail, String name, String pwd,String newPwd);

    //查看用户详细信息
    BaseResponse viewUserInfo(String mail);
}
