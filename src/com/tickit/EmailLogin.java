package com.tickit;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EmailLogin extends Fragment{

	private static String url_check_user = "http://tick-it.net23.net/view.php";
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	public EditText email;
	public EditText password;
	public static String _email;
	public static String _password;
	public static boolean success = false;
	EmailAccountHandler db;
	private ProgressDialog pDialog;
	
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.emaillogin, 
	            container, false);
	    Button letsgo = (Button) view.findViewById(R.id.login_submit);
	    email = (EditText) view.findViewById(R.id.login_email);
	    password = (EditText) view.findViewById(R.id.login_password);
	    
	    
	    letsgo.setOnClickListener( new View.OnClickListener() {
	    	
	    	
	        @SuppressWarnings("deprecation")
			@Override
	        public void onClick(View v) {
	        	
	        	
	            //When the loginEmail button is clicked,
	        	if (isEmailValid(email.getText()))
	        	{
	        		_email = (email.getText()).toString();
		    		_password = (password.getText()).toString();
		    		
		    			new CheckAccount().execute();
		    		
	        		
	        		
	        		
	        	}
	        	else {
	        		AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
	        		alertDialog.setTitle("Invalid Email");
	        		alertDialog.setMessage("Please input a valid email.");
	        		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        		dialog.cancel();
	        		}
	        		});
	        		
	        		alertDialog.show();
	        	}
	        	
	        	
	        	
	        	
	            //((MainActivity) getActivity()).showFragment(EMAIL, true);
	        }
	    });
	    
	    return view;
	}
	
	
	
	public static boolean isEmailValid(CharSequence email) {

	    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	
	class CheckAccount extends AsyncTask<String, String, String> {
		 
		
		public String Memail;
		public String Mpassword;
       
        /**
         * Creating product
         * */
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Validating account. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
        protected String doInBackground(String... args) {
    	    
    	       
    	       Memail = EmailLogin._email;
    	       Mpassword = EmailLogin._password;
    	      
    	    
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", Memail));
            params.add(new BasicNameValuePair("password", Mpassword));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject check = jsonParser.makeHttpRequest(url_check_user,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Checking if user exists", check.toString());
 
            // check for success tag
            try {
                int success = check.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	EmailLogin.success=true;
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
        	
        	if (EmailLogin.success == true){
    			Log.i("User validated!", "Inserting into SQLite..");
    			db = new EmailAccountHandler (getActivity().getApplicationContext());
    			db.addAccount(_email, _password, "email");
  
    			success = false;
    			
    			//((MainActivity) getActivity()).showFragment(SLIDINGMENU, true);
    			Intent intent = new Intent(getActivity(), InterfaceActivity.class);
    			startActivity(intent);
    		}
    		else if (EmailLogin.success == false){
    			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setTitle("No such user");
        		builder.setMessage("Invalid email or password. Please register.");
        		builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   ((MainActivity) getActivity()).showFragment(2, true);
        	           }
        	       });
        		
        		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        		AlertDialog alertDialog = builder.create();
        		alertDialog.show();
        		
    		}
            
        }
        
       
 
    }
	
}
