package com.example.sb2.service.impl;

import com.example.sb2.entity.Admin;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.adminMapper;
import com.example.sb2.mapper.collectionMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private adminMapper adminmapper;
    @Autowired
    private userMapper usermapper;
    @Autowired
    private commentMapper commentmapper;

    //管理员登录
    public BaseResponse login(String mail,String pwd){

        BaseResponse baseResponse=new BaseResponse();
        Admin admin = adminmapper.selectByPrimaryKey(mail);
        System.out.println(admin);

        if(admin !=null){
            if(admin.getPwd().equals(pwd)){
                baseResponse.setData(admin);
                baseResponse.setResult(ResultCodeEnum.LOGIN_SUCCESS); // 登录成功
            } else {
                baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_PWD_ERROR); // 登录失败，账号或密码错误
            }
        } else if(admin == null){
            baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_NO_EXIST_USER); // 登录失败，用户不存在
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //修改管理员信息
    public BaseResponse modifyAdminInfo(Integer adminId, String mail, String name, String pwd,String newPwd){

        BaseResponse baseResponse=new BaseResponse();
        Admin admin = adminmapper.selectByAdminId(adminId);
        Admin admin1 = adminmapper.selectByPrimaryKey(mail);
        if(admin != null){
            if(admin.getPwd().equals(pwd)){
                //账号和密码正确
                if(admin.getMail().equals(mail)){

                    //没有修改邮箱
                    int a = adminmapper.modifyAdminInfo(adminId,mail,name,newPwd);
                    if(a == 1){
                        admin = adminmapper.selectByPrimaryKey(mail);
                        baseResponse.setData(admin);
                        baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_SUCESS); // 信息修改成功
                    }else{
                        baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_DB_UPDATE_ERROR);//信息修改失败，数据库更新错误
                    }

                }else{

                    //修改了邮箱
                    if(admin1 == null){
                        //邮箱没有人使用
                        int a = adminmapper.modifyAdminInfo(adminId,mail,name,newPwd);
                        if(a == 1){
                            admin = adminmapper.selectByPrimaryKey(mail);
                            baseResponse.setData(admin);
                            baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_SUCESS); // 信息修改成功
                        }else{
                            baseResponse.setResult(ResultCodeEnum.USER_ADD_FAILURE);
                        }
                    }else{
                        baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_USER_MAIL_EXIST);//邮箱被使用
                    }

                }

            } else {
                baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_PWD_ERROR); // 信息修改失败，密码错误
            }
        } else if(admin == null){
            baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_NO_EXIST_USER); // 信息修改失败，用户不存在
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }



}
