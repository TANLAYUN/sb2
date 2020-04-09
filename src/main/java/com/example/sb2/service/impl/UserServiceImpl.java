package com.example.sb2.service.impl;

import com.example.sb2.entity.*;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.answerMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.questionMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private userMapper usermapper;
    @Autowired
    private questionMapper questionmapper;
    @Autowired
    private answerMapper answermapper;
    @Autowired
    private commentMapper commentmapper;

    //修改用户状态
    public BaseResponse modifyUserState(Integer userId, Integer userState){

        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        System.out.println("userId:"+userId);
        System.out.println(user.getState());
        List<Question> questions = questionmapper.selectQuessByUserId(userId);
        if(user != null){
            if(user.getState().equals(userState)){
                //用户状态和请求一致，无需更改数据库
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_CHANGE);
            }else if(userState.equals(1)){

                //将用户状态调至正常
                if(user.getState().equals(0)){
                    //用户状态为0(未审核)
                    int a = usermapper.modifyUserState(userId,userState);
                    if(a == 1){
                        System.out.println("审核用户成功");
                        baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//审核用户成功
                    }else{
                        System.out.println("审核用户失败");
                        baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);//更新数据库失败
                    }
                }else if(user.getState().equals(2)){
                    //用户状态为2(被拉黑)
                    int i,j,k;
                    //修改用户状态
                    usermapper.modifyUserState(userId,userState);
                    if(questions.size()!=0){
                        System.out.println("questions的大小"+questions.size());
                        for(i=0;i<questions.size();i++){
                            System.out.println("第"+i+"个question的状态"+questions.get(i).getQuesState());
                            if(questions.get(i).getQuesState().equals(2)){
                                questionmapper.updateQuesStateByQuesId(questions.get(i).getQuesId(),0);
                                List<Answer> answers = answermapper.selectAnssByQuesId(questions.get(i).getQuesId());
                                if(answers.size()!=0){
                                    for(j=0;j<answers.size();j++){
                                        if(answers.get(j).getAnsState().equals(2)){
                                            answermapper.updateAnsStateByAnsId(answers.get(j).getAnsId(),0);
                                            List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(j).getAnsId());
                                            if(comments.size()!=0){
                                                for(k=0;k<comments.size();k++){
                                                    if(comments.get(i).getComState().equals(2)){
                                                        commentmapper.updateComStateByComId(comments.get(k).getComId(),0);
                                                        System.out.println("评论"+k+"解除拉黑完成");
                                                    }
                                                }
                                            }
                                            System.out.println("回答"+j+"解除拉黑完成");
                                        }
                                    }
                                }
                                System.out.println("问题"+i+"解除拉黑完成");
                            }
                        }
                    }
                        System.out.println("将用户从拉黑列表中取消成功");
                        baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//用户成功
                }


            }else if(userState.equals(2)){
                //将用户拉黑
                if(user.getState().equals(0)){
                    //用户未审核
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_INVALID_AUDIT_STATE);//用户未审核
                }else if(user.getState().equals(1)){
                    //用户正常
                    //修改用户状态
                    int i,j,k;
                    //修改用户状态
                    usermapper.modifyUserState(userId,userState);
                    if(questions.size()!=0){
                        System.out.println("questions的大小"+questions.size());
                        for(i=0;i<questions.size();i++){
                            System.out.println("第"+i+"个question的状态"+questions.get(i).getQuesState());
                            if(questions.get(i).getQuesState().equals(0)){
                                questionmapper.updateQuesStateByQuesId(questions.get(i).getQuesId(),2);
                                List<Answer> answers = answermapper.selectAnssByQuesId(questions.get(i).getQuesId());
                                if(answers.size()!=0){
                                    for(j=0;j<answers.size();j++){
                                        if(answers.get(j).getAnsState().equals(0)){
                                            answermapper.updateAnsStateByAnsId(answers.get(j).getAnsId(),2);
                                            List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(j).getAnsId());
                                            if(comments.size()!=0){
                                                for(k=0;k<comments.size();k++){
                                                    if(comments.get(i).getComState().equals(0)){
                                                        commentmapper.updateComStateByComId(comments.get(k).getComId(),2);
                                                        System.out.println("评论"+k+"拉黑完成");
                                                    }
                                                }
                                            }
                                            System.out.println("回答"+j+"拉黑完成");
                                        }
                                    }
                                }
                                System.out.println("问题"+i+"拉黑完成");
                            }
                        }
                    }
                    System.out.println("将用户拉黑成功");
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//用户成功
                }else if(user.getState().equals(3)){
                    //审核未通过
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_AUDIT_FAILED);//审核不通过
                }

            }else{
                baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
            }
        }else if(user == null){
            baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_EXIST_USER);//用户不存在
        }else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //根据状态选取用户
    public BaseResponse searchUsersByState(Integer userState){

        BaseResponse baseResponse = new BaseResponse();

        if(userState.equals(4)){

            List<User> users = usermapper.selectAll();

            if(users.size()!= 0){
                baseResponse.setData(users);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }else{
            List<User> users = usermapper.selectByState(userState);

            if(users.size()!= 0){
                baseResponse.setData(users);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }
        return baseResponse;
    }

    //用户注册
    public BaseResponse register(String mail, String name,String pwd){

        BaseResponse baseResponse = new BaseResponse();

        User user = usermapper.selectByPrimaryKey(mail);

        if(user!=null){
            baseResponse.setResult(ResultCodeEnum.USER_ADD_FAILURE_MAIL_EXISTED);//邮箱被占用了
        }else{
            int a = usermapper.insert(user);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.USER_ADD_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.USER_ADD_FAILURE);
            }

        }

        return baseResponse;
    }

    //用户登录
    public BaseResponse login(String mail, String pwd){

        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByPrimaryKey(mail);

        if(user != null){
            if(user.getState().equals(1)){
                if(user.getPwd().equals(pwd)){
                    baseResponse.setData(user);
                    baseResponse.setResult(ResultCodeEnum.LOGIN_SUCCESS);//登录成功
                }else{
                    baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_PWD_ERROR);//密码错误
                }
            }else if(user.getState().equals(0)||user.getState().equals(3)){
                baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_INVALID_USER_STATE);//用户未通过或未审核
            }
        }else if(user == null){
            baseResponse.setResult(ResultCodeEnum.LOGIN_FAILURE_NO_EXIST_USER);//用户不存在
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //修改信息
    public BaseResponse modifyUserInfo(Integer userId, String mail, String name, String pwd,String newPwd){

        BaseResponse baseResponse=new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        User user1 = usermapper.selectByPrimaryKey(mail);
        if(user != null){
            if(user.getPwd().equals(pwd)){
                //账号和密码正确
                if(user.getMail().equals(mail)){

                    //没有修改邮箱
                    int a = usermapper.modifyUserInfo(userId,mail,name,newPwd);
                    if(a == 1){
                        user = usermapper.selectByPrimaryKey(mail);
                        baseResponse.setData(user);
                        baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_SUCESS); // 信息修改成功
                    }else{
                        baseResponse.setResult(ResultCodeEnum.USER_ADD_FAILURE);
                    }

                }else{

                    //修改了邮箱
                    if(user1 == null){
                        //邮箱没有人使用
                        int a = usermapper.modifyUserInfo(userId,mail,name,newPwd);
                        if(a == 1){
                            user = usermapper.selectByPrimaryKey(mail);
                            baseResponse.setData(user);
                            baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_SUCESS); // 信息修改成功
                        }else{
                            baseResponse.setResult(ResultCodeEnum.USER_ADD_FAILURE);
                        }
                    }else{
                        baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_USER_MAIL_EXIST);//邮箱被使用
                    }

                }

            } else {
                baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_PWD_ERROR); // 信息修改失败，密码错误
            }
        } else if(user == null){
            baseResponse.setResult(ResultCodeEnum.INFO_UPDATE_FAILURE_NO_EXIST_USER); // 信息修改失败，用户不存在
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //查看用户详细信息
    public BaseResponse viewUserInfo(String mail){
        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByPrimaryKey(mail);
        if(user != null){
            baseResponse.setData(user);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        }else if(user == null){
             baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

}
