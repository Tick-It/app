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

public class EmailRegister extends Fragment{

	public EditText email;
	public EditText password;
	private static final String TAG_SUCCESS = "success";
	public EditText username;
	public EditText name;
	private static String url_check_user = "http://tick-it.net23.net/view.php";
	private static String url_create_email = "http://tick-it.net23.net/createemail.php";
	JSONParser jsonParser = new JSONParser();
	public static String _email;
	public static String _password;
	public static boolean success = false;
	public static String _username;
	public static String _name;
	EmailAccountHandler db;
	private ProgressDialog pDialog;
	
	
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, Bundle savedInstanceState) {
	    super.onCreateView(inflater, container, savedInstanceState);
	    View view = inflater.inflate(R.layout.emailcreate, 
	            container, false);
	    Button letsgo = (Button) view.findViewById(R.id.create_submit);
	    name = (EditText) view.findViewById(R.id.create_name);
	    email = (EditText) view.findViewById(R.id.create_email);
	    username = (EditText) view.findViewById(R.id.create_username);
	    password = (EditText) view.findViewById(R.id.create_password);
	    
         letsgo.setOnClickListener( new View.OnClickListener() {
	    	
	    	
	        @SuppressWarnings("deprecation")
			@Override
	        public void onClick(View v) {
	        	
	        	
	            //When the createEmail button is clicked
	        	
	        	if (isEmailValid(email.getText()))
	        	{
	        		_email = (email.getText()).toString();
		    		_password = (password.getText()).toString();
		    		_username = (username.getText()).toString();
		    		_name = (name.getText()).toString();
		    			new CreateEmail().execute();
	        		
	        		
	        		
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
	
class CreateEmail extends AsyncTask<String, String, String> {
		 
		
		public String Memail;
		public String Mpassword;
		public String Musername;
		public String Mname;
       
        /**
         * Creating product
         * */
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Creating account. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
        protected String doInBackground(String... args) {
    	    
    	       Musername = EmailRegister._username;
    	       Mname = EmailRegister._name;
    	       Memail = EmailRegister._email;
    	       Mpassword = EmailRegister._password;
    	      
    	    
        	
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", Memail));
            params.add(new BasicNameValuePair("password", Mpassword));
            params.add(new BasicNameValuePair("username", Musername));
            params.add(new BasicNameValuePair("name", Mname));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject check = jsonParser.makeHttpRequest(url_check_user,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Checking if user exists", check.toString());
 
            // check for success tag
            try {
                int success = check.getInt(TAG_SUCCESS);
 
                if (success == 0) {
                	
                	JSONObject create = jsonParser.makeHttpRequest(url_create_email,
                            "POST", params);
         
                    // check log cat fro response
                    Log.d("User does not exist, creating...", create.toString());
                    
                	EmailRegister.success=true;
                	
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
        	
        	if (EmailRegister.success == true){
        		Log.i("User validated!", "Inserting into SQLite..");
        		
        		db = new EmailAccountHandler (getActivity().getApplicationContext());
        		db.addAccount(_email, _password, "email");
        		
        		
        		EmailRegister.success = false;
        		
        		//((MainActivity) getActivity()).showFragment(SLIDINGMENU, true);// make this normal please
        		Intent intent = new Intent(getActivity(), InterfaceActivity.class);
        		startActivity(intent);
    		}
    		else if (EmailRegister.success == false){
    			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setTitle("User already exists");
        		builder.setMessage("You are alrady registered. Log in now.");
        		builder.setPositiveButton("Log in", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	   
        	        	   ((MainActivity) getActivity()).showFragment(1, true);
        	               
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
