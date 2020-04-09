package com.example.sb2.service;

import com.example.sb2.kit.BaseResponse;

public interface CollectionService {

    //显示收藏列表
    BaseResponse displayPersonalCollections(Integer userId);

    //用户收藏问题
    BaseResponse collect(Integer colUserId, Integer colQuesId);

    //用户取消收藏
    BaseResponse deleteCollections(Integer colUserId, Integer colQuesId);
}
