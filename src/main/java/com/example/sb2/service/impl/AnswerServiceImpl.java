package com.example.sb2.service.impl;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Comment;
import com.example.sb2.entity.Question;
import com.example.sb2.entity.User;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.answerMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private answerMapper answermapper;
    @Autowired
    private commentMapper commentmapper;
    @Autowired
    private userMapper usermapper;

    public BaseResponse modifyAnswerState(Integer ansId, Integer ansState){
            BaseResponse baseResponse = new BaseResponse();
            Answer answer = answermapper.selectByPrimaryKey(ansId);
            List<Comment> comments = commentmapper.selectComsByAnsId(ansId);
            if(answer != null){
                if(answer.getAnsState().equals(ansState)){
                    //请求无变化
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_CHANGE);
                }else if(ansState.equals(0)){
                    //管理员解除拉黑
                    int i;
                    answermapper.updateAnsStateByAnsId(ansId, ansState);
                    //评论修改状态
                    if(comments.size()!=0){
                        for(i=0;i<comments.size();i++){
                            if(comments.get(i).getComState().equals(4)){
                                commentmapper.updateComStateByComId(comments.get(i).getComId(),0);
                                System.out.println("评论"+i+"解除拉黑完成");
                            }
                        }
                    }
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
                }else if(ansState.equals(1)){
                    //管理员拉黑
                    int i;
                    answermapper.updateAnsStateByAnsId(ansId, ansState);
                    //评论修改状态
                    if(comments.size()!=0){
                        for(i=0;i<comments.size();i++){
                            if(comments.get(i).getComState().equals(0)){
                                commentmapper.updateComStateByComId(comments.get(i).getComId(),4);
                                System.out.println("评论"+i+"拉黑完成");
                            }
                        }
                    }
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
                }else{
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);
                }
            }else if(answer == null){
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_EXIST_ANSWER);//回答不存在
            }else {
                baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
            }
            return baseResponse;
        }

    //用户回答
    public BaseResponse answer(Integer userId, Integer quesId, String ansContent){
        BaseResponse baseResponse = new BaseResponse();
        int a = answermapper.insert(userId,quesId,ansContent);
        if(a == 1){
            baseResponse.setResult(ResultCodeEnum.ANSWER_ADD_SUCCESS);
        }else if(a != 1){
            baseResponse.setResult(ResultCodeEnum.ANSWER_ADD_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }


    //查看回答
    public BaseResponse searchAnswersByUserId(Integer userId){
        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        if(user != null){
            List<Answer> answers = answermapper.selectAnssByUserId(userId);
            if(answers.size() != 0){
                baseResponse.setData(answers);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else if(answers.size()==0){
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else if(user == null){
            baseResponse.setResult(ResultCodeEnum.ANSWER_FIND_FAILURE_NO_USER);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //删除回答
    public BaseResponse deletePersonalAnswer(Integer ansId){
        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);
        if(answer != null){
            int a = answermapper.deleteByPrimaryKey(ansId);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_SUCCESS);//删除成功
            }else{
                baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_FAILURE_DB_ERROR);
            }
        }else if(answer == null){
            baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //修改回答
    public BaseResponse modifyPersonalAnswer(Integer ansId, String ansContent){

        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);

        if(answer != null){
            int a = answermapper.updateAnsByAnsId(ansId,ansContent);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_SUCCESS);//更新成功
            }else{
                baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_FAILURE_DB_ERROR);
            }
        }else if(answer == null){
            baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }
}
