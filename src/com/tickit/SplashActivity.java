package com.tickit;

import java.net.ConnectException;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class SplashActivity extends Fragment {

 private String TAG = "SplashActivity";
public static String email;
public static GraphUser user;
private static final int EMAIL = 1;
private static final int EMAILREGISTER = 2;
ConnectivityManager cm;

 @Override
 public View onCreateView(LayoutInflater inflater, 
         ViewGroup container, 
         Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.splash, container, false);
     LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
    Button loginEmail = (Button) view.findViewById(R.id.loginEmail);
    Button createEmail = (Button) view.findViewById(R.id.createEmail);
    try {
   if (isDeviceConnected() != true){
	   authButton.setEnabled(false);
	   loginEmail.setEnabled(false);
	   createEmail.setEnabled(false);
	   throw new ConnectException();
	  
   }
    	
   else {
    
    loginEmail.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //When the loginEmail button is clicked,
            ((MainActivity) getActivity()).showFragment(EMAIL, true);
        }
    });

    createEmail.setOnClickListener( new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //When the loginEmail button is clicked,
            ((MainActivity) getActivity()).showFragment(EMAILREGISTER, true);
        }
    });

     authButton.setFragment(this);
     authButton.setReadPermissions(Arrays.asList("basic_info","email"));
     authButton.setSessionStatusCallback(new Session.StatusCallback() {
    	   
    	   @Override
    	   public void call(Session session, SessionState state, Exception exception) {
    	    
    	    if (session.isOpened()) {
    	              Log.i(TAG,"Access Token"+ session.getAccessToken());
    	              Request.executeMeRequestAsync(session,
    	                      new Request.GraphUserCallback() {
    	                          @Override
    	                          public void onCompleted(GraphUser user,Response response) {
    	                              if (user != null) { 
    	                            	  //SplashActivity.this.user = user;
    	                               Log.i(TAG,"User ID "+ user.getId());
    	                               Log.i(TAG,"Email "+ user.asMap().get("email"));
    	                               //SplashActivity.this.user=user;
    	                              }
    	                          }
    	                      });
    	          }
    	    
    	   }
    	  });}
    }
    catch (ConnectException e){
    	AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setTitle("No internet");
		alertDialog.setMessage("Please connect to a network.");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		dialog.cancel();
		}
		});
		
		alertDialog.show();
    }
     
     
     return view;
 }
 
 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);

 }


 public boolean isDeviceConnected () {
	 cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	 NetworkInfo ni = cm.getActiveNetworkInfo();
	 if (ni == null) {
	     // There are no active networks.
	     return false;
	 }
	 
	 return ni.isConnected();
 }

}

