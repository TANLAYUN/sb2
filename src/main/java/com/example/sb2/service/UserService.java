package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

import java.text.ParseException;

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

    //上传用户图片
    BaseResponse upload(Integer userId, String image);

    //收到的回答
    BaseResponse searchAnswerInfo(Integer userId);

    //收到的评论
    BaseResponse searchCommentInfo(Integer userId);

    //根据年数查看每月注册人数
    BaseResponse viewRegnumByYear(String year);

    //日期查看一周的注册人数
    BaseResponse viewRegnumByDate(String date) throws ParseException;
}
