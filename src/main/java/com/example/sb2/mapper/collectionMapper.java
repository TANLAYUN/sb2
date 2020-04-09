package com.example.sb2.mapper;

import com.example.sb2.entity.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface collectionMapper {
    //select
    Collection selectByPrimaryKey(Integer colId);

//    @Select("select * from collection where col_user_id=#{colUserId} and col_ques_id=#{colQuesId}")
    Collection selectByUserIdAndQuesId(Integer colUserId, Integer colQuesId);

    List<Collection> selectByColUserId(Integer colUserId);

    //insert
    @Insert("insert into collection(col_user_id,col_ques_id) values(#{colUserId}, #{colQuesId})")
    int insert(Integer colUserId,Integer colQuesId);

    //delete
    @Delete("delete from collection where col_id=#{colId}")
    int deleteByPrimaryKey(Integer colId);
}
