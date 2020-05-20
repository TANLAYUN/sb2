package com.example.sb2.mapper;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Question;
import com.example.sb2.entity.QuestionWithBLOBs;
import com.example.sb2.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface questionMapper {

    int deleteByPrimaryKey(Integer quesId);

    //select
    Question selectByPrimaryKey(Integer quesId);

    List<Question> selectQuessByUserId(Integer userId);

    List<Question> selectAll();

    List<Question> selectAllByColNum();

    List<Question> selectAllByUser(Integer userId);

    List<Question> selectByState(Integer quesState);

    List<Question> selectByUserAndState(Integer userId, Integer quesState);

    List<Question> selectByQuesAnsState(Integer quesAnsState);

    List<Question> selectByUserAndQuesAnsState(Integer userId, Integer quesAnsState);

    //update
    @Update("update question set ques_state=#{quesState} where ques_id=#{quesId}")
    int updateQuesStateByQuesId(Integer quesId,Integer quesState);

    @Update("update question set ques_ans_state=#{quesAnsState} where ques_id=#{quesId}")
    int updateQuesAnsStateByQuesId(Integer quesId,Integer quesAnsState);

    @Update("update question set ques_col_num=#{quesColNum} where ques_id=#{quesId}")
    int updateQuesColNumByQuesId(Integer quesId,Integer quesColNum);

    @Update("update question set ques_ans_num=#{quesAnsNum} where ques_id=#{quesId}")
    int updateQuesAnsNumByQuesId(Integer quesId,Integer quesAnsNum);

    //insert
    @Insert("insert into question(user_id,ques_title,ques_content,ques_reward,ques_time) values( #{userId}, #{quesTitle}, #{quesContent}, #{quesReward}, #{quesTime} )")
    int insert(Integer userId, String quesTitle, String quesContent, Integer quesReward, String quesTime);

}