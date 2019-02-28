package com.Store.www.utils;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具，用来校验账号和密码等是否符合基本规则
 */
public class RegexUtils {

    public static final int VERIFY_SUCCESS = 0;

    private static final int VERIFY_LENGTH_ERROR = 1;

    private static final int VERIFY_TYPE_ERROR = 2;

    /**
     * 长度为11位 只能是数字
     *
     * @param phoneNumber 注册登录的手机号
     * @return {@link #VERIFY_SUCCESS}, {@link #VERIFY_TYPE_ERROR}, {@link #VERIFY_LENGTH_ERROR}
     */
    public static int verifyPhoneNumber(@NonNull String phoneNumber) {
        if (phoneNumber.length() < 11) {
            return VERIFY_LENGTH_ERROR;
        }
        return VERIFY_SUCCESS;
    }


    /**
     * 长度在6~20之间，不能为中文
     *
     * @param password 密码
     * @return {@link #VERIFY_SUCCESS}, {@link #VERIFY_TYPE_ERROR}, {@link #VERIFY_LENGTH_ERROR}
     */
    public static int verifyPassword(@NonNull String password) {
        if (countLength(password) < 6) {
            return VERIFY_LENGTH_ERROR;
        }else {
            String regex = "[\\u4e00-\\u9fa5]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);

            if (matcher.matches()) {
                return VERIFY_TYPE_ERROR;
            }else {
                return VERIFY_SUCCESS;
            }

        }

    }

    /**
     * 将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**）。
     * 然后再获取长度。
     */
    private static int countLength(@NonNull String string) {
        string = string.replaceAll("[^\\x00-\\xff]", "**");
        return string.length();
    }


    /**
     * 长度为18位
     *
     * @param idNumber 认证的身份证号
     * @return {@link #VERIFY_SUCCESS}, {@link #VERIFY_TYPE_ERROR}, {@link #VERIFY_LENGTH_ERROR}
     */
    public static int verifyIDNumber(@NonNull String  idNumber) {
        if (idNumber.length() < 18) {
            return VERIFY_LENGTH_ERROR;
        }
        return VERIFY_SUCCESS;
    }

    //金额验证
    public static boolean isNumber(String str){
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }
}
