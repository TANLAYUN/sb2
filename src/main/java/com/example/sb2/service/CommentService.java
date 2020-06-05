package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface CommentService {

    //修改评论状态
    BaseResponse modifyCommentState(Integer comId,Integer comState);

    //用户评论
    BaseResponse comment(Integer userId, Integer ansId, String comContent, Integer ansComId);

    //根据用户id查看评论
    BaseResponse searchCommentsByUserId(Integer userId);

    //根据回答id查看评论
    BaseResponse selectComsByAnsId(Integer ansId);

    //删除评论
    BaseResponse deletePersonalComment(Integer comId);

    //修改回答
    BaseResponse modifyPersonalComment(Integer comId, String comContent);

    //根据状态选取回答_管理员
    BaseResponse searchCommentsByState(Integer comState);

    //根据状态选取回答_用户
    BaseResponse searchCommentsByState(Integer userId, Integer comState);

    //评论已读
    BaseResponse readComment(Integer comId);
}
