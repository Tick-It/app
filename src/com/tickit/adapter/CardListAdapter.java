package com.tickit.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.tickit.InterfaceActivity;
import com.tickit.R;
import com.tickit.model.CardListItem;

public class CardListAdapter extends BaseAdapter {

	private InterfaceActivity activity;
	private ArrayList<CardListItem> cardListItems;
	private int tab;
	private LayoutInflater inflater;

	public CardListAdapter(InterfaceActivity activity,
			ArrayList<CardListItem> cardListItems, int tab) {
		this.activity = activity;
		this.cardListItems = cardListItems;
		this.tab = tab;
	}

	@Override
	public int getCount() {
		return cardListItems.size();
	}

	@Override
	public Object getItem(int position) {
		return cardListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final int tempPos = position;
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) activity
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.card_list_item, null);
		}

		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.reminder_title);
		
		TextView txtDueDate = (TextView) convertView
				.findViewById(R.id.reminder_duedate);
		TextView txtDone = (TextView) convertView
				.findViewById(R.id.reminder_done);
		Button btnDone = (Button) convertView.findViewById(R.id.home_done);
		Button btnEdit = (Button) convertView.findViewById(R.id.home_edit);

		btnEdit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// DialogFragment newFragment = new EditTaskDialog(activity,
				// cardListItems.get(tempPos).getTitle(),
				// cardListItems.get(tempPos).getDueDate());
				(activity.onCreateDialog(1, cardListItems.get(tempPos).getTitle(), cardListItems.get(tempPos).getDueDate())).show();
				
			}
		});
		
		
		btnDone.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// DialogFragment newFragment = new EditTaskDialog(activity,
				// cardListItems.get(tempPos).getTitle(),
				// cardListItems.get(tempPos).getDueDate());
				deleteItems(tempPos);
			}

			
		});

		
		
		if (tab == 0) {
			btnDone.setTextColor(this.activity.getResources().getColor(
					R.color.default_colour));
			btnEdit.setTextColor(activity.getResources().getColor(
					R.color.default_colour));
			btnDone.setCompoundDrawablesWithIntrinsicBounds(activity
					.getResources().getDrawable(R.drawable.ic_action_accept),
					null, null, null);
			btnEdit.setCompoundDrawablesWithIntrinsicBounds(activity
					.getResources().getDrawable(R.drawable.ic_action_edit),
					null, null, null);
		}

		else if (tab == 1) {
			btnDone.setTextColor(this.activity.getResources().getColor(
					R.color.tasks_colour));
			btnEdit.setTextColor(this.activity.getResources().getColor(
					R.color.tasks_colour));
			btnDone.setCompoundDrawablesWithIntrinsicBounds(
					this.activity.getResources().getDrawable(
							R.drawable.ic_action_accept_task), null, null, null);
			btnEdit.setCompoundDrawablesWithIntrinsicBounds(
					this.activity.getResources().getDrawable(
							R.drawable.ic_action_edit_task), null, null, null);
		}

		else if (tab == 2) {
			btnDone.setTextColor(this.activity.getResources().getColor(
					R.color.groups_colour));
			btnEdit.setTextColor(activity.getResources().getColor(
					R.color.groups_colour));
		}

		else if (tab == 0) {
			btnDone.setTextColor(this.activity.getResources().getColor(
					R.color.settings_colour));
			btnEdit.setTextColor(activity.getResources().getColor(
					R.color.settings_colour));
		}

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
		txtTitle.setText(cardListItems.get(position).getTitle());
		txtTitle.setTypeface(font1);

		btnDone.setTypeface(font);
		btnEdit.setTypeface(font);

		/*
		 * if (cardListItems.get(position).getDescription().length()<1){
		 * txtDescription.setVisibility(View.GONE); } else
		 * {txtDescription.setText
		 * (cardListItems.get(position).getDescription());
		 * txtDescription.setTypeface(font3); }
		 */

		txtDueDate.setText(cardListItems.get(position).getDueDate());
		txtDueDate.setTypeface(ostrich);

		if (cardListItems.get(position).getDoneCount() == -1) {
			txtDone.setText("Not completed yet");
			txtDone.setTypeface(font);

		}

		else if (cardListItems.get(position).getDoneCount() == 0) {
			txtDone.setText(cardListItems.get(position).getDone()
					+ " has done this");
			txtDone.setTypeface(font);
		}

		else {
			txtDone.setText(cardListItems.get(position).getDone() + " and "
					+ cardListItems.get(position).getDoneCount()
					+ " others have done this");
			txtDone.setTypeface(font);
		}

		return convertView;
	}
	
	private void deleteItems(int i) {
		// TODO Auto-generated method stub
		activity.dismissTask(i, tab);
	}
}
