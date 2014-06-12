package com.tickit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tickit.adapter.NavDrawerListAdapter;
import com.tickit.adapter.TabAdapter;
import com.tickit.model.NavDrawerItem;


@SuppressLint("NewApi")
public class InterfaceActivity extends FragmentActivity{

	private SpannableString mTypeTitle;
	EmailAccountHandler Adb;
	private ProgressDialog pDialog;
    private TypedArray navMenuIcons;
    private SlidingTabLayout slidingtablayout;
    private ViewPager viewPager;
    private TaskHandler taskhandler;
    private static String url_create_task = "http://tick-it.net23.net/createtask.php";
    private static final String TAG_SUCCESS = "success";
    private TabAdapter mAdapter;
    public TickitUser user;
    JSONParser jsonParser = new JSONParser();
    private ActionBar actionBar;
    private FragmentManager fragmanager;
    private int pos;
    private CharSequence[] tabs = { "Home", "Tasks", "Groups", "Settings" };
    int i;
    
    static class SamplePagerItem {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;

        SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
            mTitle = title;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

       

        /**
         * @return the title which represents this tab. In this sample this is used directly by
         * {@link android.support.v4.view.PagerAdapter#getPageTitle(int)}
         */
        CharSequence getTitle() {
            return mTitle;
        }

        /**
         * @return the color to be used for indicator on the {@link SlidingTabLayout}
         */
        int getIndicatorColor() {
            return mIndicatorColor;
        }

        /**
         * @return the color to be used for right divider on the {@link SlidingTabLayout}
         */
        int getDividerColor() {
            return mDividerColor;
        }
    }
    
    private List<SamplePagerItem> mTabs = new ArrayList<SamplePagerItem>();
    
    

    private Context mContext;
 
    //TODO add back button action, and log out flow, and update the icon on the top of the action bar
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.activity_interface);
        final ActionBar actionBar = getActionBar();
        final SystemBarTintManager tintManager = new SystemBarTintManager(this);
        ArrayList<Integer> TabList = new ArrayList<Integer>();
        TabList.add(R.drawable.ic_home_selector);
        TabList.add(R.drawable.ic_tasks_selector);
        TabList.add(R.drawable.ic_groups_selector);
        TabList.add(R.drawable.ic_settings_selector);
        
        Adb = new EmailAccountHandler (this);
        user = Adb.getUser();
        
        Log.i("Chacking", user.getId());
        mTabs.add(new SamplePagerItem(
                "home", // Title
                getResources().getColor(R.color.default_colour), // Indicator color
                Color.WHITE // Divider color
        ));

        mTabs.add(new SamplePagerItem(
               "tasks", // Title
               getResources().getColor(R.color.tasks_colour), // Indicator color
                Color.WHITE // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                "groups", // Title
                getResources().getColor(R.color.groups_colour), // Indicator color
                Color.WHITE // Divider color
        ));

        mTabs.add(new SamplePagerItem(
                "Settings", // Title
                getResources().getColor(R.color.settings_colour), // Indicator color
                Color.WHITE // Divider color
        ));
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        
        mContext = this;
        
        
     // setting the nav drawer list adapter
        viewPager = (ViewPager) findViewById(R.id.pager);
        
        fragmanager = getSupportFragmentManager();
        
        mAdapter = new TabAdapter(fragmanager);
        
        viewPager.setAdapter(mAdapter);
        
        actionBar.setIcon(R.drawable.actionbar);
        slidingtablayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingtablayout.setViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        	
        	tintManager.setStatusBarTintEnabled(true);
        	// My icon is #cb3500
        	int actionBarColor = Color.parseColor("#cb3500");
        	tintManager.setStatusBarTintColor(actionBarColor);
        }
        
        slidingtablayout.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                    	
                    	if (position == 0) {actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cb3500")));
                    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        	
                        	tintManager.setStatusBarTintEnabled(true);
                        	// My icon is #cb3500
                        	int actionBarColor = Color.parseColor("#cb3500");
                        	tintManager.setStatusBarTintColor(actionBarColor);
                        }
                    	
                    	
                    	}
                    	
                    
                    	else if (position == 1) {actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099FF")));
                    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        	
                        	tintManager.setStatusBarTintEnabled(true);
                        	// My icon is #cb3500
                        	int actionBarColor = Color.parseColor("#0099FF");
                        	tintManager.setStatusBarTintColor(actionBarColor);
                        }
                    	}
                    	else if (position == 2) {actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#41D97B")));
                    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        	
                        	tintManager.setStatusBarTintEnabled(true);
                        	// My icon is #cb3500
                        	int actionBarColor = Color.parseColor("#41D97B");
                        	tintManager.setStatusBarTintColor(actionBarColor);
                        }
                    	}
                    	
                    	else if (position == 3) {actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#737373")));
                    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        	
                        	tintManager.setStatusBarTintEnabled(true);
                        	// My icon is #cb3500
                        	int actionBarColor = Color.parseColor("#737373");
                        	tintManager.setStatusBarTintColor(actionBarColor);
                        }
                    	}
                    	
                    	//pos = position;
                    }
                    	
                    	}
                    	);
        slidingtablayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return mTabs.get(position).getIndicatorColor();
            }

            @Override
            public int getDividerColor(int position) {
                return mTabs.get(position).getDividerColor();
            }

        });
        
        slidingtablayout.setBackgroundColor(Color.WHITE);
	}
        
        /**
         * on swiping the viewpager make respective tab selected
         * */
        
       
    /* // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				viewPager.setCurrentItem(tab.getPosition());
				
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 4; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabs[i])
                            .setTabListener(tabListener));
        }
       
     */
        //set Navbar tint
	
	
 
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_refresh_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
 

	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_new:
        	createTaskDialog();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    private void createTaskDialog() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View v = inflater.inflate(R.layout.edit_dialog, null);
	    builder.setTitle("New Task");
	    final EditText task = (EditText) v.findViewById(R.id.dialog_task);
		final EditText duedate = (EditText) v.findViewById(R.id.dialog_duedate);
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	  CreateSQLTask create = new CreateSQLTask();
	            	   create.name = task.getText().toString();
	            	   create.duedate = duedate.getText().toString();
	            	   create.execute();
	                   dialog.dismiss();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });      
	    		
	   
		
		builder.create().show();
		
	}
    
    class CreateSQLTask extends AsyncTask<String, String, String> {
		 
		public String name;
		public String duedate;
		public String UserId;
       

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InterfaceActivity.this);
            pDialog.setMessage("Creating task...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
        protected String doInBackground(String... args) {
    	    
    	       
        	UserId = user.getId();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("duedate", duedate));
            params.add(new BasicNameValuePair("user_id", UserId));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject check = jsonParser.makeHttpRequest(url_create_task,
                    "POST", params);
            // check log cat fro response
            Log.d("Creating Task", check.toString());
 
            // check for success tag
            try {
                int success = check.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	String task_id = String. valueOf(check.getInt("task_id"));
                	taskhandler = new TaskHandler (InterfaceActivity.this);
                	taskhandler.createTask(name, duedate, task_id);
                	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                	switch (pos) {
                	
                	case 1:
                		HomeFragment fraggie = (HomeFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.pager+":0");
                		
                		ft.disallowAddToBackStack();
                		ft.detach(fraggie);
                		ft.attach(fraggie);
                		ft.commit();
                	}
                	
                	
                	
                	Log.i("CreateTask","adding to SQLite");
                	
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
        
protected void onPostExecute(String result) {
        	
	
	pDialog.dismiss();

    
        }
        
       
 
    }

	public Dialog onCreateDialog(int id, String title, String due) {
    	switch (id){
    	case 1:
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View v = inflater.inflate(R.layout.edit_dialog, null);
	    builder.setTitle("Edit Task");
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.dismiss();
	               }
	           });      
	    		
	    EditText task = (EditText) v.findViewById(R.id.dialog_task);
		task.setText((CharSequence) title);
		
		EditText duedate = (EditText) v.findViewById(R.id.dialog_duedate);
		duedate.setText((CharSequence) due);
		
		
	    return builder.create();
    	}
    	
    	return null;
	}

   
 
    /**
     * Slide menu item click listener
     * */
    
	
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
 
    @Override
    public void setTitle(CharSequence title) {
    	
    	mTypeTitle = new SpannableString(title);
    	mTypeTitle.setSpan(new TypefaceSpan(mContext, "RobotoCondensed-Regular.ttf"), 0, mTypeTitle.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActionBar().setTitle(mTypeTitle);
    }

    
  
   public void dismissTask(int i, int tab){
	   if (tab == 0){
		   HomeFragment fragment = (HomeFragment) fragmanager.findFragmentByTag("android:switcher:"+R.id.pager+":0");
		   fragment.dismissItems(i);
		  
	   }
	   
	   if (tab == 1){
		   TasksFragment fragment = (TasksFragment) fragmanager.findFragmentByTag("android:switcher:"+R.id.pager+":1");
		   fragment.dismissadapter.animateDismiss(i);
	   }
	   
   }
    
    
    }
    
    
    /*private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        
        switch (position) {
        case 0:
        	
            fragment = new HomeFragment();
            break;
        case 1:
        	
            fragment = new TasksFragment();
            break;
        case 2:
        	
            fragment = new TodayFragment();
            break;
        case 3:
        	
            fragment = new GroupsFragment();
            break;
        case 4:
        	
            fragment = new FoldersFragment();
            break;
        case 5:
        	
            fragment = new TagsFragment();
            break;
        case 6:
        	
            fragment = new PriorityFragment();
            break;
        case 7:

        	
            fragment = new SettingsFragment();
            break;
 
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mTitle = navMenuTitles[position];
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }*/
	

