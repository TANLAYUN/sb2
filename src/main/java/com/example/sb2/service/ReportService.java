package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface ReportService {

    //用户举报
    BaseResponse report(Integer reportUserId, Integer reportType, Integer reportTypeId, Integer reportedUserId, String reportContent);

    //根据举报类型和处理状态选择举报_管理员
    BaseResponse searchReportsByTypeAndState(Integer reportType, Integer reportState);

    //根据举报类型和处理状态选择举报_用户
    BaseResponse searchReportsByTypeAndState(Integer reportUserId, Integer reportType, Integer reportState);

    //根据举报类型和处理状态选择被举报_用户
    BaseResponse searchReportedsByTypeAndState(Integer reportedUserId, Integer reportType, Integer reportState);

    //修改举报处理状态
    BaseResponse modifyReportState(Integer reportId, Integer reportState);
}
