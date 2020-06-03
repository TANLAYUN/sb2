package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface AnswerService {
    //修改回答状态
    BaseResponse modifyAnswerState(Integer ansId, Integer ansState);

    //用户回答
    BaseResponse answer(Integer userId, Integer quesId, String ansContent);

    //根据用户id查看回答
    BaseResponse searchAnswersByUserId(Integer userId);

    //根据问题id查看回答
    BaseResponse selectAnssByQuesId(Integer quesId);

    //删除回答
    BaseResponse deletePersonalAnswer(Integer ansId);

    //修改回答
    BaseResponse modifyPersonalAnswer(Integer ansId, String ansContent);

    //设成最佳答案
    BaseResponse modifyBestAns(Integer ansId);

    //根据点赞个数排序
    BaseResponse sortByGoodCount(Integer quesId);

    //根据状态选取回答_管理员
    BaseResponse searchAnswersByState(Integer ansState);
}
