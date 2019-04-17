package com.io.east.district.api;

import com.io.east.district.base.BaseModel;
import com.io.east.district.bean.FindPassBean;
import com.io.east.district.bean.LoginResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者： ch
 * 时间： 2016/12/27.13:56
 * 描述：
 * 来源：
 */

public interface ApiServer {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param msgCode
     * @return
     */
    @POST("login/login")
    @FormUrlEncoded
    Observable<BaseModel<LoginResponse>> LoginByRx(@Field("phone") String username, @Field("password") String password, @Field("msgCode") String msgCode);

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param msgCode
     * @param inviteCode
     * @param confirmPassword
     * @return
     */
    @POST("register/register")
    @FormUrlEncoded
    Observable<BaseModel<String>> regex(@Field("phone") String phone, @Field("password") String password, @Field("msgCode") String msgCode, @Field("inviteCode") String inviteCode, @Field("confirmPassword") String confirmPassword);

    /**
     * 找回密码
     *
     * @return
     */
    @POST("user/password/modify")
    Observable<BaseModel<String>> findpass(@Body FindPassBean mp);

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @GET("validate/sendMsg")
    Observable<BaseModel<String>> SendMsg(@Query("phone") String phone);


    /**
     * 上传头像
     *
     * @param image
     * @return
     */
    @Multipart
    @POST("user/uploadImage")
    Observable<BaseModel<String>> upload(@Part MultipartBody.Part image);


    /**
     * 设置用户信息
     */
    @POST("register/setUserInfo")
    @FormUrlEncoded
    Observable<BaseModel<String>> SetUserInfo(@Field("headImg") String headImg, @Field("nickName") String nickName);

    @Streaming
    @GET
    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     */
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**  获取人脉
     * */
    @GET("api/people")
    Observable<BaseModel<String>> people();
}
