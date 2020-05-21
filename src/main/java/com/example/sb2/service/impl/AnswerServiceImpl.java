package com.example.sb2.service.impl;

import com.example.sb2.entity.Answer;
import com.example.sb2.entity.Comment;
import com.example.sb2.entity.Question;
import com.example.sb2.entity.User;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.answerMapper;
import com.example.sb2.mapper.commentMapper;
import com.example.sb2.mapper.questionMapper;
import com.example.sb2.mapper.userMapper;
import com.example.sb2.service.AnswerService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private questionMapper questionmapper;

    public BaseResponse modifyAnswerState(Integer ansId, Integer ansState) {
        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);
        List<Comment> comments = commentmapper.selectComsByAnsId(ansId);
        if (answer != null) {
            if (answer.getAnsState().equals(ansState)) {
                //请求无变化
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_CHANGE);
            } else if (ansState.equals(0)) {
                //管理员解除拉黑
                int i;
                answermapper.updateAnsStateByAnsId(ansId, ansState);
                //评论修改状态
                if (comments.size() != 0) {
                    for (i = 0; i < comments.size(); i++) {
                        if (comments.get(i).getComState().equals(4)) {
                            commentmapper.updateComStateByComId(comments.get(i).getComId(), 0);
                            System.out.println("评论" + i + "解除拉黑完成");
                        }
                    }
                }
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
            } else if (ansState.equals(1)) {
                //管理员拉黑
                int i;
                answermapper.updateAnsStateByAnsId(ansId, ansState);
                //评论修改状态
                if (comments.size() != 0) {
                    for (i = 0; i < comments.size(); i++) {
                        if (comments.get(i).getComState().equals(0)) {
                            commentmapper.updateComStateByComId(comments.get(i).getComId(), 4);
                            System.out.println("评论" + i + "拉黑完成");
                        }
                    }
                }
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_SUCCESS);//状态修改成功
            } else {
                baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_UPDATE_DB_ERROE);
            }
        } else if (answer == null) {
            baseResponse.setResult(ResultCodeEnum.STATE_CHANGE_FAILURE_NO_EXIST_ANSWER);//回答不存在
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //用户回答
    public BaseResponse answer(Integer userId, Integer quesId, String ansContent) {
        BaseResponse baseResponse = new BaseResponse();
        Question question = questionmapper.selectByPrimaryKey(quesId);
        //获取系统时间
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String ansTime = dateFormat.format(now);
//        System.out.println("回答的时间："+ansTime);
        int a = answermapper.insert(userId, quesId, ansContent, ansTime);
        int b = questionmapper.updateQuesAnsNumByQuesId(quesId, question.getQuesAnsNum() + 1);
        if (a == 1 && b == 1) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_ADD_SUCCESS);
        } else if (a != 1 || b != 1) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_ADD_FAILURE);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }


    //根据用户id查看回答
    public BaseResponse searchAnswersByUserId(Integer userId) {
        BaseResponse baseResponse = new BaseResponse();
        User user = usermapper.selectByUserId(userId);
        if (user != null) {
            List<Answer> answers = answermapper.selectAnssByUserId(userId);
            if (answers.size() != 0) {
                baseResponse.setData(answers);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            } else if (answers.size() == 0) {
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        } else if (user == null) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_FIND_FAILURE_NO_USER);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //根据问题id查看回答
    public BaseResponse selectAnssByQuesId(Integer quesId) {
        BaseResponse baseResponse = new BaseResponse();
        Question question = questionmapper.selectByPrimaryKey(quesId);
        List<JSONObject> jsonObjects = new ArrayList<>();
        if (question != null) {
            List<Answer> answers = answermapper.selectAnssByQuesId(quesId);
            if (answers.size() != 0) {
                int i, j;
                for (i = 0; i < answers.size(); i++) {
                    User ans_user = usermapper.selectByUserId(answers.get(i).getUserId());
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("answer", answers.get(i));
                    jsonObject1.put("ans_user_name", ans_user.getName());

                    //添加评论
                    List<JSONObject> comJsonObjs = new ArrayList<>();
                    if (answers.get(i) != null) {
                        System.out.println("answer" + i + "不是空");
                        List<Comment> comments = commentmapper.selectComsByAnsId(answers.get(i).getAnsId());
                        if (comments.size() != 0) {
                            System.out.println("comments不是空");
                            for (j = 0; j < comments.size(); j++) {
                                System.out.println("comment" + j + "不是空");
                                User user = usermapper.selectByUserId(comments.get(j).getUserId());
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("comment", comments.get(j));
                                System.out.println("ans" + i + "的comment" + j + "内容：" + comments.get(j).getComContent());
                                jsonObject.put("com_user_name", user.getName());
                                System.out.println("对应的作者名字：" + user.getName());
                                comJsonObjs.add(j, jsonObject);
                                System.out.println("我是jsonObject" + jsonObject.toString());
                            }
                        }
                    } else {
                        System.out.println("answer" + i + "是空");
                        comJsonObjs = null;
                    }
                    jsonObject1.put("comments", comJsonObjs);
                    jsonObjects.add(i, jsonObject1);
                }
                baseResponse.setData(jsonObjects);
                baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
            } else if (answers.size() == 0) {
                baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
            }
        } else if (question == null) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_FIND_FAILURE_NO_QUES);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }
        return baseResponse;
    }

    //删除回答
    public BaseResponse deletePersonalAnswer(Integer ansId) {
        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);
        if (answer != null) {
            Question question = questionmapper.selectByPrimaryKey(answer.getQuesId());
            int a = answermapper.deleteByPrimaryKey(ansId);
            int b = questionmapper.updateQuesAnsNumByQuesId(question.getQuesId(), question.getQuesAnsNum() - 1);
            if (a == 1 && b == 1) {
                baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_SUCCESS);//删除成功
            } else {
                baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_FAILURE_DB_ERROR);
            }
        } else if (answer == null) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_DELETE_FAILURE_NOT_EXIST);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //修改回答
    public BaseResponse modifyPersonalAnswer(Integer ansId, String ansContent) {

        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);

        if (answer != null) {
            int a = answermapper.updateAnsByAnsId(ansId, ansContent);
            if (a == 1) {
                baseResponse.setData(answermapper.selectByPrimaryKey(ansId));
                baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_SUCCESS);//更新成功
            } else {
                baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_FAILURE_DB_ERROR);
            }
        } else if (answer == null) {
            baseResponse.setResult(ResultCodeEnum.ANSWER_UPDATE_FAILURE_NOT_EXIST);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //设成最佳答案
    public BaseResponse modifyBestAns(Integer ansId) {
        BaseResponse baseResponse = new BaseResponse();
        Answer answer = answermapper.selectByPrimaryKey(ansId);

        if (answer == null) {
            baseResponse.setResult(ResultCodeEnum.BESTANS_UPDATE_FAILURE_ANS_NOT_EXIST);
        } else if (answer != null) {
            User user = usermapper.selectByUserId(answermapper.selectUserIdByAnsId(ansId));
            if (user == null) {
                baseResponse.setResult(ResultCodeEnum.BESTANS_UPDATE_FAILURE_USER_NOT_EXIST);
            } else {
                baseResponse.setResult(ResultCodeEnum.BESTANS_UPDATE_SUCCESS);
            }
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }


    //根据点赞个数排序
    public BaseResponse sortByGoodCount(Integer quesId) {
        BaseResponse baseResponse = new BaseResponse();
        List<Answer> answers = answermapper.sortByGoodCount(quesId);

        if (answers.size() != 0) {
            baseResponse.setData(answers);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);
        } else if (answers.size() == 0) {
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        } else {
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

}
