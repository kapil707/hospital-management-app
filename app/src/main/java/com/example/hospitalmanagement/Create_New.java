package com.example.hospitalmanagement;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_New extends AppCompatActivity {
    Button create_new_btn,create_new_btn1;
    TextView go_back_btn,alert;
    EditText enter_name1,user_name1, phone_number1,password;
    String enter_name = "",user_name = "", phone_number = "";
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new);
        //getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        create_new_btn = findViewById(R.id.create_new_btn);
        create_new_btn1 = findViewById(R.id.create_new_btn1);

        alert = (TextView) findViewById(R.id.user_alert);
        enter_name1 = (EditText) findViewById(R.id.enter_name1);
        user_name1 = (EditText) findViewById(R.id.user_name1);
        phone_number1 = (EditText) findViewById(R.id.phone_number1);
        password = (EditText) findViewById(R.id.user_password);
        go_back_btn = findViewById(R.id.go_back_btn);
        go_back_btn.setText(Html.fromHtml("Already have an account? <font color='#27ae60'>Login</font>"));

        go_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        create_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    enter_name = enter_name1.getText().toString();
                    user_name = user_name1.getText().toString();
                    phone_number = phone_number1.getText().toString();

                    if (user_name.length() > 0) {
                        if (phone_number.length() > 0) {
                            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo ni = cm.getActiveNetworkInfo();
                            if (ni != null) {
                                try {
                                    get_create_new_api();
                                    create_new_btn.setVisibility(View.GONE);
                                    create_new_btn1.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }

                            } else {
                                alert.setText(Html.fromHtml("<font color='red'>Check your internet connection</font>"));
                                Toast.makeText(Create_New.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            alert.setText(Html.fromHtml("<font color='red'>Enter mobile number</font>"));
                            Toast.makeText(Create_New.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        alert.setText(Html.fromHtml("<font color='red'>Enter chemist code</font>"));
                        Toast.makeText(Create_New.this, "Enter chemist code", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    alert.setText(Html.fromHtml("<font color='red'>button click error</font>"));
                    Toast.makeText(Create_New.this, "button click error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ImageView eyes = findViewById(R.id.eyes);
        final ImageView eyes1 = findViewById(R.id.eyes1);
        eyes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                eyes1.setVisibility(View.GONE);
                eyes.setVisibility(View.VISIBLE);
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                password.setSelection(password.getText().length());
            }
        });

        eyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                eyes.setVisibility(View.GONE);
                eyes1.setVisibility(View.VISIBLE);
                password.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setSelection(password.getText().length());
            }
        });
    }

    private void get_create_new_api() {
        progressBar2.setVisibility(View.VISIBLE);

        String enter_name = enter_name1.getText().toString();
        user_name = user_name1.getText().toString();
        phone_number = phone_number1.getText().toString();
        String password1 = password.getText().toString();

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.create_user_api(
                enter_name,user_name, password1,phone_number);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        create_new_btn.setVisibility(View.VISIBLE);
                        create_new_btn1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);

                        String res = response.body().string();
                        //Toast.makeText(Create_New.this,res.toString(),Toast.LENGTH_SHORT).show();
                        if (!res.equals("")) {
                            JSONArray jArray0 = new JSONArray(res);
                            JSONObject jsonObject0 = jArray0.getJSONObject(0);
                            String items = jsonObject0.getString("items");

                            JSONArray jArray1 = new JSONArray(items);
                            for (int i = 0; i < jArray1.length(); i++) {
                                JSONObject jsonObject = jArray1.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                String status_message = jsonObject.getString("status_message");
                                if(status.equals("1")) {
                                    Intent in = new Intent();
                                    in.setClass(Create_New.this, Create_New_thank_you.class);
                                    in.putExtra("status_message", status_message);
                                    startActivity(in);
                                    finish();
                                }
                                else {
                                    alert.setText(Html.fromHtml("<font color='red'>"+status_message+"</font>"));
                                }
                                Toast.makeText(Create_New.this,status_message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        create_new_btn.setVisibility(View.VISIBLE);
                        create_new_btn1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);

                        alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                        // TODO: handle exception
                        Log.e("drd-logs", "get_create_new_api Error parsing data" + e.toString());
                    }
                } else {
                    create_new_btn.setVisibility(View.VISIBLE);
                    create_new_btn1.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);

                    alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                    // Handle error response
                    Log.e("drd-logs", "get_create_new_api Handle error response");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                create_new_btn.setVisibility(View.VISIBLE);
                create_new_btn1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);

                alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                // Handle network failures or other errors
                Log.e("drd-logs", "get_create_new_api Handle network failures or other errors " +t.toString());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try
        {
            finish();
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
}