package com.example.sb2.mapper;

import com.example.sb2.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface commentMapper {

    int deleteByPrimaryKey(Integer comId);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer comId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    //uodate
    @Update("update comment set com_state=#{comState} where com_id=#{comId}")
    int updateComStateByComId(Integer comId, Integer comState);

    @Update("update comment set com_content=#{comContent} where com_id=#{comId}")
    int updateComByComId(Integer comId, String comContent);

    //select
    //@Select("select * from comment where ans_id={ansId}")
    List<Comment> selectComsByAnsId(Integer ansId);

    //insert
    @Insert("insert into comment(user_id,ans_id,com_content) values( #{userId}, #{ansId}, #{comContent} )")
    int insert(Integer userId, Integer ansId, String comContent);
}