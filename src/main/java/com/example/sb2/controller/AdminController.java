package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.service.*;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/online_answer/admin") //映射到controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    DefaultKaptcha defaultKaptcha;

    //生成验证码
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    //验证的方法
    @RequestMapping("/imgvrifyControllerDefaultKaptcha")//
    public BaseResponse imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String mail = (String)httpServletRequest.getParameter("mail");
        String pwd = (String)httpServletRequest.getAttribute("pwd");
        //真正的验证码
        String captchaId = (String) httpServletRequest.getSession().getAttribute("verifyCode");
        //传递的验证码
        String parameter = httpServletRequest.getParameter("confirmCode");
        System.out.println(mail);
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

    @RequestMapping(value = "login", method = RequestMethod.POST) //
    public BaseResponse login(String mail,String pwd){
        BaseResponse baseResponse;
        baseResponse = adminService.login(mail,pwd);
        return baseResponse;//baseResponse.objtoString();
    }

    @RequestMapping(value = "searchAdminInfoByAdminId", method = RequestMethod.POST) //
    public BaseResponse searchAdminInfoByAdminId(Integer adminId){
        BaseResponse baseResponse;
        baseResponse = adminService.searchAdminInfoByAdminId(adminId);
        return baseResponse;//baseResponse.objtoString();
    }

    @RequestMapping(value = "modifyAdminInfo", method = RequestMethod.POST) //
    public BaseResponse modifyAdminInfo(Integer adminId, String mail,String name,String pwd, String newPwd){
        BaseResponse baseResponse;
        baseResponse = adminService.modifyAdminInfo(adminId,mail,name,pwd,newPwd);
        return baseResponse;//baseResponse.objtoString();
    }

    @RequestMapping(value = "modifyUserState", method = RequestMethod.POST) //
    public BaseResponse modifyUserState(Integer userId, Integer userState){
        BaseResponse baseResponse;
        baseResponse = userService.modifyUserState(userId,userState);
        return baseResponse;
    }

    @RequestMapping(value = "modifyCommentState", method = RequestMethod.POST)
    public BaseResponse modifyCommentState(Integer comId, Integer comState){
        BaseResponse baseResponse;
        baseResponse = commentService.modifyCommentState(comId, comState);
        return baseResponse;
    }

    @RequestMapping(value = "modifyAnswerState", method = RequestMethod.POST)
    public BaseResponse modifyAnswerState(Integer ansId, Integer ansState){
        BaseResponse baseResponse;
        baseResponse = answerService.modifyAnswerState(ansId, ansState);
        return baseResponse;
    }

    @RequestMapping(value = "modifyQuestionState", method = RequestMethod.POST)
    public BaseResponse modifyQuestionState(Integer quesId, Integer quesState){
        BaseResponse baseResponse;
        baseResponse = questionService.modifyQuestionState(quesId, quesState);
        return baseResponse;
    }

    @RequestMapping(value = "modifyReportState", method = RequestMethod.POST)
    public BaseResponse modifyReportState(Integer reportId, Integer reportState){
        BaseResponse baseResponse;
        baseResponse = reportService.modifyReportState(reportId, reportState);
        return baseResponse;
    }
    @RequestMapping(value = "searchUsersByState", method = RequestMethod.POST)
    public BaseResponse searchUsersByState(Integer userState){
        BaseResponse baseResponse;
        baseResponse = userService.searchUsersByState(userState);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByState(Integer quesState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByState(quesState);
        return baseResponse;
    }

    @RequestMapping(value = "searchAnswersByState", method = RequestMethod.POST)
    public BaseResponse searchAnswersByState(Integer ansState){
        BaseResponse baseResponse;
        baseResponse = answerService.searchAnswersByState(ansState);
        return baseResponse;
    }

    @RequestMapping(value = "searchCommentsByState", method = RequestMethod.POST)
    public BaseResponse searchCommentsByState(Integer comState){
        BaseResponse baseResponse;
        baseResponse = commentService.searchCommentsByState(comState);
        return baseResponse;
    }

    @RequestMapping(value = "searchReportsByTypeAndState", method = RequestMethod.POST)
    public BaseResponse searchReportsByTypeAndState(Integer reportType, Integer reportState){
        BaseResponse baseResponse;
        baseResponse = reportService.searchReportsByTypeAndState(reportType,reportState);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByQuesAnsState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByQuesAnsState(Integer quesAnsState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByQuesAnsState(quesAnsState);
        return baseResponse;
    }

    //文件上传
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Transactional
    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam(name = "file", required = false) MultipartFile file, Integer adminId) {
        BaseResponse baseResponse = new BaseResponse();

        if (file == null) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_NO_FILE);//上传失败_文件不存在
            return baseResponse;
        }
        if (file.getSize() > 1024 * 1024 * 2) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_FILE_TOO_BIG);//上传失败_文件大小不能大于2M
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
        try {
            baseResponse = adminService.upload(adminId,"/static/"+filename);
            if(baseResponse.getResultCode() == "3000"){
                file.transferTo(new File(savePath + filename));
                System.out.println("savepath+filename:"+savePath+filename);
                System.out.println("我存好了");
                System.out.println(baseResponse.getResultDesc());
                return baseResponse;
            }
            System.out.println(baseResponse.getResultDesc());
            return baseResponse;
            //将文件保存指定目录
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_SAVE_ERROR);//上传失败_保存文件失败
            System.out.println("文件保存失败了："+baseResponse.getResultDesc());
            return baseResponse;
        }

    }


}
