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
    @POST("login_user_api.php")
    Call<ResponseBody> login_user_api(
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
    @POST("home_page_api.php")
    Call<ResponseBody> home_page_api(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("search_page_api.php")
    Call<ResponseBody> search_page_api(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("hospital_wise_doctor_api.php")
    Call<ResponseBody> hospital_wise_doctor_api(
            @Field("hospital_id") String hospital_id);

    @FormUrlEncoded
    @POST("send_user_query_api.php")
    Call<ResponseBody> send_user_query_api(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("message") String message);
}
