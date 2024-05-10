package com.example.hospitalmanagement;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Create_New_thank_you extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_thank_you);
        //getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        Button go_home_btn = (Button) findViewById(R.id.go_home_btn);
        go_home_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        Intent in = getIntent();
        String status_message = in.getStringExtra("status_message");

        try {
            TextView create_new_message_tv = findViewById(R.id.create_new_message_tv);
            create_new_message_tv.setText(Html.fromHtml(status_message));
        } catch (Exception e) {
            // TODO: handle exception
            //mProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}