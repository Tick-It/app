package com.tickit;
import java.util.ArrayList;
import java.util.Collection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tickit.adapter.CardListAdapter;
import com.tickit.model.CardListItem;
 
public class TasksFragment extends Fragment {
     
    public TasksFragment(){}
    private RelativeLayout greetcont;
    private TextView numTasks;
    private String[] cardListTitles;
	private String[] cardListDueDate;
	public static TickitUser user;
	private TaskHandler taskhandler;
	private ListView taskList;
	private LinearLayout header_view;
	private LinearLayout notaskview;
	private TextView notaskview1;
	private TextView notaskview2;
	public AnimateDismissAdapter dismissadapter;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private ArrayList<CardListItem> cardListItems;
    private CardListAdapter adapter;
    private String[][] cardListDone;
     
    @SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
    	
    	taskhandler = new TaskHandler(getActivity());
        View rootView = inflater.inflate(R.layout.tasks, container, false);
        taskList = (ListView) rootView.findViewById(R.id.list_task);
          
        header_view = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.tasks_header, null);
        notaskview = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.no_tasks_footer, null);
        notaskview1 = (TextView) notaskview.findViewById(R.id.notasksview1);
        notaskview2 = (TextView) notaskview.findViewById(R.id.notasksview2);
        Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        notaskview1.setTypeface(roboto);
        notaskview2.setTypeface(roboto);
        
        cardListItems = new ArrayList<CardListItem>();
        
        greetcont = (RelativeLayout) header_view.findViewById(R.id.task_greeting_cont);
        numTasks = (TextView) header_view.findViewById(R.id.task_numTask);
        
        GradientDrawable shape =  new GradientDrawable();
		shape.setCornerRadius(dpToPx(2));
        shape.setColor(Color.parseColor("#0099FF"));
        
        greetcont.setBackground(shape);

        
        taskList.addHeaderView(header_view);
        

        adapter = new CardListAdapter((InterfaceActivity) getActivity(),
                taskhandler.getTasks(), 1);
        
        dismissadapter = new AnimateDismissAdapter(adapter, new MyOnDismissCallback());
        swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(dismissadapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(300);
        swingBottomInAnimationAdapter.setAbsListView(taskList);
        taskList.setAdapter(swingBottomInAnimationAdapter);
        
        updateTasks();
        
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // This could also be a ScrollView
    ListView list = (ListView) view.findViewById(R.id.list_task);
    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
    list.setClipToPadding(false);
    // Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
    setInsets(getActivity(), list);
    }
    
    public static void setInsets(Activity context, View view) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
    	SystemBarTintManager tintManager = new SystemBarTintManager(context);
    	SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
    	view.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    	}
    
    public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
    
    public void dismissItems(int positionsToDismiss){
    	dismissadapter.animateDismiss(positionsToDismiss);
    
    }
    
    private void updateTasks() {
    	if (cardListItems.toArray().length < 1){
        	taskList.addFooterView(notaskview);
        }
        numTasks.setText(String.valueOf(cardListItems.toArray().length));
		
	}
    
    
    
    private class MyOnDismissCallback implements OnDismissCallback {

        @Override
        public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                cardListItems.remove(position);
                updateTasks();
                adapter.notifyDataSetChanged();
                
            }
        }
    }
    
    
}