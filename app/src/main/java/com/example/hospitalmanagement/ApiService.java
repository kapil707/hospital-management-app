package com.example.hospitalmanagement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("mobile_api.php")
    Call<ResponseBody> mobile_api(@Field("test") String test);

    @FormUrlEncoded
    @POST("login_api.php")
    Call<ResponseBody> login_api(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("create_user_api.php")
    Call<ResponseBody> create_user_api(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("mobile_hospital_api.php")
    Call<ResponseBody> mobile_hospital_api(
            @Field("id") String id);
}
