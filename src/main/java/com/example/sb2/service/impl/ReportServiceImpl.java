package com.example.sb2.service.impl;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.reportMapper;
import com.example.sb2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private reportMapper reportmapper;

    //用户举报
    public BaseResponse report(Integer reportUserId, Integer reportType, Integer reportedUserId, String reportContent){
        BaseResponse baseResponse = new BaseResponse();
        int a = reportmapper.insert(reportUserId,reportType,reportedUserId,reportContent);
        if( a == 1 ){
            baseResponse.setResult(ResultCodeEnum.REPORT_ADD_SUCCESS);
        }else if( a != 1){
            baseResponse.setResult(ResultCodeEnum.REPORT_ADD_FAILURE);
        }
        return baseResponse;
    }
}
