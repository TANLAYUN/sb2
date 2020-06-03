package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface ReportService {

    //用户举报
    BaseResponse report(Integer reportUserId, Integer reportType, Integer reportedUserId, String reportContent);
}
