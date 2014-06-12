package com.tickit;

public class TickitUser {

	public static String email;
	public static String id;
	public static String username;
	public static String name;
	public static String last_name;
	public static String password;
	public static String login_type; //'facebook' | 'email'
	
	
	public TickitUser(){
        
    }
	
	public TickitUser(String _email, String _password, String _login_type){
        email = _email;
        password = _password;
        login_type = _login_type;
    }
	
	public TickitUser(String _id, String _email, String _password, String _login_type){
		id = _id;
        email = _email;
        password = _password;
        login_type = _login_type;
    }
	
	public TickitUser(String _id, String _name, String _last_name, String _login_type, String _email, String _username, String _password){
        id = _id;
        name = _name;
        last_name = _last_name;
        login_type = _login_type;
		email = _email;
        username = _username;
        password = _password;
    }
	
	public static String getId(){
        return id;
    }
	
	public static String getEmail(){
        return email;
    }
	
	public static String getUsername(){
        return username;
    }
	
	public static String getName(){
        return name;
    }
	
	public static String getPassword(){
        return password;
    }
	
	public static String getLoginType(){
        return login_type;
    }
	
	public static void setId(String _id){
        id = _id;
    }
	
	public static void setEmail(String _email){
        email = _email;
    }
	
	public static void setUsername(String _username){
        username = _username;
    }
	
	public static void setName(String _name){
        name = _name;
       
    }
	
	public static void setPassword(String _password){
        password = _password;
    }
	
	public static void setLoginType(String _login_type){
        login_type = _login_type;
    }
	
	
	
	public static void logOut(){
		
		email = "";
		id = "";
		username = "";
		name = "";
		last_name = "";
		password = "";
		login_type = "";
	}
	
}
