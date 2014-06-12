package com.tickit;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tickit.adapter.GroupListAdapter;
import com.tickit.model.GroupListItem;
 
public class GroupsFragment extends Fragment {
     
	private String[] groupNames;
	private String[][] groupTasks;
	private int[] groupIds;
	private RelativeLayout groupList;
	private GroupListAdapter adapter;
	private ArrayList<GroupListItem> groupListItems;
	
    public GroupsFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.groups, container, false);
    	groupList = (RelativeLayout) rootView.findViewById(R.id.groupsList);
    	groupIds = new int[] {1, 2, 3, 4, 5, 6};
    	groupNames = new String[] {"3S2 Class Group", "Project Group", "LOL group", "Anti-Benny Federation", "Useful group", "4S2 Future Group"};
    	groupTasks = new String[][] {{"Maths Assignment 23", "Mug for EOYs", "Chinese shit", "Don't thank anyone", "tell someone to go away"}, {"Make this app work", "Make the settings", "Make Benny screw off", "Make everything"}, {"this group sucks", "exit it", "yep"}, {"Tell Benny he sucks", "Benny you suck"}, {"This is a completely useless group"}, {} };
    	groupListItems = new ArrayList<GroupListItem>();
    	for (int i = 0; i < groupNames.length; i++){
    		
    		
            groupListItems.add(new GroupListItem(groupIds[i], groupNames[i], groupTasks[i]));
            }
    	
    	adapter = new GroupListAdapter((InterfaceActivity) getActivity(),
                groupListItems);
    	
    	final int adapterCount = adapter.getCount();
    	
    	for (int a = 0; a < adapterCount; a++) {
    		int marLeft = 0;
    		int marTop = 0;
    		
    		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
      	  View item = adapter.getView(a, null, null);
      	  if (a%2 != 0){
      		
      		marLeft = dpToPx(166);
      		
      	  }
      	  
      	  marTop = (a/2)*dpToPx(200);
      	  
      	lp.setMargins(marLeft, marTop, 0, 0);
  		item.setLayoutParams(lp);
      	  groupList.addView(item);
      	}
        
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // This could also be a ScrollView
    ScrollView list = (ScrollView) view.findViewById(R.id.groupsroot);
    // This could also be set in your layout, allows the list items to scroll through the bottom padded area (navigation bar)
    list.setClipToPadding(false);
    // Sets the padding to the insets (include action bar and navigation bar padding for the current device and orientation)
    setInsets(getActivity(), list);
    }
    
    public int dpToPx(int dp) {
	    DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
    
    public static void setInsets(Activity context, View view) {
    	if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
    	SystemBarTintManager tintManager = new SystemBarTintManager(context);
    	SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
    	view.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    	}
    
    
    
}