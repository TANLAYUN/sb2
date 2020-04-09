package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface AdminService {
     //登录
     BaseResponse login(String mail, String pwd);

     //修改信息
     BaseResponse modifyAdminInfo(Integer adminId, String mail, String name, String pwd,String newPwd);



}
