package com.example.sb2.mapper;

import com.example.sb2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface userMapper {

    //select
    User selectByPrimaryKey(String mail);

    User selectByUserId(Integer userId);

    List<User> selectAll();

    List<User> selectByState(Integer userState);

    List<User> selectNumByString(String string);
    //update
    //修改用户状态
    @Update("update user set state=#{userState} where user_id=#{userId}")
    int modifyUserState(Integer userId, Integer userState);

    //修改用户的capital
    @Update("update user set capital=#{capital} where user_id=#{userId}")
    int updateUserCapital(Integer userId,Integer capital);

    //上传用户头像
    @Update("update user set image=#{image} where user_id=#{userId}")
    int upload(Integer userId, String image);

    //修改用户信息
    int modifyUserInfo(Integer userId, String mail, String name, String newPwd);

    @Update("update user set report_num = #{reportNum} where user_id = #{userId}")
    int updateReportNum(Integer userId, Integer reportNum);

    @Update("update user set pwd = #{pwd} where mail = #{mail}")
    int updatePwd(String mail, String pwd);

    //delete
    int deleteByPrimaryKey(String mail);


    //insert
    int insert(String mail, String name, String pwd, String addTime);

}