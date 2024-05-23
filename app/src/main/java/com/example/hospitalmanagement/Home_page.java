package com.example.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_page extends AppCompatActivity {

    ProgressBar action_bar_progressBar;
    UserSessionManager session;
    ListView listview;
    Home_page_Adapter adapter;
    List<Home_page_get_or_set> Home_page_List = new ArrayList<Home_page_get_or_set>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        action_bar_progressBar = findViewById(R.id.action_bar_progressBar);

        ImageButton action_bar_back = findViewById(R.id.action_bar_back);
        action_bar_back.setVisibility(View.GONE);

        TextView action_bar_title = findViewById(R.id.action_bar_title);
        action_bar_title.setText(R.string.app_name);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        listview = findViewById(R.id.listView1);
        adapter = new Home_page_Adapter(Home_page.this, Home_page_List);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Home_page_get_or_set clickedCategory = Home_page_List.get(arg2);

                Intent open_page = new Intent();
                open_page.setClass(Home_page.this, Hospital_view.class);
                open_page.putExtra("id", clickedCategory.id());
                open_page.putExtra("hospital_photo", clickedCategory.hospital_photo());
                open_page.putExtra("hospital_name", clickedCategory.hospital_name());
                open_page.putExtra("hospital_description", clickedCategory.hospital_description());
                startActivity(open_page);
            }
        });

        home_page_api();
    }
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                //Log.d("MenuClick", "Item clicked: " + id);

                if (id == R.id.action_logout) {
                    // Handle settings action
                    alertMessage_logoutUser();
                    return true;
                }
//                else if (id == R.id.action_settings1) {
//                    // Handle other menu item
//                    return true;
//                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void alertMessage_logoutUser() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        try {
                            session.logoutUser();
                            finish();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to logout?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    void home_page_api() {

        String id = "";
        action_bar_progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.home_page_api(
                id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        //Toast.makeText(Home_page.this, res.toString(), Toast.LENGTH_LONG).show();
                        if (!res.equals("")) {
                            JSONArray jArray0 = new JSONArray(res);
                            JSONObject jsonObject0 = jArray0.getJSONObject(0);
                            String items = jsonObject0.getString("items");
                            //String other_items = jsonObject0.getString("other_items");

                            JSONArray jArray1 = new JSONArray(items);
                            for (int i = 0; i < jArray1.length(); i++) {
                                JSONObject jsonObject = jArray1.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String hospital_name = jsonObject.getString("hospital_name");
                                String hospital_description = jsonObject.getString("hospital_description");
                                String hospital_photo = jsonObject.getString("hospital_photo");

                                Home_page_get_or_set set = new Home_page_get_or_set();
                                set.id(id);
                                set.hospital_name(hospital_name);
                                set.hospital_description(hospital_description);
                                set.hospital_photo("https://www.skychannelnetwork.in/hospitalmanagement/"+hospital_photo);
                                Home_page_List.add(set);
                            }
                            //header_result_found.setText("Found result (" + result_total + ")");
                            adapter.notifyDataSetChanged();
                            action_bar_progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        action_bar_progressBar.setVisibility(View.GONE);
                        // TODO: handle exception
                        Log.e("drd-logs", "home_page_api Error parsing data" + e.toString());
                    }
                } else {
                    action_bar_progressBar.setVisibility(View.GONE);
                    // Handle error response
                    Log.e("drd-logs", "home_page_api Handle error response");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                action_bar_progressBar.setVisibility(View.GONE);
                // Handle network failures or other errors
                Log.e("drd-logs", "home_page_api Handle network failures or other errors " +t.toString());
            }
        });
    }
}