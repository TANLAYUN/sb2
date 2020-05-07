package com.example.sb2.mapper;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface answerMapper {

    int deleteByPrimaryKey(Integer ansId);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer ansId);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKeyWithBLOBs(Answer record);

    int updateByPrimaryKey(Answer record);

    //select
    //@Select("select * from answer where ques_id=#{quesId}")
    List<Answer> selectAnssByQuesId(Integer quesId);

    List<Answer> selectAnssByUserId(Integer userId);

    @Select("select user_Id from answer where ans_id=#{ansId}")
    int selectUserIdByAnsId(Integer ansId);

    //update
    @Update("update answer set ans_state=#{ansState} where ans_id=#{ansId}")
    int updateAnsStateByAnsId(Integer ansId, Integer ansState);

    @Update("update answer set ans_content=#{ansContent} where ans_id=#{ansId}")
    int updateAnsByAnsId(Integer ansId, String ansContent);

    //insert
    @Insert("insert into answer(user_id,ques_id,ans_content,ans_time) values( #{userId}, #{quesId}, #{ansContent},#{ansTime} )")
    int insert(Integer userId, Integer quesId, String ansContent,String ansTime);

}