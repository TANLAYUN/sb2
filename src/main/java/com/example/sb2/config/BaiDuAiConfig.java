package com.example.sb2.config;

import com.baidu.aip.contentcensor.AipContentCensor;

public class BaiDuAiConfig {
    public static final String APP_ID ="20355192";

    public static final String API_KEY = "oIGtvLCsOBHNkSGANqE1FO7l";

    public static final String SECRET_KEY = "LOLwi8rsabgZj7S7Ba0l95zPDlNjXkIm";

    /*初始化客户端*/
    public static final AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);

}
