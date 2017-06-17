package com.example.iotcup;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MeterInformationObject {

	String userId;
	String id;
	String address;
	String name;
	
	public MeterInformationObject(String _userId) {
		userId = _userId;
	}
	
	public MeterInformationObject(String _userId, String _id, String _address) {
		userId = _userId;
		id = _id;
		address = _address;
	}
	
	public void SetMeterInformationObject(String _userId, String _id, String _address) {
		userId = _userId;
		id = _id;
		address = _address;
	}
	
	public void SetMeterInformationObjectFromJSONObject(JSONObject jsonObject) {
		try {
			//userId = jsonObject.getString("user_id");
			//address = jsonObject.getString("_address");
			id = jsonObject.getString("_id");
			name = jsonObject.getString("name");
		} catch (JSONException e) {
			Log.e("MeterInformationObject","SetMeterInformationObjectFromJSONObject");
			e.printStackTrace();
			
			userId = "";
			id = "";
			address = "";
			name = "";
		}
	}
	
	public String GetUserId() {
		return userId; 	
	}
	
	public String GetId() {
		return id;
	}
	
	public String GetAddress() {
		return address;
	}
	
	public String GetName() {
		return name;
	}
	
}
