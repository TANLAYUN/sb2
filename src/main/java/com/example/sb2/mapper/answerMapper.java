package com.example.sb2.mapper;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Comment;
import com.example.sb2.entity.Question;
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

    List<Answer> sortByGoodCount(Integer quesId);

    List<Answer> selectAnssByUserId(Integer userId);

    List<Answer> selectAll();

    List<Answer> selectByState(Integer ansState);

    List<Answer> selectByUserAndState(Integer userId, Integer ansState);

    List<Answer> selectByQuesAndRead(Integer quesId);

    @Select("select user_Id from answer where ans_id=#{ansId}")
    int selectUserIdByAnsId(Integer ansId);

    //update
    @Update("update answer set ans_state=#{ansState} where ans_id=#{ansId}")
    int updateAnsStateByAnsId(Integer ansId, Integer ansState);

    @Update("update answer set ans_com_num=#{ansComNum} where ans_id=#{ansId}")
    int updateAnsComNumByAnsId(Integer ansId, Integer ansComNum);

    @Update("update answer set ans_content=#{ansContent} where ans_id=#{ansId}")
    int updateAnsByAnsId(Integer ansId, String ansContent);

    @Update("update answer set good_count=#{goodCount} where ans_id=#{ansId}")
    int updateGoodByAnsId(Integer ansId, Integer goodCount);

    @Update("update answer set bad_count=#{badCount} where ans_id=#{ansId}")
    int updateBadByAnsId(Integer ansId, Integer badCount);

    @Update("update answer set best_answer=1 where ans_id=#{ansId}")
    int updateBestAns(Integer ansId);

    @Update("update answer set is_read = 1 where ans_id=#{ansId}")
    int readAnswer(Integer ansId);

    //insert
    @Insert("insert into answer(user_id,ques_id,ans_content,ans_time) values( #{userId}, #{quesId}, #{ansContent},#{ansTime} )")
    int insert(Integer userId, Integer quesId, String ansContent,String ansTime);

}