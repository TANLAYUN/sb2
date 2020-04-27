package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface CommentService {

    //修改评论状态
    BaseResponse modifyCommentState(Integer comId,Integer comState);

    //用户评论
    BaseResponse comment(Integer userId, Integer ansId, String comContent);

    //查看评论
    BaseResponse searchCommentsByUserId(Integer userId);

    //删除评论
    BaseResponse deletePersonalComment(Integer comId);

    //修改回答
    BaseResponse modifyPersonalComment(Integer comId, String comContent);
}
