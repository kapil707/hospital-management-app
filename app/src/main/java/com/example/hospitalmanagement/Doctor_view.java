package com.example.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doctor_view extends AppCompatActivity {

    ProgressBar action_bar_progressBar;
    TextView user_alert;
    Button submit_btn, submit_btn1;
    EditText user_name, user_mobile,user_message;
    String user_name1, user_mobile1,user_message1;
    String doctor_id="",doctor_photo="",doctor_name="",doctor_description="";
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_view);

        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        ImageButton action_bar_back = findViewById(R.id.action_bar_back);
        //action_bar_back.setVisibility(View.GONE);
        action_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Intent in = getIntent();
        doctor_id = in.getStringExtra("id");
        doctor_photo = in.getStringExtra("doctor_photo");
        doctor_name = in.getStringExtra("doctor_name");
        doctor_description = in.getStringExtra("doctor_description");

        action_bar_progressBar = findViewById(R.id.action_bar_progressBar);

        TextView action_bar_title = findViewById(R.id.action_bar_title);
        action_bar_title.setText(doctor_name);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setVisibility(View.GONE);

        ImageView search_btn = findViewById(R.id.search_btn);
        search_btn.setVisibility(View.GONE);

        ImageView item_image = findViewById(R.id.item_image);

        TextView item_lbl1 = findViewById(R.id.item_lbl1);
        TextView item_lbl2 = findViewById(R.id.item_lbl2);

        Picasso.get().load(doctor_photo).into(item_image);
        item_lbl1.setText(doctor_name);
        item_lbl2.setText(doctor_description);

        submit_btn = findViewById(R.id.submit_btn);
        submit_btn1 = findViewById(R.id.submit_btn1);

        user_alert = (TextView) findViewById(R.id.user_alert);
        user_name = (EditText) findViewById(R.id.user_name);
        user_mobile = (EditText) findViewById(R.id.user_mobile);
        user_message = (EditText) findViewById(R.id.user_message);

        session = new UserSessionManager(getApplicationContext());
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                try {
                    user_name1 = user_name.getText().toString();
                    user_mobile1 = user_mobile.getText().toString();
                    user_message1 = user_message.getText().toString();

                    if (user_name1.length() > 0) {
                        if (user_mobile1.length() > 0) {
                            if (user_message1.length() > 0) {
                                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo ni = cm.getActiveNetworkInfo();
                                if (ni != null) {
                                    try {
                                        send_user_query_api();
                                        //submit_btn1.setVisibility(View.VISIBLE);
                                        submit_btn.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }

                                } else {
                                    user_alert.setText(Html.fromHtml("<font color='red'>Check your internet connection</font>"));
                                    Toast.makeText(Doctor_view.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                user_alert.setText(Html.fromHtml("<font color='red'>Enter message</font>"));
                                Toast.makeText(Doctor_view.this, "Enter message", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            user_alert.setText(Html.fromHtml("<font color='red'>Enter mobile</font>"));
                            Toast.makeText(Doctor_view.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        user_alert.setText(Html.fromHtml("<font color='red'>Enter your name</font>"));
                        Toast.makeText(Doctor_view.this, "Enter your name", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    user_alert.setText(Html.fromHtml("<font color='red'>button click error</font>"));
                    Toast.makeText(Doctor_view.this, "button click error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void send_user_query_api() {

        action_bar_progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.send_user_query_api(
                user_name1,user_mobile1,user_message1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        //Toast.makeText(Doctor_view.this, res.toString(), Toast.LENGTH_LONG).show();
                        //Toast.makeText(Hospital_view.this, res.toString(), Toast.LENGTH_LONG).show();
                        if (!res.equals("")) {
                            JSONArray jArray0 = new JSONArray(res);
                            JSONObject jsonObject0 = jArray0.getJSONObject(0);
                            String items = jsonObject0.getString("items");
                            //String other_items = jsonObject0.getString("other_items");
                            String message = jsonObject0.getString("message");

                            JSONArray jArray1 = new JSONArray(items);
                            for (int i = 0; i < jArray1.length(); i++) {
                                JSONObject jsonObject = jArray1.getJSONObject(i);
                                String status = jsonObject.getString("status");
                                String status_message = jsonObject.getString("status_message");
                                if(status.equals("1")) {
                                    Intent in = new Intent();
                                    in.setClass(Doctor_view.this, Thankyou_page.class);
                                    in.putExtra("status_message", status_message);
                                    startActivity(in);
                                    finish();
                                }
                                else {
                                    user_alert.setText(Html.fromHtml("<font color='red'>"+status_message+"</font>"));
                                }
                                Toast.makeText(Doctor_view.this,status_message,Toast.LENGTH_SHORT).show();
                            }
                        }
                        action_bar_progressBar.setVisibility(View.GONE);
                        submit_btn.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        action_bar_progressBar.setVisibility(View.GONE);
                        submit_btn.setVisibility(View.VISIBLE);
                        // TODO: handle exception
                        Log.e("drd-logs", "send_user_query_api Error parsing data" + e.toString());
                    }
                } else {
                    action_bar_progressBar.setVisibility(View.GONE);
                    submit_btn.setVisibility(View.VISIBLE);
                    // Handle error response
                    Log.e("drd-logs", "send_user_query_api Handle error response");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                action_bar_progressBar.setVisibility(View.GONE);
                submit_btn.setVisibility(View.VISIBLE);
                // Handle network failures or other errors
                Log.e("drd-logs", "send_user_query_api Handle network failures or other errors " +t.toString());
            }
        });
    }
}