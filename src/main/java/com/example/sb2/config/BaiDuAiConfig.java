package com.example.sb2.config;

import com.baidu.aip.contentcensor.AipContentCensor;

public class BaiDuAiConfig {
    public static final String APP_ID ="20355192";

    public static final String API_KEY = "oIGtvLCsOBHNkSGANqE1FO7l";

    public static final String SECRET_KEY = "LOLwi8rsabgZj7S7Ba0l95zPDlNjXkIm";

    /*初始化客户端*/
    public static final AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
//    public static AipContentCensor getClient() {
//        // 初始化一个AipImageCensor
//
//
//        // 可选：设置网络连接参数
////        client.setConnectionTimeoutInMillis(2000);
////        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
////        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//        return client;
//    }

}
