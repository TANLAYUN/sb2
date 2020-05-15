package com.example.sb2.mapper;

import com.example.sb2.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface adminMapper {

    //select
    Admin selectByPrimaryKey(String mail);

    //@Select("select * from admin where admin_id=#{adminId}")
    Admin selectByAdminId(Integer adminId);

    int deleteByPrimaryKey(String mail);

    int insert(Admin record);

    int insertSelective(Admin record);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    //update
    //修改管理员信息
    @Update("update admin set mail=#{mail},name=#{name},pwd=#{newPwd} where admin_id=#{adminId}")
    int modifyAdminInfo(Integer adminId, String mail, String name, String newPwd);

    //上传管理员头像
    @Update("update user set image=#{image} where user_id=#{adminId}")
    int upload(Integer adminId, String image);
}