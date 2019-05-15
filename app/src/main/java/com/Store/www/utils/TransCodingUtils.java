package com.Store.www.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 字符转码解码工具类
 */

public class TransCodingUtils {

    /**
     * 将特殊字符编码
     * @param content
     * @return
     */
    public static String Encode(String content){
        try {
            return URLEncoder.encode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 将特殊字符解码
     * @param content
     * @return
     */
    public static String Decode(String content){
        try {
            return URLDecoder.decode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
           return null;
        }
    }
}
