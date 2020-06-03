package com.example.sb2.mapper;

import com.example.sb2.entity.Report;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface reportMapper {
    @Insert("insert into report(report_user_id,report_type,reported_user_id,report_content) values( #{reportUserId}, #{reportType}, #{reportedUserId}, #{reportContent} )")
    int insert(Integer reportUserId, Integer reportType, Integer reportedUserId, String reportContent);
}
