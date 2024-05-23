package com.example.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hospital_view extends AppCompatActivity {

    ProgressBar action_bar_progressBar;
    String hospital_id="",hospital_photo="",hospital_name="",hospital_description="";
    UserSessionManager session;

    ListView listview;
    Hospital_view_Adapter adapter;
    List<Hospital_view_get_or_set> Hospital_view_List = new ArrayList<Hospital_view_get_or_set>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_view);

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
        hospital_id = in.getStringExtra("id");
        hospital_photo = in.getStringExtra("hospital_photo");
        hospital_name = in.getStringExtra("hospital_name");
        hospital_description = in.getStringExtra("hospital_description");

        action_bar_progressBar = findViewById(R.id.action_bar_progressBar);

        TextView action_bar_title = findViewById(R.id.action_bar_title);
        action_bar_title.setText(hospital_name);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setVisibility(View.GONE);

        ImageView search_btn = findViewById(R.id.search_btn);
        search_btn.setVisibility(View.GONE);

        ImageView item_image = findViewById(R.id.item_image);

        TextView item_lbl1 = findViewById(R.id.item_lbl1);
        TextView item_lbl2 = findViewById(R.id.item_lbl2);

        Picasso.get().load(hospital_photo).into(item_image);
        item_lbl1.setText(hospital_name);
        item_lbl2.setText(hospital_description);

        listview = findViewById(R.id.listView1);
        adapter = new Hospital_view_Adapter(Hospital_view.this, Hospital_view_List);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                Hospital_view_get_or_set clickedCategory = Hospital_view_List.get(arg2);

                Intent open_page = new Intent();
                open_page.setClass(Hospital_view.this, Doctor_view.class);
                open_page.putExtra("id", clickedCategory.id());
                open_page.putExtra("doctor_photo", clickedCategory.doctor_photo());
                open_page.putExtra("doctor_name", clickedCategory.doctor_name());
                open_page.putExtra("doctor_description", clickedCategory.doctor_description());
                startActivity(open_page);
            }
        });

        hospital_wise_doctor_api();
    }

    void hospital_wise_doctor_api() {

        action_bar_progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.hospital_wise_doctor_api(
                hospital_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        //Toast.makeText(Hospital_view.this, res.toString(), Toast.LENGTH_LONG).show();
                        if (!res.equals("")) {
                            JSONArray jArray0 = new JSONArray(res);
                            JSONObject jsonObject0 = jArray0.getJSONObject(0);
                            String items = jsonObject0.getString("items");
                            //String other_items = jsonObject0.getString("other_items");

                            JSONArray jArray1 = new JSONArray(items);
                            for (int i = 0; i < jArray1.length(); i++) {
                                JSONObject jsonObject = jArray1.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String doctor_name = jsonObject.getString("doctor_name");
                                String doctor_description = jsonObject.getString("doctor_description");
                                String doctor_photo = jsonObject.getString("doctor_photo");

                                Hospital_view_get_or_set set = new Hospital_view_get_or_set();
                                set.id(id);
                                set.doctor_name(doctor_name);
                                set.doctor_description(doctor_description);
                                set.doctor_photo("https://www.skychannelnetwork.in/hospitalmanagement/"+doctor_photo);
                                Hospital_view_List.add(set);
                            }
                            //header_result_found.setText("Found result (" + result_total + ")");
                            adapter.notifyDataSetChanged();

                            action_bar_progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        action_bar_progressBar.setVisibility(View.GONE);
                        // TODO: handle exception
                        Log.e("drd-logs", "hospital_wise_doctor_api Error parsing data" + e.toString());
                    }
                } else {
                    action_bar_progressBar.setVisibility(View.GONE);
                    // Handle error response
                    Log.e("drd-logs", "hospital_wise_doctor_api Handle error response");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                action_bar_progressBar.setVisibility(View.GONE);
                // Handle network failures or other errors
                Log.e("drd-logs", "hospital_wise_doctor_api Handle network failures or other errors " +t.toString());
            }
        });
    }
}