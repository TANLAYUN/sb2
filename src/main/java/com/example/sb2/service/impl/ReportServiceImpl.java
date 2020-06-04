package com.example.sb2.service.impl;

import com.example.sb2.entity.*;
import com.example.sb2.entity.Question;
import com.example.sb2.entity.Report;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.answerMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.questionMapper;
import com.example.sb2.mapper.reportMapper;
import com.example.sb2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private reportMapper reportmapper;
    @Autowired
    private questionMapper questionmapper;
    @Autowired
    private answerMapper answermapper;
    @Autowired
    private commentMapper commentmapper;

    //用户举报
    public BaseResponse report(Integer reportUserId, Integer reportType,  Integer reportTypeId, Integer reportedUserId, String reportContent){
        BaseResponse baseResponse = new BaseResponse();
        //获取系统时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String reportTime = dateFormat.format(now);
        int a = reportmapper.insert(reportUserId,reportType,reportTypeId,reportedUserId,reportContent,reportTime);
        if( a == 1 ){
            baseResponse.setResult(ResultCodeEnum.REPORT_ADD_SUCCESS);
        }else if( a != 1){
            baseResponse.setResult(ResultCodeEnum.REPORT_ADD_FAILURE);
        }
        return baseResponse;
    }

    //根据举报类型和处理状态选择举报_管理员
    public BaseResponse searchReportsByTypeAndState(Integer reportType, Integer reportState){
        BaseResponse baseResponse = new BaseResponse();
        List<Report> reports ;
        if(reportType.equals(4)){
            //所有举报类型
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectAll();

            }else{
                //其他处理状态
                reports = reportmapper.selectByState(reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else{
            //查看问题或回答或评论
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectByType(reportType);

            }else{
                //其他处理状态
                reports = reportmapper.selectByTypeAndState(reportType,reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }


        return baseResponse;
    }

    //根据举报类型和处理状态选择举报_用户
    public BaseResponse searchReportsByTypeAndState(Integer reportUserId, Integer reportType, Integer reportState){
        BaseResponse baseResponse = new BaseResponse();
        List<Report> reports ;
        if(reportType.equals(4)){
            //所有举报类型
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectAllByUser(reportUserId);

            }else{
                //其他处理状态
                reports = reportmapper.selectByStateByUser(reportUserId,reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else{
            //查看问题或回答或评论
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectByTypeByUser(reportUserId,reportType);

            }else{
                //其他处理状态
                reports = reportmapper.selectByTypeAndStateByUser(reportUserId,reportType,reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }

        return baseResponse;
    }

    //根据举报类型和处理状态选择被举报_用户
    public BaseResponse searchReportedsByTypeAndState(Integer reportedUserId, Integer reportType, Integer reportState){
        BaseResponse baseResponse = new BaseResponse();
        List<Report> reports ;
        if(reportType.equals(4)){
            //所有举报类型
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectAllByReportedUser(reportedUserId);

            }else{
                //其他处理状态
                reports = reportmapper.selectByStateByReportedUser(reportedUserId,reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else{
            //查看问题或回答或评论
            if(reportState.equals(3)){

                //所有处理状态
                reports = reportmapper.selectByTypeByReportedUser(reportedUserId,reportType);

            }else{
                //其他处理状态
                reports = reportmapper.selectByTypeAndStateByReportedUser(reportedUserId,reportType,reportState);

            }
            if(reports.size() != 0){
                baseResponse.setData(reports);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }

        return baseResponse;
    }

    //修改举报处理状态
    public BaseResponse modifyReportState(Integer reportId, Integer reportState){
        BaseResponse baseResponse = new BaseResponse();
        Report report = reportmapper.selectByPrimaryKey(reportId);
        int a = reportmapper.updateReportState(reportId,reportState);
        int b = 0;

        if(reportState.equals(1)){
            //同意举报内容
            if(report.getReportType().equals(1)){
                //问题
                Question question = questionmapper.selectByPrimaryKey(report.getReportTypeId());
                b = questionmapper.updateQuesStateByQuesId(question.getQuesId(),1);
            }else if(report.getReportType().equals(2)){
                //回答
                Answer answer = answermapper.selectByPrimaryKey(report.getReportTypeId());
                b = answermapper.updateAnsStateByAnsId(answer.getAnsId(),1);
            }else if(report.getReportType().equals(3)){
                //评论
                Comment comment = commentmapper.selectByPrimaryKey(report.getReportTypeId());
                b = commentmapper.updateComStateByComId(comment.getComId(),1);
            }

            if(a ==1 && b == 1){
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);
            }

        }else if(reportState.equals(2)){
            //拒绝举报内容
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);
            }
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }


}
