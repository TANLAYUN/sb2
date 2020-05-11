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

    //@Select("select * from user where user_id=#{userId}")
    User selectByUserId(Integer userId);

    List<User> selectAll();

    List<User> selectByState(Integer userState);

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
//    @Update("update admin set mail=#{mail},name=#{name},pwd=#{newPwd} where user_id=#{userId}")
    int modifyUserInfo(Integer userId, String mail, String name, String newPwd);

    int deleteByPrimaryKey(String mail);

    int insert(String mail, String name, String pwd, String addTime);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


}