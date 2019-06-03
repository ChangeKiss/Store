/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.Store.www.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.Store.www.MyApplication;
import com.Store.www.entity.LoginResponse;
import com.Store.www.entity.WeChartAccessTokenResponse;
import com.Store.www.entity.WeChartLoginRequest;
import com.Store.www.entity.WeChartLoginUserInfoResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.LoginActivity;
import com.Store.www.ui.activity.MainActivity;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


/** 微信客户端回调activity示例 */
public class WXEntryActivity  extends Activity implements IWXAPIEventHandler {

	//ERR_OK = 0(用户同意) ERR_AUTH_DENIED = -4（用户拒绝授权） ERR_USER_CANCEL = -2（用户取消）
	private final static int WE_CHART_ERR_OK = 0;
	private final static int WE_CHART_ERR_AUTH_DENIED = -4;
	private final static int WE_CHART_ERR_USER_CANCEL = -2;
	private static final String SECRET = "f3d6406c0d6ed8e4fdd4d738ce217322";
	private ActivityUtils mActivityUtils;

	//****** 回调Activity要在Manifest.mxl里面设置Theme android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
	//这样下面关闭页面时用户体验会好很多

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.mWxApi.handleIntent(getIntent(),this);
		mActivityUtils = new ActivityUtils(this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	//app发送消息给微信，处理返回消息的回调
	@Override
	public void onResp(BaseResp baseResp) {
		LogUtils.d("错误码返回值=="+baseResp.errCode);
		if (baseResp instanceof SendAuth.Resp){
			SendAuth.Resp resp = (SendAuth.Resp)baseResp;
			if (resp.errCode ==0){  //用户同意登录授权
				//就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
				LogUtils.d("code=="+resp.code);
				getAccessToken(MyApplication.APP_ID,SECRET,resp.code,"authorization_code");
			}else {  //用户取消登录授权
				finish();  //取消授权此页面会显示在最上层 先把页面关掉再跳转到登录界面
				Intent intent = new Intent(this,LoginActivity.class);
				startActivity(intent);
			}
		}
	}

	/**
	 * 获取微信登录的accessToken 以及RefreshToken
	 * @param appId
	 * @param secret
	 * @param code
	 * @param grant_type
	 */
	private void getAccessToken(String appId,String secret, String code,String grant_type){
		RetrofitClient.getApiWeChart().getWeChartAccessToken(appId,secret,code,grant_type).enqueue(new UICallBack<WeChartAccessTokenResponse>() {
			@Override
			public void OnRequestFail(String msg) {

			}

			@Override
			public void OnRequestSuccess(WeChartAccessTokenResponse bean) {
				LogUtils.d("id=="+bean.getOpenid());
				LogUtils.d("Access_token=="+bean.getAccess_token());
				LogUtils.d("refresh_token=="+bean.getRefresh_token());
				LogUtils.d("errcode=="+bean.getErrcode());
				UserPrefs.getInstance().setWeChartRefreshToken(bean.getRefresh_token());
				getWeChartLoginUserInfo(bean.getAccess_token(),bean.getOpenid());
			}
		});
	}

	/**
	 * 获取用户信息
	 * @param accessToken
	 * @param openId
	 */
	private void getWeChartLoginUserInfo(String accessToken,String openId){
		RetrofitClient.getApiWeChart().getWeChartLoginUserInfo(accessToken,openId).enqueue(new UICallBack<WeChartLoginUserInfoResponse>() {
			@Override
			public void OnRequestFail(String msg) {

			}

			@Override
			public void OnRequestSuccess(WeChartLoginUserInfoResponse bean) {
				requestWeChartLogin(bean.getHeadimgurl(),bean.getNickname(),bean.getUnionid());
			}
		});
	}

	/**
	 * 根据微信返回信息发起微信登录
	 */
	private void requestWeChartLogin(String headImageUrl,String nickName,String unionid){
		WeChartLoginRequest request = new WeChartLoginRequest(headImageUrl,nickName,unionid);
		RetrofitClient.getInstances().requestWeChartLogin(request).enqueue(new UICallBack<LoginResponse>() {
			@Override
			public void OnRequestFail(String msg) {

			}

			@Override
			public void OnRequestSuccess(LoginResponse bean) {
				switch (bean.getReturnValue()){
					case 1:
						Intent intent = new Intent();
						intent.setAction("weChartLogin");
						sendBroadcast(intent);
						UserPrefs.getInstance().setLoginToken(bean.getData().getLoginToken());
						UserPrefs.getInstance().setLoginInfo(bean);
						CrashReport.setUserId(String.valueOf(bean.getData().getAgentId())); //设置BugLy的用户ID方便查看
						mActivityUtils.startActivity(MainActivity.class);
						finish();
						break;
					default:

						break;
				}
			}
		});
	}

	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null) {
			Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
			startActivity(iLaunchMyself);
		}
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 * 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}

}
