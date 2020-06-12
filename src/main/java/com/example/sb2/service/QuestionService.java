package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

import java.text.ParseException;

public interface QuestionService {
    //修改问题状态
    BaseResponse modifyQuestionState(Integer quesId, Integer quesState);

    //根据状态选取问题_管理员
    BaseResponse searchQuestionsByState(Integer quesState);

    //根据问题解决状态选取问题_管理员
    BaseResponse searchQuestionsByQuesAnsState(Integer quesAnsState);

    //根据状态选取问题_用户
    BaseResponse searchQuestionsByState(Integer userId, Integer quesState);

    //根据问题解决状态选取问题_用户
    BaseResponse searchQuestionsByQuesAnsState(Integer userId, Integer quesAnsState);

    //用户提问
    BaseResponse question(Integer userId, String quesTitle, String quesContent,Integer quesReward);

    //删除问题
    BaseResponse deletePersonalQuestion(Integer quesId);

    //修改问题解决状态
    BaseResponse modifyQuesAnsState(Integer quesId, Integer quesAnsState);

    //查看问题详情
    BaseResponse viewQuestionInfo(Integer quesId);

    //根据问题收藏数目查询所有问题
    BaseResponse selectAllByColNum();

    //根据年数查看每月提问次数
    BaseResponse viewQuesnumByYear(String year);

    //日期查看一周的提问次数
    BaseResponse viewQuesnumByDate(String date) throws ParseException;
}
