package com.example.sb2.kit;

import com.example.sb2.config.BaiDuAiConfig;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class BaiDuAiCheck {

    public static JSONObject checkText(String text){
        // 参数为输入文本
        JSONObject response = BaiDuAiConfig.client.textCensorUserDefined(text);
        return response;
    }

}
