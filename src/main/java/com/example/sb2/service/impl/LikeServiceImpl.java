package com.example.sb2.service.impl;


import com.example.sb2.entity.Answer;
import com.example.sb2.entity.LikeOrNot;
import com.example.sb2.entity.User;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.answerMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.likeOrNotMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    private likeOrNotMapper likeOrNotmapper;
    @Autowired
    private answerMapper answermapper;
    @Autowired
    private userMapper usermapper;

    //插入赞或踩
    public BaseResponse insert(Integer ansId, Integer userId, Integer likeState){
        BaseResponse baseResponse = new BaseResponse();
        List<LikeOrNot> likeOrNots = likeOrNotmapper.selectByAnsAndUser(ansId,userId);
        User user = usermapper.selectByUserId(userId);
        Answer answer = answermapper.selectByPrimaryKey(ansId);
        if(likeState.equals(1)){//点赞

            if(likeOrNots.size() == 0){
                //没有赞的记录
                if(user == null){
                    //用户不存在
                    baseResponse.setResult(ResultCodeEnum.GOOD_FAILURE_USER_NOT_EXIST);
                }else{
                    //用户存在
                    if(answer == null){
                        //回答不存在
                        baseResponse.setResult(ResultCodeEnum.GOOD_FAILURE_ANS_NOT_EXIST);
                    }else{
                        //回答存在
                        int a = likeOrNotmapper.insert(ansId,userId,likeState);
                        int b = answermapper.updateGoodByAnsId(ansId,answer.getGoodCount()+1);
                        if(a == 1 && b == 1){
                            //插入成功
                            baseResponse.setData(likeOrNotmapper.selectByAnsAndUser(ansId,userId).get(0));
                            baseResponse.setResult(ResultCodeEnum.GOOD_SUCCESS);
                        }else{
                            baseResponse.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
                        }
                    }
                }

            }else if(likeOrNots.size() != 0){
                baseResponse.setResult(ResultCodeEnum.GOOD_FAILURE_ALREADT_EXIST);//已经赞过了
            }

        }else if(likeState.equals(2)){//踩

            if(likeOrNots.size() == 0){
                //没有踩的记录
                if(user == null){
                    //用户不存在
                    baseResponse.setResult(ResultCodeEnum.BAD_FAILURE_USER_NOT_EXIST);
                }else{
                    //用户存在
                    if(answer == null){
                        //回答不存在
                        baseResponse.setResult(ResultCodeEnum.BAD_FAILURE_ANS_NOT_EXIST);
                    }else{
                        //回答存在
                        int a = likeOrNotmapper.insert(ansId,userId,likeState);
                        int b = answermapper.updateBadByAnsId(ansId,answer.getBadCount()+1);
                        if(a == 1 && b == 1){
                            //插入成功
                            baseResponse.setData(likeOrNotmapper.selectByAnsAndUser(ansId,userId).get(0));
                            baseResponse.setResult(ResultCodeEnum.BAD_SUCCESS);
                        }else{
                            baseResponse.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
                        }
                    }
                }

            }else if(likeOrNots.size() != 0){
                baseResponse.setResult(ResultCodeEnum.BAD_FAILURE_ALREADT_EXIST);//已经踩过了
            }

        }else{
            baseResponse.setResult(ResultCodeEnum.WRONG_LIKE_STATE);//错误的状态
        }

        return baseResponse;
    }

    //取消赞或踩
    public BaseResponse delete(Integer id){
        BaseResponse baseResponse = new BaseResponse();
        List<LikeOrNot> likeOrNots = likeOrNotmapper.selectByPrimaryKey(id);

        if(likeOrNots.size() != 0){
            //有记录，可以取消
            LikeOrNot likeOrNot = likeOrNots.get(0);
            int b;
            int a = likeOrNotmapper.delete(id);
            Answer answer = answermapper.selectByPrimaryKey(likeOrNot.getAnsId());
            if(likeOrNot.getLikeState().equals(1)){
                //现在是赞的状态
                b = answermapper.updateGoodByAnsId(likeOrNot.getAnsId(),answer.getGoodCount()-1);
            }else if(likeOrNot.getLikeState().equals(2)){
                //现在是踩的状态
                b = answermapper.updateBadByAnsId(likeOrNot.getAnsId(),answer.getBadCount()-1);
            }else{
                b = 0;
            }
            if(a == 1 && b == 1 ){
                baseResponse.setResult(ResultCodeEnum.CANCEL_SUCCESS);//取消成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_DELETE_FAILURE);//数据库删除失败
            }

        }else{
            //没有记录
            baseResponse.setResult(ResultCodeEnum.CANCEL_FAILURE_NOT_EXIST);//取消失败，不存在
        }

        return baseResponse;
    }
}
