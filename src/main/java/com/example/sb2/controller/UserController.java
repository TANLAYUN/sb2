package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.kit.SendMail;
import com.example.sb2.service.*;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/online_answer/user") //映射到controller
public class UserController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private QuestionService questionService;

    public static Map<String, String> mailCode = new HashMap<String, String>();

    @RequestMapping(value = "mailConfirm", method = RequestMethod.POST)
    public BaseResponse mailConfirm(String mail) {
        BaseResponse baseResponse = new BaseResponse();
        SendMail sendMail = new SendMail();
        StringBuilder str = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String confirmCode = str.toString();
        System.out.println(confirmCode);
        String content = "欢迎注册在线问答系统，您的验证码是“" + confirmCode + "”,请将验证码填写至注册页面。若非本人操作，请忽略此邮件。";
        boolean succeed = sendMail.sendingMail(mail, mail, content);
        if (succeed) {
            baseResponse.setResult(ResultCodeEnum.CONFIRMCODE_SEND_SUCCESS);
        } else {
            baseResponse.setResult(ResultCodeEnum.CONFIRMCODE_SEND_FAILURE);
        }
        mailCode.put(mail, confirmCode);
        System.out.println(mailCode.get(mail));
        System.out.println(mailCode);
        return baseResponse;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResponse register(String mail, String name, String pwd, String confirmCode) {

        BaseResponse baseResponse = new BaseResponse();

        String realCode = mailCode.get(mail);
        System.out.println(mailCode);
        mailCode.remove(mail);
        System.out.println(mailCode);
        System.out.println(realCode);
        if (realCode.equals(confirmCode)) {
            baseResponse = userService.register(mail, name, pwd);
        } else {
            baseResponse.setResult(ResultCodeEnum.REGISTER_FAILURE_CONFIRMCODE_ERROR);
        }
        return baseResponse;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST) //
    public BaseResponse login(String mail,String pwd){
        BaseResponse baseResponse;
        baseResponse = userService.login(mail,pwd);
        JSONObject object = JSONObject.fromObject(baseResponse);
        System.out.println(mail);
        String jsonstr = object.toString();
        System.out.println(baseResponse);
        System.out.println(baseResponse.objtoString());
        System.out.println(jsonstr);
        return baseResponse;//baseResponse.objtoString();
    }

    @RequestMapping(value = "modifyUserInfo", method = RequestMethod.POST) //
    public BaseResponse modifyAdminInfo(Integer userId, String mail,String name,String pwd, String newPwd){
        BaseResponse baseResponse;
        baseResponse = userService.modifyUserInfo(userId,mail,name,pwd,newPwd);
        return baseResponse;//baseResponse.objtoString();
    }

    @RequestMapping(value = "collect", method = RequestMethod.POST)
    public BaseResponse collect(Integer colUserId, Integer colQuesId){
        BaseResponse baseResponse;
        baseResponse = collectionService.collect(colUserId,colQuesId);
        return baseResponse;
    }

    @RequestMapping(value = "deleteCollections", method = RequestMethod.POST)
    public BaseResponse deleteCollections(Integer colId, Integer colQuesId){
        BaseResponse baseResponse;
        baseResponse = collectionService.deleteCollections(colId,colQuesId);
        return baseResponse;
    }

    @RequestMapping(value = "displayPersonalCollections", method = RequestMethod.POST)
    public BaseResponse displayPersonalCollections(Integer userId){
        BaseResponse baseResponse;
        baseResponse = collectionService.displayPersonalCollections(userId);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByState(Integer userId, Integer quesState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByState(userId,quesState);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByQuesAnsState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByQuesAnsState(Integer userId, Integer quesAnsState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByQuesAnsState(userId,quesAnsState);
        return baseResponse;
    }

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public BaseResponse question(Integer userId, String quesTitle, String quesContent){
        BaseResponse baseResponse;
        baseResponse = questionService.question(userId,quesTitle,quesContent);
        return baseResponse;
    }

    @RequestMapping(value = "answer", method = RequestMethod.POST)
    public BaseResponse answer(Integer userId, Integer quesId, String ansContent){
        BaseResponse baseResponse;
        baseResponse = answerService.answer(userId,quesId,ansContent);
        return baseResponse;
    }

    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public BaseResponse comment(Integer userId, Integer ansId, String comContent){
        BaseResponse baseResponse;
        baseResponse = commentService.comment(userId,ansId,comContent);
        return baseResponse;
    }

    @RequestMapping(value = "deletePersonalQuestion", method = RequestMethod.POST)
    public BaseResponse deletePersonalQuestion(Integer quesId){
        BaseResponse baseResponse;
        baseResponse = questionService.deletePersonalQuestion(quesId);
        return baseResponse;
    }

    @RequestMapping(value = "deletePersonalAnswer", method = RequestMethod.POST)
    public BaseResponse deletePersonalAnswer(Integer ansId){
        BaseResponse baseResponse;
        baseResponse = answerService.deletePersonalAnswer(ansId);
        return baseResponse;
    }

    @RequestMapping(value = "deletePersonalComment", method = RequestMethod.POST)
    public BaseResponse deletePersonalComment(Integer comId){
        BaseResponse baseResponse;
        baseResponse = commentService.deletePersonalComment(comId);
        return baseResponse;
    }

    @RequestMapping(value = "modifyPersonalAnswer", method = RequestMethod.POST)
    public BaseResponse modifyPersonalAnswer(Integer ansId, String ansContent){
        BaseResponse baseResponse;
        baseResponse = answerService.modifyPersonalAnswer(ansId,ansContent);
        return baseResponse;
    }

    @RequestMapping(value = "modifyPersonalComment", method = RequestMethod.POST)
    public BaseResponse modifyPersonalComment(Integer comId, String comContent){
        BaseResponse baseResponse;
        baseResponse = commentService.modifyPersonalComment(comId,comContent);
        return baseResponse;
    }

    @RequestMapping(value = "modifyQuesAnsState", method = RequestMethod.POST)
    public BaseResponse modifyQuesAnsState(Integer quesId, Integer quesAnsState){
        BaseResponse baseResponse;
        baseResponse = questionService.modifyQuesAnsState(quesId,quesAnsState);
        return baseResponse;
    }




}
