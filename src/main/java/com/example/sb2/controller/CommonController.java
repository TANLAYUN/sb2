package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.kit.SendMail;
import com.example.sb2.service.AdminService;
import com.example.sb2.service.QuestionService;
import com.example.sb2.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/online_answer/common") //映射到controller
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private AdminService adminService;

    @RequestMapping("/captcha.jpg")
    public String captcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生成文字验证码
            //String text = defaultKaptcha.createText();
            // 生成图片验证码
            //BufferedImage image = defaultKaptcha.createImage(text);
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            System.out.println("验证码:"+createText);

            // 保存到shiro session
            //redisRepository.set(RedisKey.kaptchaCode(text), text);

            request.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(challenge, "jpg", out);
            out.flush();
            return createText;
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        return "错误";
    }

    //验证的方法
    @RequestMapping("/imgvrifyControllerDefaultKaptcha")//
    public BaseResponse imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String mail = (String)httpServletRequest.getParameter("mail");
        String pwd = (String)httpServletRequest.getParameter("pwd");
        //真正的验证码
        String captchaId = (String) httpServletRequest.getAttribute("verifyCode");

        //String captchaId = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        //传递的验证码
        String parameter = (String)httpServletRequest.getParameter("confirmCode");
        System.out.println(mail);
        System.out.println(pwd);
        System.out.println("Session  verifyCode "+captchaId+" true confirmCode "+parameter);

        BaseResponse baseResponse=adminService.login(mail,pwd);
        if (baseResponse.getResultCode().equals(4000) && !captchaId.equals(parameter)) {
            baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_CODE_ERROR);
            System.out.println("验证码错误");
        } else if(captchaId.equals(parameter)){
            System.out.println("验证码正确");
        }
        return baseResponse;
    }


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
