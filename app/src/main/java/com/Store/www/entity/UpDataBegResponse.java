package com.Store.www.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by www on 2018/9/1.
 * 升级请求响应体
 */

public class UpDataBegResponse implements Serializable{

    /**
     * returnValue : 1
     * data : [{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822192_1517553820714_headImage.jpg","currentLevelName":"经销商","code":"K20180202822192","name":"Kivie test2","time":1535618529000,"id":1,"applyLevelName":"代理商","status":0},{"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822192_1517553820714_headImage.jpg","currentLevelName":"经销商","code":"K20180202822192","name":"Kivie test2","time":1535619658000,"id":2,"applyLevelName":"代理商","status":0},{"headPicture":null,"currentLevelName":"经销商","code":"K20180403825811","name":"郑盛4.23测试","time":1535780966000,"id":4,"applyLevelName":"代理商","status":0}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822192_1517553820714_headImage.jpg
         * currentLevelName : 经销商
         * code : K20180202822192
         * name : Kivie test2
         * time : 1535618529000
         * id : 1
         * applyLevelName : 代理商
         * status : 0
         */

        private String headPicture;
        private String currentLevelName;
        private String code;
        private String name;
        private long time;
        private int id;
        private String applyLevelName;
        private int status;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getCurrentLevelName() {
            return currentLevelName;
        }

        public void setCurrentLevelName(String currentLevelName) {
            this.currentLevelName = currentLevelName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApplyLevelName() {
            return applyLevelName;
        }

        public void setApplyLevelName(String applyLevelName) {
            this.applyLevelName = applyLevelName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
