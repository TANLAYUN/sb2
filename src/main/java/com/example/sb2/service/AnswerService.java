package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface AnswerService {
    //修改回答状态
    BaseResponse modifyAnswerState(Integer ansId, Integer ansState);

    //用户回答
    BaseResponse answer(Integer userId, Integer quesId, String ansContent);

    //查看回答
    BaseResponse searchAnswersByUserId(Integer userId);

    //删除回答
    BaseResponse deletePersonalAnswer(Integer ansId);

    //修改回答
    BaseResponse modifyPersonalAnswer(Integer ansId, String ansContent);
}
