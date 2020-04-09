package com.example.sb2.kit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import java.io.IOException;

public class JsonUtil {
     private static ObjectMapper objectMapper = new ObjectMapper();

        //对象转字符串
        public static <Object> String obj2String(Object obj){
            if (obj == null){
                return null;
            }
            try {
                return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //字符串转对象
        public static <Object> Object string2Obj(String str,Class<Object> clazz){
            if (StringUtils.isEmpty(str) || clazz == null){
                return null;
            }
            try {
                return clazz.equals(String.class)? (Object) str :objectMapper.readValue(str,clazz);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
}