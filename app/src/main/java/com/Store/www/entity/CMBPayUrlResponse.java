package com.Store.www.entity;

/**
 * Created by www on 2018/11/19.
 * 获取招行支付链接响应体
 */

public class CMBPayUrlResponse extends BaseBenTwo{

    /**
     * resultValue : 1
     * data : <!DOCTYPE html>
     <html>
     <head>
     <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
     <meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
     <meta http-equiv="Expires" content="0" />
     <meta http-equiv="Pragma"  content="no-cache" />
     <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
     <title>一网通支付[S1]</title>
     <link href="styles/Mobile_NetPay_New.css" type="text/css" rel="Stylesheet" />
     <script type="text/javascript" src="jsLib/Dictionary.js"></script>
     <script type="text/javascript">
     var isSubmit = "0";
     function Init()
     {

     submitForm();
     }
     function submitForm()
     {
     if (isSubmit == "1") return; // 已提交过，直接返回
     try
     {
     var redirectFormObj = document.getElementById("redirectForm");
     redirectFormObj.submit();
     isSubmit = "1"; // 已提交
     }
     catch(e)
     {
     alert("您的浏览器未能自动跳转，请重新提交！");
     isSubmit = "0";
     }
     }
     </script>
     </head>
     <body onload="Init()">
     <div class="ContentBgMid ProcessingTip">
     <div>正在提交请求，请稍候...</div>
     </div>
     <form id="redirectForm" action="http://121.15.180.66:801/EUserPayOS_Wallet/BaseHttp.dll?MB_EUserPay" target="_self" method="post" autocomplete="off">
     <input type="hidden" name="jsonRequestData" value="7B2272657144617461223A7B226461746554696D65223A223230313831313139313334323435222C2264617465223A223230313831313139222C22616D6F756E74223A22302E3031222C226F726465724E6F223A223230313831313139313334323435222C227369676E4E6F7469636555726C223A22687474703A2F2F7777772E6D65726368616E742E636F6D2F706174682F7061794E6F746963652E646F222C226D65726368616E7453657269616C4E6F223A223131313131313131313130222C226167724E6F223A223132333435363738303030222C227061794E6F7469636555726C223A22687474703A2F2F7777772E6D65726368616E742E636F6D2F706174682F7061794E6F746963652E646F222C226272616E63684E6F223A2230353734222C226D65726368616E744E6F223A22303030303236227D2C2263686172736574223A225554462D38222C227369676E223A2241413532324541303633454646384539424346313634314442423030363731434344413239414446343343314342433639453430463435353537443634414435222C227369676E54797065223A225348412D323536222C2276657273696F6E223A22312E30227D" />
     </form>
     </body>

     </html>
     */


    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
