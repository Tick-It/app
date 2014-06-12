package com.tickit;

import java.util.ArrayList;

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
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.AnimateAdditionAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tickit.adapter.CardListAdapter;
import com.tickit.model.CardListItem;
import com.tickit.model.CircularProgressBar;
import com.tickit.model.CircularProgressBar.ProgressAnimationListener;
 
@SuppressLint("NewApi")
public class HomeFragment extends Fragment{
     
	
	int actionbarheight;
	private TextView greeting;
	private TextView hometodaytext;
	private TextView hometodaycount;
	private TextView smileytext;
	private RelativeLayout hometodaycont;
	private RelativeLayout greetcont;
	private RotateAnimation rotatesmiley;
	private CircularProgressBar progresslevel;
	public AnimateDismissAdapter dismissadapter;
	private TaskHandler taskhandler;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private LinearLayout header_view;
	private LinearLayout notaskview;
	private TextView notaskview1;
	private TextView notaskview2;
	private ListView list;
	//private int progress;
	//int screenheight;
	private boolean isResumed = true;
	private AnimateAdditionAdapter<CardListItem> mAnimateAdditionAdapter;
	private ArrayList<CardListItem> cardListItems;
    public CardListAdapter adapter;
    public HomeFragment(){}
   
     
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
    	taskhandler = new TaskHandler(getActivity());
    	
        View rootView = inflater.inflate(R.layout.home, container, false);
        
        list = (ListView) rootView.findViewById(R.id.homeroot);
        header_view = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.home_header, null);
        notaskview = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.no_tasks_footer, null);
        notaskview1 = (TextView) notaskview.findViewById(R.id.notasksview1);
        notaskview2 = (TextView) notaskview.findViewById(R.id.notasksview2);
        String noticecolor = "#ED5A39";
        
        hometodaycont = (RelativeLayout) header_view.findViewById(R.id.hometodaycont);
        greetcont = (RelativeLayout) header_view.findViewById(R.id.home_greeting_cont);
        smileytext = (TextView) header_view.findViewById(R.id.smileytext);
        greeting = (TextView) header_view.findViewById(R.id.greetingtext);
        Typeface roboto = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoCondensed-Regular.ttf");
        notaskview1.setTypeface(roboto);
        notaskview2.setTypeface(roboto);
        greeting.setTypeface(roboto);
        hometodaytext = (TextView) header_view.findViewById(R.id.hometodaytext);
        hometodaycount = (TextView) header_view.findViewById(R.id.hometodaycount);
        Typeface robotothin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        hometodaytext.setTypeface(robotothin);
        hometodaycount.setTypeface(robotothin);
        //homeall = (LinearLayout) rootView.findViewById(R.id.home_all_cont);
       
        cardListItems = new ArrayList<CardListItem>();
        
        rotatesmiley = (RotateAnimation)AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_smiley);
        
        GradientDrawable shape0 =  new GradientDrawable();
		 shape0.setCornerRadius(dpToPx(20));
		 
		 shape0.setColor(Color.parseColor(noticecolor));
		 
		 hometodaycount.setBackground(shape0);
		 
		 
        
        progresslevel = (CircularProgressBar) header_view.findViewById(R.id.circularprogressbar1);
        
        GradientDrawable shape =  new GradientDrawable();
		 shape.setCornerRadius(dpToPx(2));
		 
		 
        
		 adapter = new CardListAdapter((InterfaceActivity) getActivity(),
				 taskhandler.getTasks(), 0);
       
        dismissadapter = new AnimateDismissAdapter(adapter, new MyOnDismissCallback());
        
        swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(dismissadapter);
        swingBottomInAnimationAdapter.setInitialDelayMillis(300);
        
		swingBottomInAnimationAdapter.setAbsListView(list);
        list.setAdapter(swingBottomInAnimationAdapter);
        
        shape.setColor(Color.parseColor(noticecolor));
        
        greetcont.setBackground(shape);
        
        
        
        greeting.setText("You slacked a lot today.");
        
        
        smileytext.startAnimation(rotatesmiley);
        list.addHeaderView(header_view);
        if (isResumed){
        	
        	
        	
        progresslevel.animateProgressTo(0, 77, new ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            	
            }

            @Override
            public void onAnimationProgress(int progress) {
            	progresslevel.setSubTitle("Level " + "2");
            	progresslevel.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
            	
            }
        });}
        else {
        	progresslevel.setProgress(77);
        	progresslevel.setTitle("77%");
        	progresslevel.setSubTitle("Level " + "2");
        }
        
        
        //Arrays
        //TODO updateTasks();
        
        return rootView;
    }
    


	@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // This could also be a ScrollView
    ListView list = (ListView) view.findViewById(R.id.homeroot);
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
    
    
    public static int findLevel (int progress) {
    	int a = (int) (-1*progress/12.5);
    	float b = (float) (1-4*a);
    	
    	int c = (int) android.util.FloatMath.sqrt(b);
    	
    	int d = -1+c;
    	int e = d/2;
    	return e;
    	
    }
    
    
    
    @Override
	public void onResume() {
	    super.onResume();
	    isResumed = true;
	}
    
    @Override
	public void onPause() {
	    super.onPause();
	    isResumed = false;
	}
    
    public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}


    public void dismissItems(int positionsToDismiss){
    	dismissadapter.animateDismiss(positionsToDismiss);
    	
    }

    private class MyOnDismissCallback implements OnDismissCallback {

        @Override
        public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
            	
            	cardListItems.remove(position);
            		
            	//TODO updateTasks();
                
                adapter.notifyDataSetChanged();
                
                
                
            }
        }
    }

    

}
    
