package com.example.sb2.service.impl;

import com.example.sb2.entity.*;
import com.example.sb2.entity.Question;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.*;
import com.example.sb2.service.QuestionService;
import com.sun.mail.imap.ResyncData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.Result;
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
                    System.out.println("answers的大小"+answers.size());
                    for(i=0;i<answers.size();i++){
                        System.out.println("第"+i+"个answer的状态"+answers.get(i).getAnsState());
                        if(answers.get(i).getAnsState().equals(3)){
                            answermapper.updateAnsStateByAnsId(answers.get(i).getAnsId(),0);
                            List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(i).getAnsId());
                            if(comments.size()!=0){
                                for(j=0;j<comments.size();j++){
                                    if(comments.get(j).getComState().equals(3)){
                                        commentmapper.updateComStateByComId(comments.get(j).getComId(),0);
                                        System.out.println("评论"+j+"解除拉黑完成");
                                    }
                                }
                            }
                            System.out.println("回答"+i+"解除拉黑完成");
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
                                        System.out.println("评论"+j+"拉黑完成");
                                    }
                                }
                            }
                            System.out.println("回答"+i+"拉黑完成");
                        }
                    }
                }
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
            }else{
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);
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

        if(quesState.equals(3)){

            List<Question> questions = questionmapper.selectAll();
            Question question;
            User user;
            List<JSONObject> jsonObjects = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            if(questions.size()!= 0){
                int i;
                for(i=0;i<questions.size();i++){
                    question = questions.get(i);
                    user = usermapper.selectByUserId(question.getUserId());
                    jsonObject.put(("question"),question);
                    System.out.println("question"+i+"的标题"+question.getQuesTitle());
                    jsonObject.put(("user_name"),user.getName());
                    System.out.println("user_name"+i+"的内容"+user.getName());
                    jsonObjects.add(i,jsonObject);
                }
                baseResponse.setData(jsonObjects);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }else{
            List<Question> questions = questionmapper.selectByState(quesState);

            if(questions.size()!= 0){
                baseResponse.setData(questions);
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

        List<Question> questions = questionmapper.selectByQuesAnsState(quesAnsState);

        if(questions.size()!= 0){
            baseResponse.setData(questions);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
        }else{
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
        }

        return baseResponse;
    }


    //根据状态选取问题_用户
    public BaseResponse searchQuestionsByState(Integer userId, Integer quesState){
        BaseResponse baseResponse = new BaseResponse();

        if(quesState.equals(3)){

            List<Question> questions = questionmapper.selectAllByUser(userId);

            if(questions.size()!= 0){
                baseResponse.setData(questions);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }else{
            List<Question> questions = questionmapper.selectByUserAndState(userId,quesState);

            if(questions.size()!= 0){
                baseResponse.setData(questions);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }
        return baseResponse;
    }

    //根据问题解决状态选取问题_用户
    public BaseResponse searchQuestionsByQuesAnsState(Integer userId, Integer quesAnsState){

        BaseResponse baseResponse = new BaseResponse();

        List<Question> questions = questionmapper.selectByUserAndQuesAnsState(userId,quesAnsState);

        if(questions.size()!= 0){
            baseResponse.setData(questions);
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
            int a = questionmapper.insert(userId,quesTitle,quesContent,quesReward);
            int b = usermapper.updateUserCapital(userId,(user.getCapital()-quesReward));
            if(a == 1 && b == 1){
                baseResponse.setResult(ResultCodeEnum.QUESTION_ADD_SUCCESS);
            }else if(a != 1 || b != 1){
                baseResponse.setResult(ResultCodeEnum.QUESTION_ADD_FAILURE);
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
        Question question = questionmapper.selectByPrimaryKey(quesId);
        if(question != null){
            baseResponse.setData(question);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        }else if(question == null){
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

}
