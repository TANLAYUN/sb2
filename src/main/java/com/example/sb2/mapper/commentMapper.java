package com.example.sb2.mapper;

import com.example.sb2.entity.Answer;
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

    Comment selectByPrimaryKey(Integer comId);


    //uodate
    @Update("update comment set com_state=#{comState} where com_id=#{comId}")
    int updateComStateByComId(Integer comId, Integer comState);

    @Update("update comment set com_content=#{comContent} where com_id=#{comId}")
    int updateComByComId(Integer comId, String comContent);

    @Update("update comment set is_read = 1 where com_id=#{comId}")
    int readComment(Integer comId);

    //select
    //@Select("select * from comment where ans_id={ansId}")
    List<Comment> selectComsByAnsId(Integer ansId);

    List<Comment> selectComsByUserId(Integer userId);

    List<Comment> selectAll();

    List<Comment> selectByState(Integer comState);

    List<Comment> selectByUserAndState(Integer userId, Integer comState);

    List<Comment> selectByAnsAndRead(Integer ansId);

    List<Comment> selectByAnsComAndRead(Integer ansComId);
    //insert
    @Insert("insert into comment(user_id,ans_id,com_content,ans_com_id) values( #{userId}, #{ansId}, #{comContent}, #{ansComId} )")
    int insert(Integer userId, Integer ansId, String comContent, Integer ansComId);
}