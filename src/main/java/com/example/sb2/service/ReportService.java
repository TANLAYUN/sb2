package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface ReportService {

    //用户举报
    BaseResponse report(Integer reportUserId, Integer reportType, Integer reportTypeId, Integer reportedUserId, String reportContent);

    //根据举报类型和处理状态选择举报_管理员
    BaseResponse searchReportsByTypeAndState(Integer reportType, Integer reportState);
}
