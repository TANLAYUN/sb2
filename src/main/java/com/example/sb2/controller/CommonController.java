package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.kit.SendMail;
import com.example.sb2.service.QuestionService;
import com.example.sb2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/online_answer/common") //映射到controller
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;


    @RequestMapping(value = "viewUserInfo", method = RequestMethod.POST)
    public BaseResponse viewUserInfo(String mail){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = userService.viewUserInfo(mail);
        return baseResponse;
    }

    @RequestMapping(value = "viewQuestionInfo", method = RequestMethod.POST)
    public BaseResponse viewQuestionInfo(Integer quesId){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = questionService.viewQuestionInfo(quesId);
        return baseResponse;
    }



}
