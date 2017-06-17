package com.example.iotcup;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LoginInformationObject {
	 
	String user = null;
    String pass = null;
	
    public LoginInformationObject(String _user, String _pass) {
		this.user = _user;
		this.pass = _pass;
	}
    
    public JSONObject GetJSONObject() {
    	JSONObject json = null;
    	try {
    		json = new JSONObject();
        	json.put("username",Getuser());
        	json.put("password",Getpass());
        }
        catch (JSONException j) {
        	Log.e("LoginInformationObject","getJSONObject");
        	j.printStackTrace();
        }
    	return json;
    }
    
    public String Getuser() {
    	return this.user;
    }
    
    public String Getpass() {
    	return this.pass;
    }
    
}
