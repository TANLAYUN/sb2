package com.example.sb2.service.impl;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Comment;
import com.example.sb2.entity.Question;
import com.example.sb2.entity.User;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private commentMapper commentmapper;
    @Autowired
    private userMapper usermapper;

    //修改评论状态
    public BaseResponse modifyCommentState(Integer comId, Integer comState){
        BaseResponse baseResponse = new BaseResponse();
        Comment comment = commentmapper.selectByPrimaryKey(comId);
        if(comment != null){
            //请求无变化
            if(comment.getComState().equals(comState)){
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_CHANGE);
            }else if(comState.equals(0) || comState.equals(1)){
                //管理员拉黑或者解除拉黑
                int a;
                a = commentmapper.updateComStateByComId(comId, comState);
                if(a == 1){
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
                }else{
                    baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);//更新数据库失败
                }
            }else{
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);
            }
        }else if(comment == null){
            baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_EXIST_COMMENT);//评论不存在
        }else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //用户评论
    public BaseResponse comment(Integer userId, Integer ansId, String comContent){
        BaseResponse baseResponse = new BaseResponse();
        int a = commentmapper.insert(userId,ansId,comContent);
        if(a == 1){
            baseResponse.setResult(ResultCodeEnum.COMMENT_ADD_SUCCESS);
        }else if(a != 1){
            baseResponse.setResult(ResultCodeEnum.COMMENT_ADD_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //查看评论
    public BaseResponse searchCommentsByUserId(Integer userId){
        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        if(user != null){
            List<Comment> comments = commentmapper.selectComsByUserId(userId);
            if(comments.size() != 0){
                baseResponse.setData(comments);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else if(comments.size()==0){
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else if(user == null){
            baseResponse.setResult(ResultCodeEnum.COMMENT_FIND_FAILURE_NO_USER);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }
    //删除评论
    public BaseResponse deletePersonalComment(Integer comId){
        BaseResponse baseResponse = new BaseResponse();
        Comment comment = commentmapper.selectByPrimaryKey(comId);
        if(comment != null){
            int a = commentmapper.deleteByPrimaryKey(comId);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.COMMENT_DELETE_SUCCESS);//删除成功
            }else{
                baseResponse.setResult(ResultCodeEnum.COMMENT_DELETE_FAILURE_DB_ERROR);
            }
        }else if(comment == null){
            baseResponse.setResult(ResultCodeEnum.COMMENT_DELETE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //修改回答
    public BaseResponse modifyPersonalComment(Integer comId, String comContent){
        BaseResponse baseResponse = new BaseResponse();
        Comment comment = commentmapper.selectByPrimaryKey(comId);

        if(comment != null){
            int a = commentmapper.updateComByComId(comId,comContent);
            if(a == 1){
                baseResponse.setResult(ResultCodeEnum.COMMENT_UPDATE_SUCCESS);//更新成功
            }else{
                baseResponse.setResult(ResultCodeEnum.COMMENT_UPDATE_FAILURE_DB_ERROR);
            }
        }else if(comment == null){
            baseResponse.setResult(ResultCodeEnum.COMMENT_UPDATE_FAILURE_NOT_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

}
