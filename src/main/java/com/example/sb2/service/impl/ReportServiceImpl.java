package com.example.sb2.service.impl;

import com.example.sb2.entity.Report;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.reportMapper;
import com.example.sb2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private reportMapper reportmapper;

    //用户举报
    public BaseResponse report(Integer reportUserId, Integer reportType,  Integer reportTypeId, Integer reportedUserId, String reportContent){
        BaseResponse baseResponse = new BaseResponse();
        int a = reportmapper.insert(reportUserId,reportType,reportTypeId,reportedUserId,reportContent);
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


}
