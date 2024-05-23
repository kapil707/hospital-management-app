package com.example.hospitalmanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_page extends AppCompatActivity {
    Button login_btn, login_btn1;
    EditText user_name, password;
    TextView alert, termsofservice, create_new_btn;
    String user_name1 = "", password1 = "";
    String user_id = "", name = "", username = "", mobile = "",status="",status_message="";
    UserSessionManager session;
    ProgressBar progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        login_btn = findViewById(R.id.login_btn);
        login_btn1 = findViewById(R.id.login_btn1);

        alert = (TextView) findViewById(R.id.user_alert);
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);
        termsofservice = findViewById(R.id.termsofservice);
        create_new_btn = findViewById(R.id.create_new);
        create_new_btn.setText(Html.fromHtml("Don't have an account? <br><font color='#27ae60'>Create New Account</font>"));

        create_new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Login_page.this,
                        Create_New.class);
                startActivity(myIntent);
            }
        });


        final CheckBox ck = findViewById(R.id.checkBox);

        session = new UserSessionManager(getApplicationContext());
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    user_name1 = user_name.getText().toString();
                    password1 = password.getText().toString();

                    if (user_name1.length() > 0) {
                        if (password1.length() > 0) {
                            if (ck.isChecked()) {
                                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo ni = cm.getActiveNetworkInfo();
                                if (ni != null) {
                                    try {
                                        get_login_api();
                                        login_btn1.setVisibility(View.VISIBLE);
                                        login_btn.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                } else {
                                    alert.setText(Html.fromHtml("<font color='red'>Check your internet connection</font>"));
                                    Toast.makeText(Login_page.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                alert.setText(Html.fromHtml("<font color='red'>Check terms of service</font>"));
                                Toast.makeText(Login_page.this, "Check terms of service", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            alert.setText(Html.fromHtml("<font color='red'>Enter password</font>"));
                            Toast.makeText(Login_page.this, "Enter password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        alert.setText(Html.fromHtml("<font color='red'>Enter username</font>"));
                        Toast.makeText(Login_page.this, "Enter username", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    alert.setText(Html.fromHtml("<font color='red'>button click error</font>"));
                    Toast.makeText(Login_page.this, "button click error", Toast.LENGTH_SHORT).show();
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

    void get_login_api() {
        progressBar2.setVisibility(View.VISIBLE);
        username = user_name.getText().toString();
        String password1 = password.getText().toString();

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.login_user_api(
                username, password1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        //Toast.makeText(Login_page.this, res.toString(), Toast.LENGTH_LONG).show();
                        //alert.setText(Html.fromHtml("<font>"+res.toString()+"</font>"));

                        login_btn.setVisibility(View.VISIBLE);
                        login_btn1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);

                        if (!res.equals("")) {

                            JSONArray jArray0 = new JSONArray(res);
                            JSONObject jsonObject0 = jArray0.getJSONObject(0);
                            String items = jsonObject0.getString("items");

                            //Toast.makeText(Login_page.this,items.toString(),Toast.LENGTH_SHORT).show();

                            JSONArray jArray1 = new JSONArray(items);
                            for (int i = 0; i < jArray1.length(); i++) {
                                JSONObject jsonObject = jArray1.getJSONObject(i);
                                user_id = jsonObject.getString("user_id");
                                name = jsonObject.getString("name");
                                username = jsonObject.getString("username");
                                mobile = jsonObject.getString("mobile");
                                status = jsonObject.getString("status");
                                status_message = jsonObject.getString("status_message");
                            }
                        }

                        Toast.makeText(Login_page.this, status_message, Toast.LENGTH_SHORT).show();
                        if (status.equals("1")) {
                            alert.setText(Html.fromHtml("<font color='#28a745'>" + status_message + "</font>"));


                            session.createUserLoginSession(user_id, name, username, mobile);

                            Intent in = new Intent();
                            in.setClass(Login_page.this, MainActivity.class);
                            startActivity(in);
                            finish();

                        } else {
                            alert.setText(Html.fromHtml("<font color='red'>" + status_message + "</font>"));
                        }
                    } catch (Exception e) {
                        login_btn.setVisibility(View.VISIBLE);
                        login_btn1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);

                        alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                        // TODO: handle exception
                        Log.e("drd-logs", "get_login_api Error parsing data" + e.toString());
                    }
                } else {
                    login_btn.setVisibility(View.VISIBLE);
                    login_btn1.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);

                    alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                    // Handle error response
                    Log.e("drd-logs", "get_login_api Handle error response");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                login_btn.setVisibility(View.VISIBLE);
                login_btn1.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);

                alert.setText(Html.fromHtml("<font color='red'>try again</font>"));
                // Handle network failures or other errors
                Log.e("drd-logs", "get_login_api Handle network failures or other errors " +t.toString());
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
        try {
            finish();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //return inetAddress.getHostAddress().toString();
                        @SuppressWarnings("deprecation")
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            //Log.e("IP Address", ex.toString());
        }
        return null;
    }
}