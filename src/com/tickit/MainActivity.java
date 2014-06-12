package com.tickit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

public class MainActivity extends FragmentActivity{
	private static final int SPLASH = 0;
	private static final int EMAIL = 1;
	private static final int EMAILREGISTER = 2;
	private static final int FRAGMENT_COUNT = EMAILREGISTER +1;
	
	
	EmailAccountHandler db;
	private ProgressDialog pDialog;
	EmailAccountHandler Mdb;
	public static TickitUser user;
	CreateSQLUser creation;
	JSONParser jsonParser = new JSONParser();
	private static String url_check_user = "http://tick-it.net23.net/view.php";
	private static String url_create_user = "http://tick-it.net23.net/createuser.php";
	private static final String TAG_SUCCESS = "success";
	//public SlidingMenu menu;
	public static String password = "password";
	//private MenuItem settings;

	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	//private SplashActivity splashFragment;
	private boolean isResumed = false;
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	    setContentView(R.layout.main);
	    
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);

	    //menu = new SlidingMenu(this);
	    //menu.setMode(SlidingMenu.LEFT_RIGHT);
		//menu.setBehindWidth(500);
		//menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		//menu.setFadeDegree(0.15f);
		//menu.setMenu(R.layout.menu_frame);
		//menu.setSecondaryMenu(R.layout.menu_frame);
	    FragmentManager fm = getSupportFragmentManager();
	    fragments[SPLASH] = fm.findFragmentById(R.id.splashActivity);
	    fragments[EMAIL] = fm.findFragmentById(R.id.emailLoginFragment);
	    fragments[EMAILREGISTER] = fm.findFragmentById(R.id.emailRegisterFragment);

	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();
	}
	
	public void showFragment(int fragmentIndex, boolean addToBackStack/*, boolean slider*/) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (int i = 0; i < fragments.length; i++) {
	        if (i == fragmentIndex) {
	            transaction.show(fragments[i]);
	        } else {
	            transaction.hide(fragments[i]);
	        }
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    
	    //if (slider)
	    //{
	    //	menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	    //}
	    //
	    //else {
	    //	menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
	    //}
	    
	    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	    transaction.commit();
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    // Only make changes if the activity is visible
	    if (isResumed) {
	        FragmentManager manager = getSupportFragmentManager();
	        // Get the number of entries in the back stack
	        int backStackSize = manager.getBackStackEntryCount();
	        // Clear the back stack
	        for (int i = 0; i < backStackSize; i++) {
	            manager.popBackStack();
	        }
	        if (state.isOpened()) {
	            // If the session state is open:
	            // Show the authenticated fragment
	    	   if (isEmailValidated(this.getApplicationContext()) == 2) {
	        	Intent intent = new Intent(this, InterfaceActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	        	startActivity(intent);
	        } else if (session != null && session.isOpened() && isEmailValidated(this.getApplicationContext()) == 1){
		    	Intent intent = new Intent(this, InterfaceActivity.class);
		    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		    	startActivity(intent);
		    } else if (session != null && session.isOpened() && isEmailValidated(this.getApplicationContext()) == 0) {
	    	        // Get the user's data
	    	        getInfo(session);
	    	    }
	            creation = new CreateSQLUser();
	            creation.execute();
	            //showFragment(SLIDINGMENU, false, true);
	            
	        }  
	        
	        else {
	        	showFragment(SPLASH, false);
	        }
	    }
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    
	    	if (isEmailValidated(this.getApplicationContext()) == 2){
	        // 
	    	Intent intent = new Intent(this, InterfaceActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	
	    	startActivity(intent);
	    } else if (session != null && session.isOpened() && isEmailValidated(this.getApplicationContext()) == 1){
	    	Intent intent = new Intent(this, InterfaceActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	startActivity(intent);
	    }
	    	
	    	else if (session != null && session.isOpened() && isEmailValidated(this.getApplicationContext()) == 0) {
    	        // Get the user's data
    	        getInfo(session);
    	    
    	        //Intent intent = new Intent(this, InterfaceActivity.class);
    	        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	        //startActivity(intent);
	        //new CreateSQLUser().execute();
	    } 
	    
	    else {
	    	showFragment(SPLASH, false);
	    }
	}
	
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	    
	}
	
	public int isEmailValidated (Context mContext){
		
		db = new EmailAccountHandler (mContext);
		
		if (db.getAccountCount() != 0){
	
			

			
			if(db.getFacebookCount()!=0)
			{
				Log.i("isEmailValidated","facebook");
				return 1;
			}
			
			else if (db.getEmailCount()!=0)
			{
				Log.i("isEmailValidated","email");
				return 2;
			}
			
		}
		
		Log.i("isEmailValidated","Not valid");
		
		return 0;
	}
	
	
		
		
	
	
	class CreateSQLUser extends AsyncTask<String, String, String> {
		 
		public String id;
		public String name;
		public String username;
		public String email;
		public String password = "password";
       

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting things ready...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
        protected String doInBackground(String... args) {
    	    
    	       id = TickitUser.getId();
    	       name = TickitUser.getName();
    	       username = TickitUser.getUsername();
    	       email = TickitUser.getEmail();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject check = jsonParser.makeHttpRequest(url_check_user,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Checking if user exists", check.toString());
 
            // check for success tag
            try {
                int success = check.getInt(TAG_SUCCESS);
 
                if (success != 1) {
                	
                	if (TickitUser.getLoginType() == "facebook"){
                    //no such user
                	JSONObject create = jsonParser.makeHttpRequest(url_create_user,
                            "POST", params);
                	Log.d("Creating new user", create.toString());
                	}
                	
                	else {
                		
                	}
                	
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
        
protected void onPostExecute(String result) {
        	
	db = new EmailAccountHandler (MainActivity.this.getApplicationContext());
	db.addAccount(email, password, "facebook");
	Log.i("CreateSQLUser","adding to SQLite");
	pDialog.dismiss();
	Intent intent = new Intent(MainActivity.this, InterfaceActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    startActivity(intent);
    
        }
        
       
 
    }
	
	private void getInfo(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                	TickitUser.setId(user.asMap().get("id").toString());
	                	TickitUser.setEmail(user.asMap().get("email").toString());
	                	TickitUser.setName(user.asMap().get("name").toString());
	                	TickitUser.setUsername(user.asMap().get("username").toString());
	                	TickitUser.setPassword("password");
	                	TickitUser.setLoginType("facebook");
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    request.executeAsync();
	} 
	
}
