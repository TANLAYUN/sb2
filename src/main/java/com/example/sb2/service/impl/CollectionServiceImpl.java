package com.example.sb2.service.impl;

import com.example.sb2.entity.Collection;
import com.example.sb2.entity.Question;
import com.example.sb2.kit.BaseResponse;
import com.example.sb2.kit.ResultCodeEnum;
import com.example.sb2.mapper.collectionMapper;
import com.example.sb2.mapper.questionMapper;
import com.example.sb2.service.CollectionService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private collectionMapper collectionmapper;
    @Autowired
    private questionMapper questionmapper;

    public BaseResponse displayPersonalCollections(Integer userId){

        BaseResponse baseResponse = new BaseResponse();
        List<Collection> collections = collectionmapper.selectByColUserId(userId);
        List<Question> questions = new ArrayList<>();

        int i;
        if(collections.size()!=0){

            for(i=0;i<collections.size();i++){
                Question question=questionmapper.selectByPrimaryKey(collections.get(i).getColQuesId());
                questions.add(i,question);
                System.out.println("question的标题："+question.getQuesTitle());
                System.out.println("questions的内容："+questions.toString());
            }

            baseResponse.setData(questions);
            baseResponse.setResult(ResultCodeEnum.DB_FIND_SUCCESS);//数据库查找成功

        }else if(collections.size()==0){
            baseResponse.setResult(ResultCodeEnum.DB_FIND_FAILURE);//没有记录
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);//不知道的错误
        }

        return baseResponse;
    }

    //用户收藏问题
    public BaseResponse collect(Integer colUserId, Integer colQuesId){
        BaseResponse baseResponse = new BaseResponse();
        Collection collection = collectionmapper.selectByUserIdAndQuesId(colUserId,colQuesId);
        Question question = questionmapper.selectByPrimaryKey(colQuesId);

        if(collection == null && question != null){
            int ques_col_num = question.getQuesColNum();
            int a = collectionmapper.insert(colUserId,colQuesId);
            int b = questionmapper.updateQuesColNumByQuesId(colQuesId,ques_col_num+1);
            if(a == 1 && b == 1){
                baseResponse.setResult(ResultCodeEnum.COLLECTION_ADD_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.COLLECTION_ADD_FAILURE);
            }
        }else if(collection != null){
            baseResponse.setResult(ResultCodeEnum.COLLECTION_EXISTED);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }

    //用户取消收藏
    public BaseResponse deleteCollections(Integer colUserId, Integer colQuesId){

        BaseResponse baseResponse = new BaseResponse();
        Collection collection = collectionmapper.selectByUserIdAndQuesId(colUserId,colQuesId);
        Question question = questionmapper.selectByPrimaryKey(colQuesId);

        if(collection != null && question != null){
            System.out.println("问题&收藏不是空");
            int ques_col_num = question.getQuesColNum();
            System.out.println("问题的收藏数据："+ques_col_num);
            int a = collectionmapper.deleteByUserIdAndQuesId(colUserId,colQuesId);
            int b = questionmapper.updateQuesColNumByQuesId(colQuesId,ques_col_num-1);
            System.out.println("收藏删除情况："+a+"+问题收藏数减一情况:"+b);
            if(a == 1 && b == 1){
                baseResponse.setResult(ResultCodeEnum.COLLECTION_DELETE_SUCCESS);
            }else{
                baseResponse.setResult(ResultCodeEnum.COLLECTION_DELETE_FAILURE);
            }
        }else if(collection == null){
            baseResponse.setResult(ResultCodeEnum.COLLECTION_NO_EXIST);
        }else{
            baseResponse.setResult(ResultCodeEnum.UNKOWN_ERROE);
        }

        return baseResponse;
    }
}
