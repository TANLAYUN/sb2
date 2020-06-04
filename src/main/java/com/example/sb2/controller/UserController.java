package com.example.sb2.controller;

import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.kit.SendMail;
import com.example.sb2.service.*;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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
    @Autowired
    private LikeService likeService;
    @Autowired
    private ReportService reportService;

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
        System.out.println("mailCon的："+confirmCode);
        String content = "欢迎注册在线问答系统，您的验证码是“" + confirmCode + "”,请将验证码填写至注册页面。若非本人操作，请忽略此邮件。";
        boolean succeed = sendMail.sendingMail(mail, mail, content);
        if (succeed) {
            baseResponse.setResult(ResultCodeEnum.CONFIRMCODE_SEND_SUCCESS);
        } else {
            baseResponse.setResult(ResultCodeEnum.CONFIRMCODE_SEND_FAILURE);
        }
        mailCode.put(mail, confirmCode);
        System.out.println("mailCon的：mail"+mailCode.get(mail));
        System.out.println("mailCon的：mailCode"+mailCode);
        return baseResponse;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResponse register(String mail, String name, String pwd, String confirmCode) {

        BaseResponse baseResponse = new BaseResponse();

        String realCode = mailCode.get(mail);
        System.out.println("register的：mailCode"+mailCode);

        //移除mail
        mailCode.remove(mail);
        System.out.println("register的：mailCode"+mailCode);
        System.out.println("register的：realCode"+realCode);


        if(realCode == null){
            baseResponse.setResult(ResultCodeEnum.REGISTER_FAILURE_NOT_APPLYED_MAILCODE);//没有申请验证码
        } else if (realCode.equals(confirmCode)) {
            baseResponse = userService.register(mail, name, pwd);
        } else {
            baseResponse.setResult(ResultCodeEnum.REGISTER_FAILURE_CONFIRMCODE_ERROR);//验证码错误
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
    public BaseResponse deleteCollections(Integer colUserId, Integer colQuesId){
        BaseResponse baseResponse;
        baseResponse = collectionService.deleteCollections(colUserId,colQuesId);
        return baseResponse;
    }

    @RequestMapping(value = "displayPersonalCollections", method = RequestMethod.POST)
    public BaseResponse displayPersonalCollections(Integer userId){
        BaseResponse baseResponse;
        baseResponse = collectionService.displayPersonalCollections(userId);
        return baseResponse;
    }

    @RequestMapping(value = "searchUserInfoByUserId", method = RequestMethod.POST)
    public BaseResponse searchUserByUserId(Integer userId){
        BaseResponse baseResponse;
        baseResponse = userService.searchUserByUserId(userId);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByState(Integer userId, Integer quesState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByState(userId,quesState);
        return baseResponse;
    }

    @RequestMapping(value = "searchAnswersByState", method = RequestMethod.POST)
    public BaseResponse searchAnswersByState(Integer userId, Integer ansState){
        BaseResponse baseResponse;
        baseResponse = answerService.searchAnswersByState(userId,ansState);
        return baseResponse;
    }

    @RequestMapping(value = "searchCommentsByState", method = RequestMethod.POST)
    public BaseResponse searchCommentsByState(Integer userId, Integer comState){
        BaseResponse baseResponse;
        baseResponse = commentService.searchCommentsByState(userId,comState);
        return baseResponse;
    }

    @RequestMapping(value = "searchQuestionsByQuesAnsState", method = RequestMethod.POST)
    public BaseResponse searchQuestionsByQuesAnsState(Integer userId, Integer quesAnsState){
        BaseResponse baseResponse;
        baseResponse = questionService.searchQuestionsByQuesAnsState(userId,quesAnsState);
        return baseResponse;
    }

    @RequestMapping(value = "searchAnswersByUserId", method = RequestMethod.POST)
    public BaseResponse searchAnswersByUserId(Integer userId){
        BaseResponse baseResponse;
        baseResponse = answerService.searchAnswersByUserId(userId);
        return baseResponse;
    }

    @RequestMapping(value = "searchCommentsByUserId", method = RequestMethod.POST)
    public BaseResponse searchCommentsByUserId(Integer userId){
        BaseResponse baseResponse;
        baseResponse = commentService.searchCommentsByUserId(userId);
        return baseResponse;
    }

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public BaseResponse question(Integer userId, String quesTitle, String quesContent,Integer quesReward){
        BaseResponse baseResponse;
        baseResponse = questionService.question(userId,quesTitle,quesContent,quesReward);
        return baseResponse;
    }

    @RequestMapping(value = "answer", method = RequestMethod.POST)
    public BaseResponse answer(Integer userId, Integer quesId, String ansContent){
        BaseResponse baseResponse;
        baseResponse = answerService.answer(userId,quesId,ansContent);
        return baseResponse;
    }

    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public BaseResponse comment(Integer userId, Integer ansId, String comContent, Integer ansComId){
        BaseResponse baseResponse;
        baseResponse = commentService.comment(userId,ansId,comContent,ansComId);
        return baseResponse;
    }

    @RequestMapping(value = "report", method = RequestMethod.POST)
    public BaseResponse report(Integer reportUserId, Integer reportType, Integer reportTypeId, Integer reportedUserId, String reportContent){
        BaseResponse baseResponse;
        baseResponse = reportService.report(reportUserId,reportType,reportTypeId,reportedUserId,reportContent);
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

    @RequestMapping(value = "modifyBestAns", method = RequestMethod.POST)
    public BaseResponse modifyBestAns(Integer ansId){
        BaseResponse baseResponse;
        baseResponse = answerService.modifyBestAns(ansId);
        return baseResponse;
    }

    //文件上传
    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Transactional
    @PostMapping("/upload")
    public BaseResponse upload(@RequestParam(name = "file", required = false) MultipartFile file, Integer userId) {
        BaseResponse baseResponse = new BaseResponse();
        System.out.println("我是一次请求~~~");
        System.out.println("文件："+file);
        System.out.println("userId："+userId);

        if (file == null) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_NO_FILE);//上传失败_文件不存在
            System.out.println(baseResponse.getResultDesc());
            return baseResponse;
        }
        if (file.getSize() > 1024 * 1024 * 2) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_FILE_TOO_BIG);//上传失败_文件大小不能大于2M
            System.out.println(baseResponse.getResultDesc());
            return baseResponse;
        }

        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_FORMAT_ERROR);//上传失败_请选择jpg,jpeg,gif,png格式的图片
            System.out.println(baseResponse.getResultDesc());
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
            baseResponse = userService.upload(userId,"/static/"+filename);
            if(baseResponse.getResultCode() == "3000"){
                //将文件保存指定目录
                file.transferTo(new File(savePath + filename));
                System.out.println("savepath+filename:"+savePath+filename);
                System.out.println("我存好了");
                System.out.println(baseResponse.getResultDesc());
                return baseResponse;
            }
            System.out.println(baseResponse.getResultDesc());
            return baseResponse;
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse.setResult(ResultCodeEnum.UPLOAD_FAILURE_SAVE_ERROR);//上传失败_保存文件失败
            System.out.println("文件保存失败了："+baseResponse.getResultDesc());
            return baseResponse;
        }
//        System.out.println("我最终上传成功了："+baseResponse.getResultDesc());
//        return  baseResponse;

    }

    @RequestMapping(value = "insertGoodOrBad", method = RequestMethod.POST)
    public BaseResponse insertGoodOrBad(Integer ansId, Integer userId, Integer likeState){
        BaseResponse baseResponse;
        baseResponse = likeService.insert(ansId,userId,likeState);
        return baseResponse;
    }

    @RequestMapping(value = "cancelGoodOrBad", method = RequestMethod.POST)
    public BaseResponse cancelGoodOrBad(Integer id){
        BaseResponse baseResponse;
        baseResponse = likeService.delete(id);
        return baseResponse;
    }

    @RequestMapping(value = "searchReportsByTypeAndState", method = RequestMethod.POST)
    public BaseResponse searchReportsByTypeAndState(Integer reportUserId, Integer reportType, Integer reportState){
        BaseResponse baseResponse;
        baseResponse = reportService.searchReportsByTypeAndState(reportUserId,reportType,reportState);
        return baseResponse;
    }

    @RequestMapping(value = "searchReportedsByTypeAndState", method = RequestMethod.POST)
    public BaseResponse searchReportedsByTypeAndState(Integer reportedUserId, Integer reportType, Integer reportState){
        BaseResponse baseResponse;
        baseResponse = reportService.searchReportedsByTypeAndState(reportedUserId,reportType,reportState);
        return baseResponse;
    }
}
