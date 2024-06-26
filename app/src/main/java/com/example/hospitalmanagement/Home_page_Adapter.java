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

public class Home_page_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Home_page_get_or_set> livetvItems;

    public Home_page_Adapter(Context context, List<Home_page_get_or_set> arraylist)
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
		View itemView = abc.inflate(R.layout.home_page_item, null,true);
		final Home_page_get_or_set My_order_get = livetvItems.get(position);

        ImageView item_image = itemView.findViewById(R.id.item_image);

        TextView item_lbl1 = itemView.findViewById(R.id.item_lbl1);
        TextView item_lbl2 = itemView.findViewById(R.id.item_lbl2);

        Picasso.get().load(My_order_get.hospital_photo()).into(item_image);
        item_lbl1.setText(Html.fromHtml(My_order_get.hospital_name()));
        item_lbl2.setText(Html.fromHtml(My_order_get.hospital_description()));
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