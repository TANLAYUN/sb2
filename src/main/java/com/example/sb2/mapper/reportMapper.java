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
    //insert
    @Insert("insert into report(report_user_id,report_type,report_type_id,reported_user_id,report_content) values( #{reportUserId}, #{reportType}, #{reportTypeId}, #{reportedUserId}, #{reportContent} )")
    int insert(Integer reportUserId, Integer reportType, Integer report_type_id, Integer reportedUserId, String reportContent);

    //select
    Report selectByPrimaryKey(Integer reportId);

    //管理员
    List<Report> selectAll();

    List<Report> selectByType(Integer reportType);

    List<Report> selectByState(Integer reportState);

    List<Report> selectByTypeAndState(Integer reportType, Integer reportState);

    //用户
    List<Report> selectAllByUser(Integer reportUserId);

    List<Report> selectByTypeByUser(Integer reportUserId, Integer reportType);

    List<Report> selectByStateByUser(Integer reportUserId, Integer reportState);

    List<Report> selectByTypeAndStateByUser(Integer reportUserId, Integer reportType, Integer reportState);


    //被举报用户
    List<Report> selectAllByReportedUser(Integer reportedUserId);

    List<Report> selectByTypeByReportedUser(Integer reportedUserId, Integer reportType);

    List<Report> selectByStateByReportedUser(Integer reportedUserId, Integer reportState);

    List<Report> selectByTypeAndStateByReportedUser(Integer reportedUserId, Integer reportType, Integer reportState);
}
