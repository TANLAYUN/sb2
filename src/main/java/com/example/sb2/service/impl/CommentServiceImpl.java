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
import com.example.sb2.service.CommentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private commentMapper commentmapper;
    @Autowired
    private userMapper usermapper;
    @Autowired
    private answerMapper answermapper;

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
    public BaseResponse comment(Integer userId, Integer ansId, String comContent, Integer ansComId){
        BaseResponse baseResponse = new BaseResponse();
        int a = commentmapper.insert(userId,ansId,comContent,ansComId);
        int b = answermapper.updateAnsComNumByAnsId(ansId,answermapper.selectByPrimaryKey(ansId).getAnsComNum()+1);
        if(a == 1 && b == 1){
            baseResponse.setResult(ResultCodeEnum.COMMENT_ADD_SUCCESS);
        }else if(a != 1 || b != 1){
            baseResponse.setResult(ResultCodeEnum.COMMENT_ADD_FAILURE);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //根据用户id查看评论
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

    //根据回答id查看评论
    public BaseResponse selectComsByAnsId(Integer ansId){
        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Answer answer = answermapper.selectByPrimaryKey(ansId);
        if(answer != null){
            List<Comment> comments = commentmapper.selectComsByAnsId(ansId);
            if(comments.size() != 0){
                int i=0;
                for(i=0;i<comments.size();i++){
                    User user = usermapper.selectByUserId(comments.get(i).getUserId());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("comment",comments.get(i));
                    jsonObject.put("user_name",user.getName());
                    jsonObjects.add(i,jsonObject);
                }
                baseResponse.setData(jsonObjects);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            }else if(comments.size()==0){
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        }else if(answer == null){
            baseResponse.setResult(ResultCodeEnum.COMMENT_FIND_FAILURE_NO_ANS);
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
            Answer answer = answermapper.selectByPrimaryKey(comment.getAnsId());
            int a = commentmapper.deleteByPrimaryKey(comId);
            int b = answermapper.updateAnsComNumByAnsId(answer.getAnsId(),answer.getAnsComNum()-1);
            if(a == 1 && b == 1){
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

    //修改评论
    public BaseResponse modifyPersonalComment(Integer comId, String comContent){
        BaseResponse baseResponse = new BaseResponse();
        Comment comment = commentmapper.selectByPrimaryKey(comId);

        if(comment != null){
            int a = commentmapper.updateComByComId(comId,comContent);
            if(a == 1){
                baseResponse.setData(commentmapper.selectByPrimaryKey(comId));
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

    //根据状态选取问题_管理员
    public BaseResponse searchCommentsByState(Integer comState){

        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        List<Comment> comments;
        Comment comment;
        User user;

        if(comState.equals(5)){

            comments = commentmapper.selectAll();

            if(comments.size()!= 0){
                int i;
                for(i=0;i<comments.size();i++){
                    comment = comments.get(i);
                    user = usermapper.selectByUserId(comment.getUserId());
                    if(comment != null){
                        jsonObject.put(("comment"),comment);
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
            comments = commentmapper.selectByState(comState);

            if(comments.size()!= 0){
                int i;
                for(i=0;i<comments.size();i++){
                    comment = comments.get(i);
                    user = usermapper.selectByUserId(comment.getUserId());
//                    System.out.println("question"+i+"的time显示："+question.getQuesTime());
                    if(comment != null){
                        jsonObject.put(("comment"),comment);
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

    //根据状态选取问题_用户
    public BaseResponse searchCommentsByState(Integer userId, Integer comState){

        BaseResponse baseResponse = new BaseResponse();
        List<JSONObject> jsonObjects = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        List<Comment> comments;
        Comment comment;
//        User user;

        if(comState.equals(5)){

            comments = commentmapper.selectComsByUserId(userId);

            if(comments.size()!= 0){
//                int i;
//                for(i=0;i<comments.size();i++){
//                    comment = comments.get(i);
//                    user = usermapper.selectByUserId(comment.getUserId());
//                    if(comment != null){
//                        jsonObject.put(("comment"),comment);
//                        jsonObject.put(("user_name"),user.getName());
//                        jsonObjects.add(i,jsonObject);
//                    }
//                }
//                baseResponse.setData(jsonObjects);
                baseResponse.setData(comments);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }else{
            comments = commentmapper.selectByUserAndState(userId,comState);

            if(comments.size()!= 0){
//                int i;
//                for(i=0;i<comments.size();i++){
//                    comment = comments.get(i);
//                    user = usermapper.selectByUserId(comment.getUserId());
//                    System.out.println("question"+i+"的time显示："+question.getQuesTime());
//                    if(comment != null){
//                        jsonObject.put(("comment"),comment);
//                        jsonObject.put(("user_name"),user.getName());
//                        jsonObjects.add(i,jsonObject);
//                    }
//                }
//                baseResponse.setData(jsonObjects);
                baseResponse.setData(comments);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据查找成功
            }else{
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
            }

        }
        return baseResponse;

    }
}
