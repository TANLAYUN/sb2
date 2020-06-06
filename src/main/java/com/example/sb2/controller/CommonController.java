package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.kit.SendMail;
import com.example.sb2.service.*;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/online_answer/common") //映射到controller
public class CommonController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CommentService commentService;

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

    //文件上传
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam(name = "file", required = false) MultipartFile file, Integer userId/*, HttpServletRequest request*/) {
        BaseResponse baseResponse = new BaseResponse();
        if (file == null) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_NO_FILE);//上传失败_文件不存在
            return baseResponse;
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_FILE_TOO_BIG);//上传失败_文件大小不能大于10M
            return baseResponse;
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_FORMAT_ERROR);//上传失败_请选择jpg,jpeg,gif,png格式的图片
            return baseResponse;
        }
        String savePath = UPLOAD_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        String image = savePath+filename;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
            baseResponse = userService.upload(userId,image);
            System.out.println("savepath+filename:"+savePath+filename);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_SAVE_ERROR);//上传失败_保存文件失败
            return baseResponse;
        }
        baseResponse.setResult(ResultCodeEnum.UPLOAD_SUCCESS);
        return baseResponse;
    }

    @RequestMapping(value = "selectAnssByQuesId", method = RequestMethod.POST)
    public BaseResponse selectAnssByQuesId(Integer quesId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = answerService.selectAnssByQuesId(quesId);
        return baseResponse;
    }

    @RequestMapping(value = "searchCommentsByAnsId", method = RequestMethod.POST)
    public BaseResponse searchCommentsByAnsId(Integer ansId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = commentService.selectComsByAnsId(ansId);
        return baseResponse;
    }

    @RequestMapping(value = "selectAnssByGoodCount", method = RequestMethod.POST)
    public BaseResponse selectAnssByGoodCount(Integer quesId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = answerService.sortByGoodCount(quesId);
        return baseResponse;
    }

    @RequestMapping(value = "selectAllByColNum", method = RequestMethod.POST)
    public BaseResponse selectAllByColNum() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = questionService.selectAllByColNum();
        return baseResponse;
    }

    @RequestMapping(value = "searchAnswerByAnsId", method = RequestMethod.POST)
    public BaseResponse searchAnswerByAnsId(Integer ansId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse = answerService.selectAnsByAnsId(ansId);
        return baseResponse;
    }
}
