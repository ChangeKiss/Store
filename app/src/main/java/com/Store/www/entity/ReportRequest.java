package com.Store.www.entity;

/**
 * 举报的请求体
 */

public class ReportRequest {

    /**
     * userId : 252699
     * circleId : 123
     * content : 举报内容，字符串
     * reportType : 1
     */

    private int userId;
    private int circleId;
    private String content;
    private int reportType;

    public ReportRequest(int userId, int circleId, String content, int reportType) {
        this.userId = userId;
        this.circleId = circleId;
        this.content = content;
        this.reportType = reportType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }
}
