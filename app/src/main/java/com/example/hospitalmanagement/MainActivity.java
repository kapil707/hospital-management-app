package com.example.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        //sendSmsToServer();
        try {
            if (session.checkLogin()) {
                finish();
            } else {
                //main_page_api();

                Intent in = new Intent();
                in.setClass(MainActivity.this, Home_page.class);
                startActivity(in);
                finish();
            }
        } catch (Exception e) {
        }
    }
    private void sendSmsToServer() {
        try {

            String test = "xx";
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.mobile_api(test);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Handle success response
                        // response.body() contains the response data

                        try {
                            String res = response.body().string();
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        // Handle error response
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Handle network failures or other errors
                    Log.e("log-sms", " " + t.toString());
                }
            });
        }catch (Exception e){
            Log.e("log-sms", " " + e.toString());
        }
    }
}