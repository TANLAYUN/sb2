package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface LikeService {
    //插入赞或踩
    BaseResponse insert(Integer ansId, Integer userId, Integer likeState);

    //取消赞或踩
    BaseResponse delete(Integer id);
}
