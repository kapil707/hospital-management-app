package com.example.hospitalmanagement;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Hospital_view_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Hospital_view_get_or_set> livetvItems;

    public Hospital_view_Adapter(Context context, List<Hospital_view_get_or_set> arraylist)
    {
        this.context = context;
        this.livetvItems = arraylist;
    }
 
    @Override
	public int getCount() {
		return livetvItems.size();
	}
    
   
    @Override
	public View getView(int position, View view, ViewGroup parent) 
    {
		// TODO Auto-generated method stub
       
        LayoutInflater abc = ((Activity) context).getLayoutInflater();
		View itemView = abc.inflate(R.layout.hospital_view_item, null,true);
		final Hospital_view_get_or_set get = livetvItems.get(position);

        ImageView item_image = itemView.findViewById(R.id.item_image);

        TextView item_lbl1 = itemView.findViewById(R.id.item_lbl1);
        TextView item_lbl2 = itemView.findViewById(R.id.item_lbl2);

        Picasso.get().load(get.doctor_photo()).into(item_image);
        item_lbl1.setText(Html.fromHtml(get.doctor_name()));
        item_lbl2.setText(Html.fromHtml(get.doctor_description()));
        return itemView;
    }

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}