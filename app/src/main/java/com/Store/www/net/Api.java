package com.Store.www.net;


import com.Store.www.entity.*;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by www on 2017/12/14.
 */
    //API接口
public interface Api {

    //资讯新闻
    @GET("api/cms/indexList")
    Call<NewsResponse>getNewsList();

    //新闻资讯更多
    @POST("api/cms/typeList")
    Call<NewsMoreResponse>  requestNewsMore(@Body NewsMoreRequest requestBody);

    //新闻详情
    @POST("api/cms/cmsInfo")
    Call<NewsDetailsResponse> requestNews(@Body NewsDetailsRequest requestBody);

    //首页轮播图
    @GET("api/cms/topList")
    Call<BannerResponse> getShopBanner();

    //轮播图详情
    @POST("api/cms/cmsInfo")
    Call<CarouselResponse> requestCarousel(@Body CarouselRequest requestBody);

    //全部的商品管理
    @GET("api/product/allProductList")
    Call<CommodityManagerResponse> getCommodity(@Query("userId")int userId,
                                                @Query("countPerPage")int countPerPage,
                                                @Query("pageIndex")int pageIndex,
                                                @Query("token")int token);

    //获取商品详情
    @GET("api/product/info")
    Call<IntroduceResponse>getIntroduce(@Query("userId")int userId,
                                        @Query("productId")int productId);

    //加入购物车
    @POST("api/order/addShoppingCart")
    Call<BaseBenTwo> requestAddShoppingCart(@Body AddShoppingrequest requestBody);

    //获取购物车信息
    @GET("api/order/getCartList")
    Call<ShoppingCartResponse> getCart(@Query("userId")int userId);

    //删除商品
    @POST("api/order/delShoppingCart")
    Call<BaseBenTwo> requestDeleteCart(@Body DeleteCartRequest requestBody);

    //获取地址信息
    @GET("api/agent/getAddressList")
    Call<LocationResponse> getLocation(@Query("userId") int userId);

    //获取我的订单
    @GET("api/v2/order/getOrderList")
    Call<MyOrderResponse> getOrderList(@Query("userId")int userId,
                                       @Query("status")int status,
                                       @Query("countPerPage")int countPerPage,
                                       @Query("pageIndex")int pageIndex);

    //获取订单详情
    @GET("api/order/getOrderInfo")
    Call<OrderDetailsResponse> getOrderDetails(@Query("orderNo")String orderNo,
                                               @Query("userId") int userId);

    //获取我的仓库
    @GET("api/repository/getAllRepositoryInfo")
    Call<MyWarehouseResponse> getWarehouse(@Query("agentCode")String agentCode);

    //获取商品库存
    @GET("api/repository/getAllProductCount")
    Call<CommodityStocksResponse> getStocks(@Query("repositoryId")int repositoryId,
                                            @Query("type")int type);
    //获取仓库商品库存
    @GET("api/repository/getProductRepositorySku")
    Call<WarehouseStocksResponse> getWarehouse(@Query("repositoryId")int repositoryId,
                                               @Query("type")int type,
                                               @Query("productNo")String productNo);


    //获取单件商品的库存
    @GET("api/repository/getProductRepositorySkuByChangeBill")
    Call<OnePieceStocksResponse> getOnePiece(@Query("repositoryId")int repositoryId,
                                             @Query("type")int type,
                                             @Query("productNo")String productNo);

    //获取内衣业绩***新
    @GET("api/achievement/getOwnAchievement")
    Call<SumResultsResponse> getSumResults(@Query("agentId")int agentId);

    //获取塑身衣业绩
    @GET("api/achievement/getOwnCorsetAchievement")
    Call<SumResultsResponse> getShapeWearResults(@Query("agentId")int agentId);

    //获取业绩明细  ***新
    @GET("api/achievement/getTeamAchievement")
    Call<TeamResultsResponse> getTeamResults(@Query("agentId")int agentId);

    //获取内衣业绩明细  ***新
    @GET("api/achievement/getAchievementDetails")
    Call<MyResultsDetailsResponse> getMyResultsDetail(@Query("agentId")int agentId,
                                                      @Query("month")String month,
                                                      @Query("year") int year);

    //获取塑身衣业绩明细
    @GET("api/achievement/getCorsetAchievementDetails")
    Call<MyResultsDetailsResponse> getMyShapeWearResults(@Query("agentId") int agentId,
                                                         @Query("month")String month,
                                                         @Query("year")int year);

    //发起修改密码
    @POST("api/agent/changePassword ")
    Call<BaseBenTwo> requestAlterPassword(@Body AlterPasswordRequest requestBody);

    //发起登录
    @POST("api/agent/login")
    Call<LoginResponse> requestLogin(@Body LoginRequest requestBody);

    //获取验证码 -->找回密码时用  找回支付密码时用
    @GET("api/v2/agent/sendModifyPasswordVerificationCode")
    Call<BaseBenTwo> getVerifyCode(@Query("mobilephone")String mobilephone);

    //(找回密码)修改密码
    @POST("api/v2/agent/modifyPassword")
    Call<BaseBenTwo> requestModifyPassword(@Body FindAlterPasswordRequest requestBody);

    //注册
    @POST("api/agent/register")
    Call<BaseBenTwo> requestRegister(@Body RegisterRequest requestBody);

    //验证推荐人
    @GET("api/agent/JSRAgent")
    Call<VerifyPeopleResponse>getReferrer(@Query("jsrCode")String jsrCode);



    //取消提货定单
    @POST("api/repository/cancelBillOrder")
    Call<BaseBenTwo> requestCancelOrder(@Body CancelOrderRequest requestBody);

    //新增提货篮
    @GET("api/repository/getBillIndexList")
    Call<NewBillResponse> getBill(@Query("userId")int userId,
                                  @Query("isNew")int isNew);

    //提货时获取单件商品的库存
    @GET("api/repository/getProductRepositorySkuByBillV2")
    Call<PickUpRepertoryResponse> getRepertory(@Query("repositoryId")int repositoryId,
                                               @Query("type")int type,
                                               @Query("productNo")String productNo);

    //加入提货篮
    @POST("api/repository/addBillCart")
    Call<BaseBenTwo> requestAddBill(@Body AddBillCartRequest requestBody);


    //代理商申请
    @POST("api/agent/upgrade")
    Call<BaseBenTwo> requestAgentApply(@Body AgentApplyRequest requestBody);

    /**
     * 提交自己的提货单
     */
    @POST("api/repository/submitBillOrder")
    Call<SubmitPickUpGoodsResponse> requestSubmitBill(@Body SubmitBillRequest requestBody);

    /**
     * 提交下级提货单
     */
    @POST("api/transfer/submitSbwBillOrder")
    Call<BaseBenTwo> requestSubmitSubordinateBill(@Body SubmitSubordinateBillRequest requestBody);

    //获取购物车详情
    @POST("api/order/getShoppingDetail")
    Call<AlterOrderDetailsResponse> requestShoppingCart(@Body ShoppingCartRequest requestBody);

    //保存购物车修改后的信息
    @POST("api/order/addShoppingCart")
    Call<BaseBenTwo> requestSaveAlter(@Body SaveAlterShoppingRequest requestBody);

    //提交订单请求
    @POST("api/order/submitOrder")
    Call<SubmitOrderResponse> requestSubmitOrder(@Body SubmitOrderRequest requestBody);

    //获取支付类型，对公还是对私
    @GET("api/pay/getPayType")
    Call<GetPayTypeResponse> getPayType(@Query("token")int token,
                                        @Query("userId")int userId);

    //发起支付宝支付请求获取支付链接  老接口     *********post请求的时候 向API中间插入值方法 ********
    @POST("zsyhpayApp/zhpay/{id}/getPayInfoPage")
    Call<AliPayResponse> requestAliPay(@Path("id") String id);

    //获取支付链接   新接口
    @GET("api/pay/getPayInfoUrl")
    Call<GetPayUrlResponse> getPayUrl(@Query("orderNumber") String orderNumber);

    //取消商品订单
    @POST("api/order/cancelOrder")
    Call<BaseBenTwo> requestCancelMyOrder(@Body CancelOrderRequest requestBody);

    //刷新获取支付状态 老接口   *********post请求的时候 向API中间插入值方法 ********
    @POST("zsyhpayApp/zhpay/{id}/searchOrderPayStatus/")
    Call<PayOverResponse> requestPayOver(@Path("id") String id);

    //刷新支付状态  新接口
    @GET("api/pay/searchOrderPayStatus")
    Call<BaseBenTwo> getPayStatus(@Query("orderNumber") String orderNumber);

    //上传截图支付
    @POST("api/order/submitPayInfo")
    Call<BaseBenTwo> requestScreenPay(@Body ScreenPayRequest requestBody);

    //我的提货单
    @GET("api/v2/bill/getBillOrderList")
    Call<PickUpGoodsResponse> getLading(@Query("userId")int userId,
                                        @Query("countPerPage")int countPerPage,
                                        @Query("pageIndex")int pageIndex,
                                        @Query("status")int status);

    //获取尺码调整单
    @GET("api/change/getChangList")
    Call<SizeAdjustResponse> getSizeBill(@Query("userId")int userId,
                                         @Query("countPerPage")int countPerPage,
                                         @Query("pageIndex")int pageIndex);

    //提交尺码调整单
    @POST("api/change/submitChangeList")
    Call<BaseBenTwo> requestAdjustSize(@Body AdjustSizeRequest requestBody);

    //获取上线新款
    @GET("api/product/getActivityProductList")
    Call<HomeNewArrivalResponse> getNewArrival(@Query("userId")String userId,
                                               @Query("token")int token);

    //获取帮助中心内容
    @GET("api/cms/problemlist")
    Call<CommonIssueResponse> getCommonIssue();

    //获取公司制度内容
    @GET("api/cms/comRolelist")
    Call<CompanySystemResponse> getSystem();

    //修改用户头像
    @POST("api/agent/modifyHeadPicture")
    Call<BaseBenTwo> requestIconEdit(@Body IconEditRequest requestBody);

    //修改地址
    @POST("api/agent/editAddress")
    Call<BaseBenTwo> requestAlterLocation(@Body AlterLocationRequest requestBody);

    //删除地址
    @POST("api/agent/deleteAddress")
    Call<BaseBenTwo> requestDeleteAddress(@Body DeleteAddressRequest requestBody);

    //选择地址
    @GET("api/city/list")
    Call<AlterAddressResponse> getProvince(@Query("type") int type,
                                           @Query("id")int id);

    //添加新地址
    @POST("api/agent/addAgentAddress")
    Call<BaseBenTwo> requestAddNew(@Body AddNewLocationRequest requestBody);

    //全选删除购物车
    @POST("api/v2/order/delShoppingCart")
    Call<BaseBenTwo> requestAllDeleteCart(@Body AllDeleteCartRequest requestBody);

    //获取提货单详情
    @GET("api/v2/bill/getBillInfoById")
    Call<PickUpDetailsResponse> getPickUpDetails(@Query("orderNumber")String orderNumber);

    //获取提货篮以及商品库存
    @GET("api/v2/bill/getBillRepository")
    Call<PickUpAndCommodityResponse> getPickUpCommodity(@Query("userId")String userId,
                                                        @Query("repositoryId")int repositoryId);

    //意见反馈
    @POST("api/v2/call/callBack")
    Call<BaseBenTwo> requestFeedBack(@Body FeedBackRequest requestBody);

   //删除订单
    @POST("api/v2/order/delOrder")
    Call<BaseBenTwo> requestDeleteOrder(@Body DeleteOrderRequest requestBody);

    //确认收货
    @POST("api/order/confirmationOrder")
    Call<BaseBenTwo> requestConfirm(@Body ConfirmOrderRequest requestBody);

    //获取购物车是否商品
    @GET("api/v2/order/getCartNumber")
    Call<CartWhetherNullResponse> getCartWhetherIsNull(@Query("userId")int userId);

    //获取上级代理列表
    @GET("api/v2/agent/findAgentByParentCode")
    Call<AgencyQueryResponse> getAgencyList(@Query("parentCode")String parentCode,
                                            @Query("pageIndex")int pageIndex,
                                            @Query("countPerPage")int countPerPage,
                                            @Query("userId")String userId);

    //获取下级代理
    @GET("api/v2/agent/findAgentByUserId")
    Call<AgencyQueryResponse> getLowerAgency(@Query("userId")String userId,
                                              @Query("pageIndex")int pageIndex,
                                              @Query("countPerPage")int countPerPage);

    //搜索代理
    @GET("api/v2/agent/searchAgent")
    Call<AgencyQueryResponse> getSearchAgency(@Query("keyword")String keyword,
                                              @Query("pageIndex")int pageIndex,
                                              @Query("countPerPage")int countPerPage,
                                              @Query("userId")String userId);

    //查自己
    @GET("api/v2/agent/findSelf")
    Call<AgencyQueryResponse> getMeAgency(@Query("userId") String userId,
                                          @Query("pageIndex")int pageIndex,
                                          @Query("countPerPage")int countPerPage);

    //销售管理
    @GET("api/sales/getSalesOrderList")
    Call<SellManageResponse> getSellManage(@Query("userId") String userId,
                                           @Query("countPerPage") int countPerPage,
                                           @Query("pageIndex") int pageIndex,
                                           @Query("status") int status);

    //全选请求
    @POST("api/v2/cart/productSelect")
    Call<BaseBenTwo> requestAllCart(@Body AllCartRequest requestBody);

    //提交结算请求
    @POST("api/v2/cart/settlement")
    Call<CloseAccountResponse> requestCloseAccount(@Body CloseAccountRequest requestBody);

    //支付确认详情
    @GET("api/sales/getPayInfo")
    Call<SellOrderPayResponse> getSellDetails(@Query("orderNo") String orderNo);

    //销售订单详情
    @GET("api/sales/getOrderInfo")
    Call<SellOrderDetailsResponse> getSellOrderDetails(@Query("userId") String userId,
                                                       @Query("orderNo") String orderNo);

    //发起确认支付
    @POST("api/sales/submitPayInfo")
    Call<BaseBenTwo> requestPayAffirm(@Body PayAffirmRequest requestBody);

    //提交物流信息
    @POST("api/sales/submitSendOut")
    Call<BaseBenTwo> requestShipments(@Body LogisticsShipmentsRequest requestBody);

    //发布圈子
    @POST("api/circle/publishCircle")
    Call<BaseBenTwo> requestPublish(@Body PublishCircleRequest requestBody);

    //获取圈子列表
    @GET("api/circle/getCircleList")
    Call<CircleResponse> getCircleList(@Query("userId") int userId,
                                       @Query("type")int type,
                                       @Query("countPerPage") int countPerPage,
                                       @Query("pageIndex") int pageIndex);

    //点赞
    @POST("api/circle/doLike")
    Call<BaseBenTwo> requestPraise(@Body PraiseRequest requestBody);

    //取消点赞
    @POST("api/circle/doNotLike")
    Call<BaseBenTwo> requestPraiseCancel(@Body PraiseRequest requestBody);

    //举报
    @POST("api/circle/report")
    Call<BaseBenTwo> requestReportRequest(@Body ReportRequest requestBody);

    //生成证书
    @POST("api/cert/publishCert")
    Call<CreateCredentialResponse> requestCreate(@Body CreateCredentialRequest requestBody);

    //获取代理证书
    @GET("api/cert/getAgentCert")
    Call<JudgeCredentialResponse> requestJudge(@Query("agentId") int agentId);

    //获取二维码
    @GET("api/agent/getCode")
    Call<QRCodeResponse> getQRCode (@Query("userId")int userId);

    //获取自己的收款信息
    @GET("api/v2/order/getMyPayPrompt")
    Call<MyPayContentResponse> getMyPay(@Query("userId")int userId);

    //设置自己的收款信息
    @POST("api/v2/order/setMyPayPrompt")
    Call<BaseBenTwo> setPayContent(@Body SetMyPayContentRequest requestBody);

    //获取上级的收款信息
    @GET("api/v2/order/getOrderPayPrompt")
    Call<MyPayContentResponse> getSuperiorPayContent(@Query("userId")int userId);

    //获取新闻评论
    @GET("api/newsComment/getCommentList")
    Call<NewsCommentResponse> getNewsComment(@Query("userId")int userId,
                                             @Query("textId")int textId,
                                             @Query("countPerPage")int countPerPage,
                                             @Query("pageIndex")int pageIndex);

    //发表评论
    @POST("api/newsComment/publishComment")
    Call<BaseBenTwo> requestPublishComment(@Body PublishCommentRequest requestBody);

    //删除评论
    @POST("api/newsComment/deleteComment")
    Call<BaseBenTwo> requestDeleteComment(@Body DeleteCommentRequest requestBody);

    //获取指定圈子评论
    @GET("api/circle/getCommentCircleById")
    Call<LookCommentResponse> getCircleComment(@Query("circleId")int circleId,
                                               @Query("countPerPage")int countPerPage,
                                               @Query("pageIndex")int pageIndex);

    //新增评论
    @POST("api/circle/addComment")
    Call<BaseBenTwo> requestNewComment(@Body NewCommentRequest requestBody);

    //修改微信
    @POST("api/agent/updateWeChat")
    Call<BaseBenTwo> requestAlterWeChar(@Body AlterWeCharRequest requestBody);

    //获取余额
    @GET("api/balance/getAgentBalance")
    Call<BalanceResponse> getMyBalance(@Query("agentId")int agentId);

    //获取余额明细
    @GET("api/balance/getAgentBalanceDetail")
    Call<BalanceDetailResponse> getBalanceDetail(@Query("agentId")int agentId,
                                                 @Query("month")int month,
                                                 @Query("year")int year,
                                                 @Query("type") int type);

    //获取银行卡列表
    @GET("api/balance/getAgentBankCard")
    Call<BankCardListResponse> getBankCard(@Query("agentId")int agentId);

    //添加银行卡
    @POST("api/balance/addBankCard")
    Call<BaseBenTwo> requestAddBankCard(@Body AddBankCardRequest requestBody);

    //删除银行卡
    @POST("api/balance/delBankCard")
    Call<BaseBenTwo> requestDeleteBankCard(@Body DeleteBankCardRequest requestBody);

    //是否有密码
    @POST("api/balance/judgePassWord")
    Call<BaseBenTwo> requestPassword(@Body WhetherPasswordRequest requestBody);

    //提现申请
    @POST("api/balance/addWithdraw")
    Call<BaseBenTwo> requestWithdrawDeposit(@Body WithdrawDepositRequest requestBody);

    //设置支付密码
    @POST("api/balance/setPaymentPassword")
    Call<BaseBenTwo> requestSettingPassword(@Body SettingPasswordRequest requestBody);

    //查看账户余额收支明细
    @GET("api/balance/getInsideDetail")
    Call<AccountBalanceDetailResponse> getAccountBalance(@Query("id")int id,
                                                         @Query("type")int type);

    //修改支付密码
    @POST("api/balance/modifyPassword")
    Call<BaseBenTwo> requestAlterPayPassword(@Body AlterPayPasswordRequest requestBody);

    //余额支付
    @POST("api/order/balancePayment")
    Call<BalancePayResponse> requestBalancePay(@Body BalancePayRequest requestBody);

    //退出登录
    @POST("api/agent/loginOut")
    Call<BaseBenTwo> requestLoginOut(@Body LoginOutRequest requestBody);

    //获取可用的支付方式
    @GET("api/order/getChd")// 正式服是 getPayMode ******
    Call<PayTypeResponse> getUsablePayType(@Query("agentId")int agentId,
                                           @Query("orderNo")String orderNo);

    //建行支付
    @GET("api/pay/getCCBPay")
    Call<CCBPayResponse> getCCBPay(@Query("orderNumber")String orderNumber);

    //获取修改手机号验证码
    @GET("api/v2/agent/sendModifyMobilePhoneVerificationCode")
    Call<AlterVerifyResponse> getAlterPhoneVerifyCode(@Query("mobilephone")String mobilephone);

    //验证旧的手机验证码
    @POST("api/agent/judgeCode")
    Call<BaseBenTwo> requestOldVerifyCode(@Body VerifyOldCodeRequest requestBody);

    //修改手机号码
    @POST("api/agent/updatePhoneNumber")
    Call<BaseBenTwo> requestAlterNewPhone(@Body VerifyOldCodeRequest requestBody);

    //获取个人信息
    @GET("api/agent/getAgentDetail")
    Call<AgentDataResponse> getAgentData(@Query("agentId")int agentId);

    //创建京东支付
    @POST("api/pay/createJdPay")
    Call<JDPayResponse> requestJDPay(@Body JDPayRequest requestBody);

    //提交京东支付
    @POST("api/pay/submitJdPay")
    Call<BaseBenTwo> requestSubmitJDPay(@Body SubmitJDPayRequest requestBody);

    //分红查询
    @GET("api/bonus/getOwnBonus")
    Call<BonusQueryResponse> queryBonus(@Query("agentCode") String agentCode);

    //分红详情
    @GET("api/bonus/getBonusDetail")
    Call<BonusDetailResponse> getBonusDetail(@Query("agentCode") String agentCode,
                                             @Query("month") int month);

    //获取广告
    @GET("api/newsComment/getPosterPush")
    Call<AdvertisingResponse> getAdvertising();

    //查看物流
    @POST("api/logistics/getLogisticsInfoByOrder")
    Call<LookLogisticsResponse> requestLookLogistics(@Body LookLogisticsRequest requestBody);

    //获取APP升级信息
    @GET("api/versionController/getVersionInfo.json")
    Call<AppUpDataResponse> getAppUpData();

    //获取代理等级信息
    @GET("api/agent/getGradePowerDetail")
    Call<AgencyUpDataResponse> getAgencyUpData(@Query("agentId")int agentId);

    //获取升级请求
    @GET("api/agent/getApplyList")
    Call<UpDataBegResponse>  getUpBeg(@Query("agentId")int agentId,
                                      @Query("countPerPage")int countPerPage,
                                      @Query("pageIndex")int pageIndex);

    //同意升级
    @POST("api/agent/agree")
    Call<BaseBenTwo> requestConsentUp(@Body ConsentUpRequest requestBody);

    //拒绝升级
    @POST("api/agent/refuse")
    Call<BaseBenTwo> requestRejectUp(@Body RejectUpRequest requestBody);

    //提交等级升级申请
    @POST("api/agent/apply")
    Call<BaseBenTwo> requestSubmitBeg(@Body SubmitUpBegRequest requestBody);

    //添加实体仓库
    @POST("api/physicalWarehouse/addNewWarehouse")
    Call<BaseBenTwo> requestAddWarehouse(@Body AddWarehouseRequest requestBody);

    //获取实体仓库列表
    @GET("api/physicalWarehouse/getPhysicalWarehouse")
    Call<EntityWarehouseResponse> getEntityWarehouse(@Query("agentId") int agentId);

    //删除实体仓库
    @POST("api/physicalWarehouse/delWarehouse")
    Call<BaseBenTwo> requestDeleteWarehouse(@Body DeleteEntityWarehouseRequest requestBody);

    //修改实体仓库
    @POST("api/physicalWarehouse/updateWarehouse")
    Call<BaseBenTwo> requestAlterEntityWarehouse(@Body AlterEntityWarehouseRequest requestBody);

    //获取实体仓库商品库存
    @GET("api/physicalWarehouse/getProduct")
    Call<EntityWarehouseStocksResponse> getEntityStocks(@Query("warehouseId") int warehouseId,
                                                        @Query("page") int page,
                                                        @Query("countPerPage") int countPerPage);

    //获取实体仓库单件商品库存
    @GET("api/physicalWarehouse/getProColSize")
    Call<EntitySingletonStockResponse> getEntitySingletonStock(@Query("productId")int productId,
                                                      @Query("warehouseId") int warehouseId);

    //新增保存实体仓库商品数量
    @POST("api/physicalWarehouse/addProductPhysicalWarehouse")
    Call<BaseBenTwo> requestSaveEntitySingletonStock(@Body SaveSingletonStockRequest requestBody);

    //获取国家地区编码
    @GET("api/agent/getPhonePrefix")
    Call<CountryCodeResponse> getCountryCode();

    //保存出库商品数量
    @POST("api/physicalWarehouse/addProductOutbound")
    Call<BaseBenTwo> requestSaveComeStock(@Body SaveSingletonStockRequest requestBody);

    //获取未出库产品
    @GET("api/physicalWarehouse/getNotOutOrder")
    Call<GainNotComeStockResponse> getNotComeStock(@Query("warehouseId") int warehouseId);

    //修改出库商品
    @POST("api/physicalWarehouse/updateProductOutbound")
    Call<BaseBenTwo> requestAlterComeStock(@Body AlterComeStockRequest requestBody);

    //确认出库 添加零售记录
    @POST("api/physicalWarehouse/addOutbound")
    Call<BaseBenTwo> requestAffirmCome(@Body AffirmComeStockRequest requestBody);

    //清空出库记录
    @POST("api/physicalWarehouse/delProductOutbound")
    Call<BaseBenTwo> requestCleanComeStack(@Body CleanComeStockRequest requestBody);

    //获取零售记录
    @GET("api/physicalWarehouse/getOutList")
    Call<RetailRecordResponse> getRetailRecord(@Query("agentId")int agentId,
                                               @Query("pageIndex") int pageIndex,
                                               @Query("size") int size);

    //扫描成功回调接口
    @POST("api/codeRepository/scanCode")
    Call<BaseBenTwo> requestScanCode(@Body ScanCodeCallBackRequest requestBody);

    //获取商品提示
    @GET("api/remind/getProductRemind")
    Call<CommodityHintResponse> getCommodityHint(@Query("list") int[] cardId);

    //获取商品扫描记录时间
    @GET("api/codeRepository/getDateList")
    Call<ScanRecordTimeResponse> getScanRecordTime(@Query("agentCode") String agentCode);

    //获取商品扫描记录明细
    @GET("api/codeRepository/getDateInfoList")
    Call<ScanRecordDetailResponse> getScanContentDetail(@Query("agentCode") String agentCode,
                                                        @Query("date") String date);

    //获取招行链接
    @POST("api/pay/getOneNetPay")
    Call<CMBPayUrlResponse> getCmbPayUrl(@Body CMBPayRequest requestBody);

    //获取上级云仓库库存数量
    @POST("api/order/getCloudRepository")
    Call<SuperiorsCloudKcResponse> requestCloudRepository(@Body SuperiorsCloudKcRequest requestBody);

    //获取保证金
    @GET("api/agent/getBoundInfo")
    Call<MarginResponse> getMarginStatus(@Query("agentId") int agentId);


    //支付保证金
    @POST("api/agent/payBoundOrder")
    Call<CMBPayUrlResponse> requestPayBound(@Body PayBoundRequest requestBody);

    //我的团队详情
    @GET("api/achievement/getTeamDetail")
    Call<MyTeamDetailResponse> getMyTeamDetail(@Query("agentId")int agentId,
                                               @Query("month")String month,
                                               @Query("year")String year);

    //获取每日团队业绩
    @GET("api/achievement/getDayTeamMoney")
    Call<EveryDayGradeResponse> getEveryDayTeamGrade(@Query("agentId") int agentId,
                                                     @Query("date") String date);

    //直属下级团队业绩
    @GET("api/achievement/getSubTeamMoney")
    Call<SubordinateGradeResponse> getSubordinateGrade(@Query("agentId") int agentId,
                                                       @Query("date") String date,
                                                       @Query("pageIndex")int pageIndex,
                                                       @Query("countPerPage")int countPerPage);

    //每日招新人数
    @GET("api/achievement/getOwnRecruitNew")
    Call<EveryDayNewPeopleResponse> getNewPeople(@Query("agentId") int agentId,
                                                 @Query("date") String date);

    //直属下级团队人员状况
    @GET("api/achievement/getSubRecruitNew")
    Call<SubordinateTeamPeopleResponse>  getSubordinateTeamPeople(@Query("agentId") int agentId,
                                                                  @Query("date") String date,
                                                                  @Query("pageIndex")int pageIndex,
                                                                  @Query("countPerPage")int countPerPage);

    //获取内衣各等级人员分布
    @GET("api/achievement/getRankPersonnel")
    Call<BraEachLevelPeopleResponse>  getEachLevelPeople(@Query("agentId")int agentId);

    //获取塑身衣各等级人员分布
    @GET("api/achievement/getIShowRankPersonnel")
    Call<ShapeWearEachLevelPeopleResponse> getShapeWearEachLevelPeople(@Query("agentId") int agentId);

    //获取下级各等级人员分布
    @GET("api/achievement/getSubRankPersonnel")
    Call<SubordinateTeamPeopleDistributionResponse> getSubordinationTeamDistribution(@Query("agentId") int agentId,
                                                                                     @Query("pageIndex")int pageIndex,
                                                                                     @Query("countPerPage")int countPerPage);

    //获取资金回退订单
    @GET("api/balance/getOrderBalanceIncome")
    Call<OrderFundRollBackResponse> getFundOrderRollBack(@Query("agentId") int agentId,
                                                         @Query("countPerPage") int countPerPage,
                                                         @Query("pageIndex")int pageIndex);

    //提现到银行卡
    @POST("api/balance/ConnectBankEnterprise")
    Call<BaseBenTwo> requestWithdrawBackCard(@Body WithdrawBankCardRequest requestBody);

    //提现订单回退
    @POST("api/balance/corsetWithdraw")
    Call<BaseBenTwo> requestWithdrawOrderBack(@Body WithdrawOrderBackRequest requestBody);

    //获取塑身衣证书
    @GET("api/cert/getAgentIShowCert")
    Call<ShapeWearCredentialResponse>  getShapeWearCredential(@Query("agentId") int agentId);

    //生成塑身衣证书
    @POST("api/cert/publishIShowCert")
    Call<CreateCredentialResponse> requestCreateShapeWearCredential(@Body CreateCredentialRequest requestBody);

    //支付运费
    @POST("api/repository/payPickBillExpress")
    Call<CMBPayUrlResponse> requestPayExpress(@Body PayExpressRequest requestBody);

    //获取下级扫码列表
    @GET("api/agent/order/getAgentOrderList")
    Call<SubordinateScanResponse> getSubordinateScan(@Query("userId")int userId,
                                                     @Query("pageIndex")int pageIndex,
                                                     @Query("countPerPage")int countPerPage);

    //资金申请是判断是否是台湾代理
    @GET("api/balance/judgeTaiwanAgent")
    Call<JudgeTaiWanAgentResponse> getTaiWanAgent(@Query("agentId")int agentId);

    //出库扫码
    @POST("api/codeRepository/superiorScanCode")
    Call<BaseBenTwo> requestOutScanCode(@Body OutScanCodeRequest requestBody);

    /**
     * 查询出库下级信息
     */
    @GET("api/physicalWarehouse/getAgentByCode")
    Call<QueryOutSubordinateResponse> getOutSubordinate(@Query("agentCode")String agentCode);

    /**
     * 获取提货提醒
     */
    @GET("api/transfer/getRemind")
    Call<PickUpRemindResponse> getPickUpRemind();

    /**
     * 获取相册
     */
    @GET("api/album/getAlbumList")
    Call<CompanyPhotoResponse> getPhotoAlbum(@Query("pageIndex")int pageIndex,
                                             @Query("countPerPage")int countPerPage);

    /**
     * 获取相册的照片
     */
    @GET("api/album/getPhotosByAlbumId")
    Call<PhotoAlbumPictureResponse> getPhotoAlbumPicture(@Query("albumId")int albumId,
                                                         @Query("pageIndex")int pageIndex,
                                                         @Query("countPerPage")int countPerPage);

    /**
     * 获取我的圈子
     */
    @GET("api/circle/getMyCircleList")
    Call<MyCircleResponse> getMyCircles(@Query("countPerPage")int countPerPage,
                                        @Query("pageIndex")int pageIndex,
                                        @Query("type") int type,
                                        @Query("userId")int userId);

    /**
     * 获取首页新闻
     */
    @GET("api/newsNotice/getNewsNoticeList")
    Call<HomeNewsResponse> getHomeNews(@Query("pageIndex")int pageIndex,
                                       @Query("countPerPage")int countPerPage);


    /**
     * 获取首页活动
     */
    @GET("api/activityNotice/getActivityNoticeList")
    Call<HomeActivityResponse> getHomeNotice(@Query("pageIndex")int pageIndex,
                                             @Query("countPerPage")int countPerPage);

    /**
     * 获取首页圈子
     */
    @GET("api/circle/getCircleNoticeList")
    Call<HomeCircleResponse> getHomeCircle();

    /**
     * 获取热门圈子
     */
    @GET("api/circle/getPopularCircleList")
    Call<MyCircleResponse> getHotCircle(@Query("pageIndex")int pageIndex,
                                        @Query("countPerPage")int countPerPage,
                                        @Query("userId")int userId);

    /**
     * 获取产品轮播
     */
    @GET("api/product/productChartList")
    Call<ProductBannerResponse> getProductBanner();

    /**
     * 获取活动商品集
     */
    @GET("api/product/getChartProduct")
    Call<CommodityManagerResponse> getProductList(@Query("chartId") int chartId);

    /**
     * 获取微信登录的access_token
     */
    @GET("sns/oauth2/access_token")
    Call<WeChartAccessTokenResponse> getWeChartAccessToken(@Query("appid")String appid,
                                                           @Query("secret")String secret,
                                                           @Query("code")String code,
                                                           @Query("grant_type") String grant_type);

    /**
     * 获取微信登录成功的用户信息
     */
    @GET("sns/userinfo")
    Call<WeChartLoginUserInfoResponse> getWeChartLoginUserInfo(@Query("access_token")String access_token,
                                                               @Query("openid") String openid);

    /**
     * 验证码登录
     */
    @POST("api/agent/codeLogin")
    Call<LoginResponse> requestCodeLogin(@Body CodeLoginRequest requestBody);

    /**
     * 微信登录成功后服务器通知
     */
    @POST("api/agent/wxLogin")
    Call<LoginResponse> requestWeChartLogin(@Body WeChartLoginRequest requestBody);


}
