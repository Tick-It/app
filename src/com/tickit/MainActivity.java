package com.tickit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity{
	private static final int SPLASH = 0;
	private static final int SLIDINGMENU = 1;
	private static final int SETTINGS = 2;
	private static final int FRAGMENT_COUNT = SETTINGS +1;
	public static GraphUser user;
	public static Fragment slidingmenumenu = new SlidingMenuMenuFragment();
	
	JSONParser jsonParser = new JSONParser();
	private static String url_check_user = "http://tick-it.net23.net/view.php";
	private static String url_create_user = "http://tick-it.net23.net/createuser.php";
	private static final String TAG_SUCCESS = "success";
	public static String id;
	public static String first_name;
	public static String last_name;
	public static String username;
	public static String email;
	public static String password = "password";
	private MenuItem settings;

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

	    FragmentManager fm = getSupportFragmentManager();
	    fragments[SPLASH] = fm.findFragmentById(R.id.splashActivity);
	    fragments[SLIDINGMENU] = fm.findFragmentById(R.id.slidingmenumenuFragment);
	    fragments[SETTINGS] = fm.findFragmentById(R.id.userSettingsFragment);

	    FragmentTransaction transaction = fm.beginTransaction();
	    for(int i = 0; i < fragments.length; i++) {
	        transaction.hide(fragments[i]);
	    }
	    transaction.commit();
	}
	
	private void showFragment(int fragmentIndex, boolean addToBackStack) {
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
	    	    if (session != null && session.isOpened()) {
	    	        // Get the user's data
	    	        getInfo(session);
	    	    }
	            new CreateSQLUser().execute();
	            showFragment(SLIDINGMENU, false);
	            showUserInterface();
	        } else if (state.isClosed()) {
	            // If the session state is closed:
	            // Show the login fragment
	            showFragment(SPLASH, false);
	        }
	    }
	}
	
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	    	if (session != null && session.isOpened()) {
    	        // Get the user's data
    	        getInfo(session);
    	    }
	    	showFragment(SLIDINGMENU, false);
	    	showUserInterface();
	        //new CreateSQLUser().execute();
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
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
	
	public void showUserInterface() {

		/*getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, slidingmenumenu)
		.commit();*/

		// configure the SlidingMenu
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setBehindWidth(500);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setFadeDegree(0.15f);
		menu.setMenu(R.layout.menu_frame);
		menu.setSecondaryMenu(R.layout.menu_frame);
		/*The below code adds the menu
		 * getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SlidingMenuMenuFragment())
		.commit();*/
	}
		
		
	
	
	class CreateSQLUser extends AsyncTask<String, String, String> {
		 
		public String id;
		public String first_name;
		public String last_name;
		public String username;
		public String email;
		public String password = "password";
       
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
    	    
    	       id = MainActivity.id;
    	       first_name = MainActivity.first_name;
    	       last_name = MainActivity.last_name;
    	       username = MainActivity.username;
    	       email = MainActivity.email;
    	      
    	    
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", id));
            params.add(new BasicNameValuePair("first_name", first_name));
            params.add(new BasicNameValuePair("last_name", last_name));
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
                    //no such user
                	JSONObject create = jsonParser.makeHttpRequest(url_create_user,
                            "POST", params);
                	Log.d("Creating new user", create.toString());
                	
                	
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
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
	                	id = (user.asMap().get("id").toString());
	                	email = (user.asMap().get("email").toString());
	                	first_name = (user.asMap().get("first_name").toString());
	                	last_name = (user.asMap().get("last_name").toString());
	                	username = (user.asMap().get("username").toString());
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
