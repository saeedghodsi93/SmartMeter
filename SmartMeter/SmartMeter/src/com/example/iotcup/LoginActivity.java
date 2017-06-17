package com.example.iotcup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iotcup.JSONParser.JSONParserReturnObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements AsyncResponse {

	private String url, userId, token;
	private final int LoginAsyncTaskId = 1;
	private boolean asyncTaskIsRunning;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.i("LoginActivity","onCreate");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
       
        final EditText usernameEditText = (EditText) findViewById(R.id.username_editText);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_editText);
        Button loginButton = (Button) findViewById(R.id.login_button);
        usernameEditText.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        passwordEditText.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        loginButton.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        
        loginButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				login(usernameEditText,passwordEditText);			
			}
		});

        url = getIntent().getExtras().getString("url");
        
        
        
        
        usernameEditText.setText("admin@test.com");
        passwordEditText.setText("admin123321");
        
        
        
        
    }
	
	private void login(EditText usernameEditText, EditText passwordEditText) {
    	Log.i("LoginActivity","login");
    	
		if(checkFields(usernameEditText, passwordEditText)) {
			String Url = url + "login";
			HTTPAsyncTask httpAsyncTask = new HTTPAsyncTask(LoginAsyncTaskId,LoginActivity.this,Url,"PostJSONObjectAndGetJSONArray",(ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE));
			
			if(httpAsyncTask.checkConnectivity()) {
				String user = usernameEditText.getText().toString();
		    	String pass = passwordEditText.getText().toString();
		    	LoginInformationObject loginInformationObject = new LoginInformationObject(user,pass);
		    	JSONObject json = loginInformationObject.GetJSONObject();

    	    	userId = user;
    	    	
				try {
					asyncTaskIsRunning = true;
					httpAsyncTask.asyncResponse = this;
					httpAsyncTask.execute(json);
				}
				catch (Exception e) {
		        	Log.e("LoginActivity","HTTPAsyncTask-execute");
		        	e.printStackTrace();
		        }
			}
		}

	}

	private boolean checkFields(EditText usernameEditText, EditText passwordEditText) {
    	Log.i("LoginActivity","checkFields");
    	
		String username = usernameEditText.getText().toString();
		if(TextUtils.isEmpty(username)) {
			usernameEditText.setError("لطفا نام کاربری خود را وارد نمایید.");
			return false;
		}
		String password = passwordEditText.getText().toString();
		if(TextUtils.isEmpty(password)) {
			passwordEditText.setError("لطفا رمز عبور خود را وارد نمایید.");
			return false;
		}
		return true;
	}	
	
	@Override
	public void processFinish(JSONParserReturnObject jsonParserReturnObject, int asyncTaskId) {
		if(asyncTaskId==LoginAsyncTaskId) {
			if(jsonParserReturnObject.getStatusCode()==200) {   
	        	token = Deserialize(jsonParserReturnObject);
	    		if(token!=null) {
	    	    	Log.i("LoginActivity","processFinish-successful");

					asyncTaskIsRunning = false;
		    		Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
		    		intent.putExtra("url", url);
		    		intent.putExtra("userId", userId);
		    		intent.putExtra("token", token);
					startActivity(intent);
					
	    		}
	    		else {
	    			Log.e("LoginActivity","processFinish-can not parse jsonobject");
		    	
	    			Toast toast = Toast.makeText(LoginActivity.this,"اشکال در برقراری ارتباط با  سرور",Toast.LENGTH_SHORT);
	        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
	        		toast.show();
	    		}
		    }
		    else if(jsonParserReturnObject.getStatusCode()==401) {
		    	Log.e("LoginActivity","processFinish-unauthorized token");
		    	
		    	Toast toast = Toast.makeText(LoginActivity.this,"لطفا نام کاربری و رمز عبور صحیح را وارد نمایید",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
		    } 
		    else {
		    	Log.e("LoginActivity","processFinish-status code: "+jsonParserReturnObject.getStatusCode());
		    	
		    	Toast toast = Toast.makeText(LoginActivity.this,"اشکال در برقراری ارتباط با  سرور",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
		    }
		}
		else {
			Log.e("LoginActivity","processFinish-asyncTaskId");
	    	
	    	Toast toast = Toast.makeText(LoginActivity.this,"اشکال در برقراری ارتباط با  سرور",Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
		}
	}
	
	private String Deserialize(JSONParserReturnObject jsonParserReturnObject) {
		String token = null;
		if(jsonParserReturnObject.getJSONArray()!=null) {
			JSONArray jsonArray = jsonParserReturnObject.getJSONArray();
			try {
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				token = jsonObject.getString("token");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return token;
	}
	
}
