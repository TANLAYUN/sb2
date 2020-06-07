package com.example.sb2.kit;

public enum ResultCodeEnum
{

    SITES_OPEN("1001","网页打开成功"),
    INTERNTE_FAILURE("1002","网络错误，请重试"),
    UNKOWN_ERROE("1003","未知的错误"),
    REQUEST_NO_PARAM_ID_ERROR("1004","页面请求参数错误"),
    DB_SYS_ERROR("1005","数据库错误"),
    RECORD_NO_EXIST("1006","记录不存在"),

    ACCOUNT_LOGGED_IN("1007","账号已经登录"),


    DB_CONNECTION_SUCCESS("2000","数据库连接成功"),
    DB_CONNECTION_FAILURE("2001","数据库连接失败"),
    DB_UPDATE_SUCCESS("2002","数据库修改成功"),
    DB_UPDATE_ERROR("2003","数据库修改失败"),
    DB_ERROR_OVERFLOW("2004","数据库修改失败_字段字数超过规定"),
    DB_ERROR_FORMAT("2005","数据库修改失败_字段输入数据格式错误"),

    //
    DB_FIND_SUCCESS("2006","数据库查找成功"),
    DB_FIND_FAILURE("2007","数据库查找失败，没有该条记录"),
    //

    DB_WRONG_NULL_WRONG_PARA("2008","该次查询结果为空_输入参数错误"),
    DB_DELETE_SUCCESS("2009","数据删除成功"),
    DB_DELETE_FAILURE("2010","数据删除失败"),
    DB_WRONG_NULL("2011","请求参数为空"),


    BEST_ANS_UPDATE_SUCCESS("2008","最佳答案修改成功"),
    BEST_ANS_UPDATE_FAILURE_USER_NOT_EXIST("2009","最佳答案修改失败_用户不存在"),
    BEST_ANS_UPDATE_FAILURE_ANS_NOT_EXIST("2010","最佳答案修改失败_回答不存在"),
    BEST_ANS_UPDATE_FAILURE_DB_ERROR("2011","最佳答案修改失败_数据库错误"),

    //
    QUESTION_ADD_FAILURE_INSUFFICIENT_CAPITAL("2014","问题添加失败_积分不足"),
    QUESTION_ADD_SUCCESS("2015","问题添加成功"),
    QUESTION_ADD_FAILURE_DB_ERROR("2016","问题添加失败_数据库错误"),

    //
    ANSWER_ADD_SUCCESS("2017","回答添加成功"),
    ANSWER_ADD_FAILURE_DB_ERROR("2018","回答添加失败_数据库错误"),

    //
    COMMENT_ADD_SUCCESS("2019","评论添加成功"),
    COMMENT_ADD_FAILURE_DB_ERROR("2020","评论添加失败_数据库错误"),

    //
    QUESTION_DELETE_SUCCESS("2021","问题删除成功"),
    QUESTION_DELETE_FAILURE_DB_ERROR("2022","问题删除失败_数据库错误"),
    QUESTION_DELETE_FAILURE_NOT_EXIST("2023","问题删除失败_不存在"),

    //
    ANSWER_DELETE_SUCCESS("2024","回答删除成功"),
    ANSWER_DELETE_FAILURE_DB_ERROR("2025","回答删除失败_数据库错误"),
    ANSWER_DELETE_FAILURE_NOT_EXIST("2026","回答删除失败_不存在"),

    //
    COMMENT_DELETE_SUCCESS("2027","评论删除成功"),
    COMMENT_DELETE_FAILURE_DB_ERROR("2028","评论删除失败_数据库错误"),
    COMMENT_DELETE_FAILURE_NOT_EXIST("2029","评论删除失败_不存在"),

    //
    ANSWER_UPDATE_SUCCESS("2034","回答更新成功"),
    ANSWER_UPDATE_FAILURE_DB_ERROR("2035","回答更新失败_数据库错误"),
    ANSWER_UPDATE_FAILURE_NOT_EXIST("2036","回答更新失败_不存在"),

    //
    COMMENT_UPDATE_SUCCESS("2037","评论更新成功"),
    COMMENT_UPDATE_FAILURE_DB_ERROR("2038","评论更新失败_数据库更新失败"),
    COMMENT_UPDATE_FAILURE_NOT_EXIST("2039","评论更新失败_不存在"),

    //
    COLLECTION_ADD_SUCCESS("2042","收藏成功"),
    COLLECTION_ADD_FAILURE_DB_REEOR("2043","收藏失败_数据库错误"),
    COLLECTION_EXISTED("2044","收藏失败_您已收藏了本条知识"),

    //
    QUES_ANS_STATE_UPDATE_SUCCESS("2045","问题解决状态更新成功"),
    QUES_ANS_STATE_UPDATE_FAILURE_DB_ERROR("2046","问题解决状态更新失败_数据库错误"),
    QUES_ANS_STATE_UPDATE_FAILURE_NOT_EXIST("2047","问题解决状态更新失败_不存在"),
    QUES_ANS_STATE_UPDATE_FAILURE_WRONG_STATE("2048","问题解决状态更新失败_错误的问题解决状态"),

    //
    COLLECTION_DELETE_SUCCESS("2050","收藏删除成功"),
    COLLECTION_DELETE_FAILURE_DB_ERROR("2051","收藏删除失败_数据库错误"),
    COLLECTION_NO_EXIST("2052","收藏删除失败_您没有收藏过"),

    //
    ANSWER_FIND_FAILURE_NO_USER("2060","回答查找失败_没有该用户"),
    ANSWER_FIND_FAILURE_NO_QUES("2062","回答查找失败_没有该问题"),
    COMMENT_FIND_FAILURE_NO_USER("2063","评论查找失败_没有该用户"),
    COMMENT_FIND_FAILURE_NO_ANS("2064","评论查找失败_没有该回答"),

    GOOD_SUCCESS("2070","点赞成功"),
    GOOD_FAILURE_ANS_NOT_EXIST("2071","点赞失败_回答不存在"),
    GOOD_FAILURE_USER_NOT_EXIST("2072","点赞失败_用户不存在"),
    GOOD_FAILURE_ALREADT_EXIST("2073","点赞失败_已经点过赞了或已经踩过了"),

    BAD_SUCCESS("2080","踩成功"),
    BAD_FAILURE_ANS_NOT_EXIST("2081","踩失败_回答不存在"),
    BAD_FAILURE_USER_NOT_EXIST("2082","踩失败_用户不存在"),
    BAD_FAILURE_ALREADT_EXIST("2083","踩失败_已经点过赞了或已经踩过了"),

    CANCEL_SUCCESS("2084","取消成功"),
    CANCEL_FAILURE_NOT_EXIST("2085","取消失败_没有点赞或者踩的记录"),
    WRONG_LIKE_STATE("2088","点赞或踩失败_错误的状态"),


    UPLOAD_SUCCESS("3000","上传成功"),
    UPLOAD_FAILURE_NO_FILE("3001","上传失败_文件不存在"),
    UPLOAD_FAILURE_FILE_TOO_BIG("3002","上传失败_文件大小不能大于10M"),
    UPLOAD_FAILURE_FORMAT_ERROR("3003","上传失败_请选择jpg,jpeg,gif,png格式的图片"),
    UPLOAD_FAILURE_SAVE_ERROR("3004","上传失败_保存文件失败"),
    UPLOAD_FAILURE_NO_USER("3005","上传失败_用户不存在"),
    UPLOAD_FAILURE_DB_ERROR("3006","上传失败_数据库错误"),

    //
    REPORT_ADD_SUCCESS("3010","举报添加成功"),
    REPORT_ADD_FAILURE("3011","举报添加失败"),

    //
    LOGIN_SUCCESS("4000","登录成功"),
    LOGIN_FAILURE_PWD_ERROR("4001","登录失败_密码错误"),
    LOGIN_FAILURE_NO_EXIST_USER("4002","登录失败_用户不存在"),
    LOGIN_FAILURE_INVALID_USER_STATE("4003","登录失败_用户未审核或审核未通过"),
    LOGIN_FAILURE_WRONG_VERIFYCODE("4004", "登录失败_验证码错误"),
    //

    LOGOUT_SUCCESS("4005","退出登录成功"),
    NO_LOGIN_USER("4006","退出登录失败_用户未登录"),
    LOGIN_FAILURE_CODE_ERROR("4008", "登录失败_验证码错误"),

    //
    INFO_UPDATE_SUCCESS("5000","信息修改成功"),
    INFO_UPDATE_FAILURE_PWD_ERROR("5001","信息修改失败_密码错误"),
    INFO_UPDATE_FAILURE_NO_EXIST_USER("5002","信息修改失败_该用户不存在"),
    INFO_UPDATE_FAILURE_USER_MAIL_EXIST("5003","信息修改失败_用户邮箱已被使用"),
    INFO_UPDATE_FAILURE_DB_UPDATE_ERROR("5004", "信息修改失败_数据库更新失败"),

    //
    ROLE_UPDATE_SUCCESS("5007","权限修改成功"),
    ROLE_UPDATE_FAILURE("5008","权限修改失败_数据库错误"),
    ROLE_UPDATE_FAILURE_NO_USER("5009","权限修改失败_用户不存在"),

    //
    STATE_CHANGE_SUCCESS("6000","状态修改成功"),
    STATE_CHANGE_FAILURE_UPDATE_DB_ERROE("6001","状态修改失败_更新数据库错误"),
    STATE_CHANGE_FAILURE_INVALID_AUDIT_STATE("6002","状态修改失败_用户未经审核"),
    STATE_CHANGE_FAILURE_USER_BLOCKED("6003","状态修改失败_用户已被拉黑"),
    STATE_CHANGE_FAILURE_NO_EXIST_USER("6004","状态修改失败_用户不存在"),
    STATE_CHANGE_FAILURE_NO_CHANGE("6005","状态修改失败_状态和请求一致"),
    STATE_CHANGE_FAILURE_NO_EXIST_QUESTION("6006","状态修改失败_问题不存在"),
    STATE_CHANGE_FAILURE_NO_EXIST_ANSWER("6007","状态修改失败_回答不存在"),
    STATE_CHANGE_FAILURE_NO_EXIST_COMMENT("6008","状态修改失败_评论不存在"),
    STATE_CHANGE_FAILURE_AUDIT_FAILED("6009","状态修改失败_用户未通过审核"),

    //
    NOTICE_SEND_SUCCESS("7000","通知发送成功"),
    NOTICE_SEND_FAILURE_MAIL_SYS_ERROR("7001","通知发送失败（部分用户未成功通知）"),
    NOTICE_SEND_FAILURE_FIRM_NOT_EXIST("7002","通知发送失败_用户不存在"),

    //
    CONFIRM_CODE_SEND_SUCCESS("7003","验证码发送成功"),
    CONFIRM_CODE_SEND_FAILURE("7004","验证码发送失败"),


    //
    REGISTER_SUCCESS("8000", "注册成功"),
    REGISTER_FAILURE_USER_MAIL_EXIST("8001","注册失败_用户邮箱已经存在"),
    REGISTER_FAILURE_USER_UNCHECKED("8002","注册失败_用户待审核"),
    REGISTER_FAILURE_DB_ERROR("8003","注册失败_数据库错误"),
    REGISTER_FAILURE_CONFIRM_CODE_ERROR("8005","注册失败_验证码错误"),
    REGISTER_FAILURE_NOT_APPLIED_MAIL_CODE("8006","注册失败_没有获取邮箱验证码");


    private String code;
    private String desc;

    ResultCodeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public String getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }


}

