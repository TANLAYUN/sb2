package com.example.sb2.mapper;

import com.example.sb2.entity.LikeOrNot;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface likeOrNotMapper {

    @Insert("insert into likeornot(ans_id,user_id,like_state) values( #{ansId}, #{userId}, #{likeState} )")
    int insert(Integer ansId, Integer userId, Integer likeState);

    @Delete("delete from likeornot where id=#{id}")
    int delete(Integer id);

    @Update("update likeornot")
    int updateState();

//    @Select("select * from likeornot where ans_id=#{ansId} and user_id=#{userId}")
    List<LikeOrNot> selectByAnsAndUser(Integer ansId, Integer userId);

//    @Select("select * from likeornot where id=#{id}")
    List<LikeOrNot> selectByPrimaryKey(Integer id);
}
