package com.tickit.adapter;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tickit.InterfaceActivity;
import com.tickit.R;
import com.tickit.model.GroupListItem;

public class GroupListAdapter extends BaseAdapter{

	private InterfaceActivity activity;
	private ArrayList<GroupListItem> groupListItems;

	public GroupListAdapter(InterfaceActivity activity,
			ArrayList<GroupListItem> groupListItems) {
		this.activity = activity;
		this.groupListItems = groupListItems;
	}

	@Override
	public int getCount() {
		return groupListItems.size();
	}

	@Override
	public Object getItem(int position) {
		return groupListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//final int tempPos = position;

		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) activity
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.group_list_item, null);
		}

		TextView groupLogo = (TextView) convertView
				.findViewById(R.id.grouplogo);
		TextView groupName = (TextView) convertView
				.findViewById(R.id.groupname);
		TextView groupDesc = (TextView) convertView
				.findViewById(R.id.groupdesc);


		

		Typeface font = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/RobotoCondensed-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/Roboto-Thin.ttf");
		Typeface font1 = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/Roboto-Light.ttf");
		Typeface font3 = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/Roboto-LightItalic.ttf");
		Typeface ostrich = Typeface.createFromAsset(this.activity.getAssets(),
				"fonts/ostrich-regular.ttf");
		
		int num = groupListItems.get(position).getNum();
		String numStr = String.valueOf(num);
	
		
		GradientDrawable shape =  new GradientDrawable();
		 shape.setCornerRadius(dpToPx(32));

		 if (num==0){
			 shape.setColor(Color.parseColor("#AAAAAA"));
		 }
		 
		 else if (num == 1){
			shape.setColor(Color.parseColor("#39ED7B"));
		}
		
		else if (num == 2){
			shape.setColor(Color.parseColor("#CEDE23"));
		}
		
		else if (num == 3){
			shape.setColor(Color.parseColor("#DECC45"));
		}
		
		else if (num == 4){
			shape.setColor(Color.parseColor("#ED9F39"));
		}
		
		else if (num > 4){
			shape.setColor(Color.parseColor("#ED5A39"));
		}
		
		groupLogo.setBackground(shape);
		
		groupLogo.setText(numStr);
		groupLogo.setTextColor(Color.parseColor("#EEEEEE"));
		groupLogo.setTypeface(font1);
		
		groupName.setText(groupListItems.get(position).getName());
		groupName.setTypeface(font1);

		groupDesc.setText("► " + groupListItems.get(position).getDesc());
		groupDesc.setTypeface(font1);
		return convertView;
	}
	
	public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	
}
