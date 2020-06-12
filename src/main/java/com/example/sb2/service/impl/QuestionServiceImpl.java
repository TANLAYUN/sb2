package com.example.sb2.service.impl;

import com.example.sb2.entity.*;
import com.example.sb2.entity.Question;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.*;
import com.example.sb2.service.QuestionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private questionMapper questionmapper;
    @Autowired
    private answerMapper answermapper;
    @Autowired
    private commentMapper commentmapper;
    @Autowired
    private userMapper usermapper;

    //修改问题状态
    public BaseResponse modifyQuestionState(Integer quesId, Integer quesState){
        BaseResponse baseResponse = new BaseResponse();
        Question question = questionmapper.selectByPrimaryKey(quesId);
        List<Answer> answers = answermapper.selectAnssByQuesId(quesId);
        if(question != null){
            if(question.getQuesState().equals(quesState)){
                //请求无变化
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_CHANGE);
            }else if(quesState.equals(0)){
                //管理员解除问题拉黑
                int i,j;
                questionmapper.updateQuesStateByQuesId(quesId,quesState);
                //回答修改状态
                if(answers.size()!=0){
                    for(i=0;i<answers.size();i++){
                        if(answers.get(i).getAnsState().equals(3)){
                            answermapper.updateAnsStateByAnsId(answers.get(i).getAnsId(),0);
                            List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(i).getAnsId());
                            if(comments.size()!=0){
                                for(j=0;j<comments.size();j++){
                                    if(comments.get(j).getComState().equals(3)){
                                        commentmapper.updateComStateByComId(comments.get(j).getComId(),0);
                                    }
                                }
                            }
                         }
                    }
                }
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
            }else if(quesState.equals(1)){
                //管理员拉黑
                int i,j;
                questionmapper.updateQuesStateByQuesId(quesId,quesState);
                //回答修改状态
                if(answers.size()!=0){
                    for(i=0;i<answers.size();i++){
                        if(answers.get(i).getAnsState().equals(0)){
                            answermapper.updateAnsStateByAnsId(answers.get(i).getAnsId(),3);
                            List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(i).getAnsId());
                            if(comments.size()!=0){
                                for(j=0;j<comments.size();j++){
                                    if(comments.get(j).getComState().equals(0)){
                                        commentmapper.updateComStateByComId(comments.get(j).getComId(),3);
                                    }
                                }
                            }
                        }
                    }
                }
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
            }else{
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROR);
            }
        }else if(question == null){
            baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_EXIST_QUESTION);//问题不存在
        }else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //根据状态选取问题_管理员
    public BaseResponse searchQuestionsByState(Integer quesState){

        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        List<Question> questions;
        Question question;
        User user;

        if(quesState.equals(3)){

            questions = questionmapper.selectAll();

            if(questions.size()!= 0){
                int i;
                for(i=0;i<questions.size();i++){
                    question = questions.get(i);
                    user = usermapper.selectByUserId(question.getUserId());
                    if(question != null){
                        jsonObject.put(("question"),question);
                        jsonObject.put(("user_name"),user.getName());
                        jsonObjects.add(i,jsonObject);
                    }
                }
                baseResponse.setData(jsonObjects);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }else{
            questions = questionmapper.selectByState(quesState);

            if(questions.size()!= 0){
                int i;
                for(i=0;i<questions.size();i++){
                    question = questions.get(i);
                    user = usermapper.selectByUserId(question.getUserId());
                    if(question != null){
                        jsonObject.put(("question"),question);
                        jsonObject.put(("user_name"),user.getName());
                        jsonObjects.add(i,jsonObject);
                    }
                }
                baseResponse.setData(jsonObjects);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }
        return baseResponse;

    }

    //根据问题解决状态选取问题_管理员
    public BaseResponse searchQuestionsByQuesAnsState(Integer quesAnsState){

        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Question question;
        User user;
        List<Question> questions = questionmapper.selectByQuesAnsState(quesAnsState);

        if(questions.size()!= 0){
            int i;
            for(i=0;i<questions.size();i++){
                question = questions.get(i);
                user = usermapper.selectByUserId(question.getUserId());
                if(question != null){
                    jsonObject.put(("question"),question);
                    jsonObject.put(("user_name"),user.getName());
                    jsonObjects.add(i,jsonObject);
                }
            }
            baseResponse.setData(jsonObjects);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
        }else{
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
        }

        return baseResponse;
    }

    //根据状态选取问题_用户
    public BaseResponse searchQuestionsByState(Integer userId, Integer quesState){
        BaseResponse baseResponse = new BaseResponse();
        List<Question> questions;

        //数据查找成功
        //没有记录
        if(quesState.equals(3)){
            questions = questionmapper.selectAllByUser(userId);
        }else{
            questions = questionmapper.selectByUserAndState(userId,quesState);
        }
        if(questions.size()!= 0){
            baseResponse.setData(questions);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
        }else{
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
        }
        return baseResponse;
    }

    //根据问题解决状态选取问题_用户
    public BaseResponse searchQuestionsByQuesAnsState(Integer userId, Integer quesAnsState){

        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        Question question;
        User user;

        List<Question> questions = questionmapper.selectByUserAndQuesAnsState(userId,quesAnsState);

        if(questions.size()!= 0){
            int i;
            for(i=0;i<questions.size();i++){
                question = questions.get(i);
                user = usermapper.selectByUserId(userId);
                if(question != null){
                    jsonObject.put(("question"),question);
                    jsonObject.put(("user_name"),user.getName());
                    jsonObjects.add(i,jsonObject);
                }
            }
            baseResponse.setData(jsonObjects);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
        }else{
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
        }

        return baseResponse;
    }

    //用户提问
    public BaseResponse question(Integer userId, String quesTitle, String quesContent, Integer quesReward){

        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        if(user.getCapital() < quesReward){
            baseResponse.setResult(ResultCodeEnum.QUESTION_ADD_FAILURE_INSUFFICIENT_CAPITAL);
        }else{
            //获取系统时间
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
            String quesTime = dateFormat.format(now);
            int a = questionmapper.insert(userId,quesTitle,quesContent,quesReward,quesTime);
            int b = usermapper.updateUserCapital(userId,(user.getCapital()-quesReward));
            if(a == 1 && b == 1){
                baseResponse.setResult(ResultCodeEnum.QUESTION_ADD_SUCCESS);
            }else if(a != 1 || b != 1){
                baseResponse.setResult(ResultCodeEnum.QUESTION_ADD_FAILURE_DB_ERROR);
            }else{
                baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
            }
        }


        return baseResponse;
    }

    //删除问题
    public BaseResponse deletePersonalQuestion(Integer quesId){

        BaseResponse baseResponse = new BaseResponse();
        Question question = questionmapper.selectByPrimaryKey(quesId);
        if(question != null){
            int a = questionmapper.deleteByPrimaryKey(quesId);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.QUESTION_DELETE_SUCCESS);//删除成功
            }else{
                baseResponse.setResult(ResultCodeEnum.QUESTION_DELETE_FAILURE_DB_ERROR);
            }
        }else if(question == null){
            baseResponse.setResult(ResultCodeEnum.QUESTION_DELETE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //修改问题解决状态
    public BaseResponse modifyQuesAnsState(Integer quesId, Integer quesAnsState){

        BaseResponse baseResponse = new BaseResponse();
        Question question = questionmapper.selectByPrimaryKey(quesId);

        if(question != null){

            if(quesAnsState.equals(1)){
                int a = questionmapper.updateQuesAnsStateByQuesId(quesId,quesAnsState);
                if(a == 1){
                    baseResponse.setResult(ResultCodeEnum.QUES_ANS_STATE_UPDATE_SUCCESS);
                }else{
                    baseResponse.setResult(ResultCodeEnum.QUES_ANS_STATE_UPDATE_FAILURE_DB_ERROR);
                }
            }else{
                baseResponse.setResult(ResultCodeEnum.QUES_ANS_STATE_UPDATE_FAILURE_WRONG_STATE);
            }

        }else if(question == null){
            baseResponse.setResult(ResultCodeEnum.QUES_ANS_STATE_UPDATE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //查看问题详情
    public BaseResponse viewQuestionInfo(Integer quesId){
        BaseResponse baseResponse = new BaseResponse();
        JSONObject jsonObject = new JSONObject();
        User user;
        Question question = questionmapper.selectByPrimaryKey(quesId);

        if(question != null){
            user = usermapper.selectByUserId(question.getUserId());
            jsonObject.put("question",question);
            jsonObject.put("user_name",user.getName());
            baseResponse.setData(jsonObject);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        }else if(question == null){
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //根据问题收藏数目查询所有问题
    public BaseResponse selectAllByColNum(){
        BaseResponse baseResponse = new BaseResponse();
        List<Question> questions = questionmapper.selectAllByColNum();
        List<JSONObject> jsonObjects = new ArrayList<>();
        if(questions.size() != 0){
            int i;
            for(i=0;i<questions.size();i++){
                Question question = questions.get(i);
                User user = usermapper.selectByUserId(question.getUserId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("question",question);
                jsonObject.put("user_name",user.getName());
                jsonObjects.add(i,jsonObject);
            }
            baseResponse.setData(jsonObjects);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        }else if(questions.size() == 0){
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //根据年数查看每月提问次数
    public BaseResponse viewQuesnumByYear(String year){
        BaseResponse baseResponse = new BaseResponse();
        List list = new ArrayList();
        int i=0,a=0;
        for(i=0;i<12;i++){
            String string;
            if(i<9){
                string = year+"-0"+(i+1);
            }else{
                string = year+"-"+(i+1);
            }
            System.out.println(string);
//            System.out.println(usermapper.selectNumByString(string));
            List<Question> questions = questionmapper.selectNumByString(string);
            System.out.println(questions.size());
            list.add(i,questions.size());
        }
        baseResponse.setData(list);
        baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        return baseResponse;
    }

    //日期查看一周提问次数
    public BaseResponse viewQuesnumByDate(String date) throws ParseException {
        BaseResponse baseResponse = new BaseResponse();
        List list = new ArrayList();
        int i=0,a=0;

        for(i=0;i<7;i++){
            String string=null;
            if (i != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date sDate = sdf.parse(date);//要实现日期+1 需要String转成Date类型

                Format f = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("Date结束日期:" + f.format(sDate));

                Calendar c = Calendar.getInstance();
                c.setTime(sDate);
                c.add(Calendar.DAY_OF_MONTH, 1);      //利用Calendar 实现 Date日期+1天

                sDate = c.getTime();
                System.out.println("Date结束日期+1 " +f.format(sDate));//打印Date日期,显示成功+1天

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf1.format(sDate);
                System.out.println("Date类型转String类型  "+date);//将日期转成String类型 方便进入数据库比较

            }
            List<Question> questions = questionmapper.selectNumByString(date);
            System.out.println(questions.size());
            list.add(i,questions.size());
        }
        baseResponse.setData(list);
        baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        return baseResponse;
    }
}
