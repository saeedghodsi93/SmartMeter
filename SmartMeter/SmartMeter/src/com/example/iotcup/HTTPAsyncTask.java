package com.example.iotcup;

import java.net.InetAddress;
import java.net.URL;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.iotcup.JSONParser.JSONParserReturnObject;

// supported methods: GetJSONObject, GetJSONArray, GetString, PostJSONObjectAndGetJSONObject, PostJSONObjectAndGetJSONArray, PostJSONObjectAndGetJSONString
class HTTPAsyncTask extends AsyncTask<JSONObject, String, JSONParserReturnObject> {
	public AsyncResponse asyncResponse = null;
	private int asyncTaskId;
	private Context context;
	private ProgressDialog pDialog;
	private String url = null;
	private String method = null;
	private ConnectivityManager connectivityManager = null;
	
	public HTTPAsyncTask(int _asyncTaskId, Context _context, String _url, String _method, ConnectivityManager _connectivityManager) {
		asyncTaskId = _asyncTaskId;
		context = _context;
		url = _url;
		method = _method;
		connectivityManager = _connectivityManager;
		
	}
		
	@Override
	protected void onPreExecute() {
		Log.i("HTTPAsyncTask","onPreExecute");
		
		super.onPreExecute();
	    pDialog = new ProgressDialog(context);
	    pDialog.setMessage("لطفا منتظر بمانید...");
	    pDialog.setIndeterminate(false);
	    pDialog.setCancelable(true);
	    pDialog.show();
	    
	}
	
	@Override
	protected JSONParserReturnObject doInBackground(JSONObject... args) {
		Log.i("HTTPAsyncTask","doInBackground");
		
		JSONParser jsonParser = new JSONParser();
		JSONParserReturnObject jsonParserReturnObject = null;
		if(method.equals("GetJSONObject")) {
			jsonParserReturnObject = jsonParser.getJSONObjectFromUrl(url);
		}
		else if(method.equals("GetJSONArray")) {
			jsonParserReturnObject = jsonParser.getJSONArrayFromUrl(url);
		}
		else if(method.equals("GetString")) {
			jsonParserReturnObject = jsonParser.getStringFromUrl(url);
		}
		else if(method.equals("PostJSONObjectAndGetJSONObject")) {
			jsonParserReturnObject = jsonParser.postJSONObjectToUrlAndGetJSONObject(url, args[0]);
		}
		else if(method.equals("PostJSONObjectAndGetJSONArray")) {
			jsonParserReturnObject = jsonParser.postJSONObjectToUrlAndGetJSONArray(url, args[0]);
		}
		else if(method.equals("PostJSONObjectAndGetJSONString")) {
			jsonParserReturnObject = jsonParser.postJSONObjectToUrlAndGetString(url, args[0]);
		}
		else {
			Log.e("HTTPAsyncTask","doInBackground-wrong method");
		}
	    return jsonParserReturnObject;
		
	}
	
	@Override
	protected void onPostExecute(JSONParserReturnObject jsonParserReturnObject) {
		Log.i("HTTPAsyncTask","onPostExecute");
		
		asyncResponse.processFinish(jsonParserReturnObject,asyncTaskId);
	    pDialog.dismiss();
	    
	}
	
	protected boolean checkConnectivity() {
    	Log.i("HTTPAsyncTask","checkConnectivity");
    	
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
        	return true;
        	/*
        	try {
        		String ip = InetAddress.getByName(new URL(url).getHost()).getHostAddress();
				Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 "+ip);
            	Log.i("HTTPAsyncTask","checkConnectivity-ip address: "+ip);
		        int returnValue = process.waitFor();
		        if(returnValue == 0) {
	        		return true;
	        	}
	        	else {
	            	Log.e("LoginActivity","checkConnectivity-ping returnValue");
	        		Toast toast = Toast.makeText(context, "اشکال در برقراری ارتباط با سرور", Toast.LENGTH_SHORT);
	        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
	        		toast.show();
	        		return false;
	        	}
			}
			catch (Exception e) {
		    	Log.e("LoginActivity","checkConnectivity-ping exec");
		    	Toast toast = Toast.makeText(context, "اشکال در برقراری ارتباط با سرور", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
				e.printStackTrace();
				return false;
			}*/
        }
        else {
	    	Log.e("LoginActivity","checkConnectivity-networkInfo");
        	Toast toast = Toast.makeText(context, "لطفا اتصال اینترنت را بررسی کنید", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
            return false; 
        }
    }
}
