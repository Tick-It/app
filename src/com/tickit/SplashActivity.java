package com.tickit;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

 @Override
 public View onCreateView(LayoutInflater inflater, 
         ViewGroup container, 
         Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.splash, container, false);
     LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
    


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
    	  });
     
     
     return view;
 }
 
 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);

 }


 

}

