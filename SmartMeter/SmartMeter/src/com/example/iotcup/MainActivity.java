package com.example.iotcup;

import com.example.iotcup.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

	String url = "http://93.174.92.183:8080/api/v1/";
	//String url = "http://77.104.86.24:80/api/v1/device";
	//String url = "http://posttestserver.com/post.php";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        Button loginButton = (Button) findViewById(R.id.login_activity_button);
        Button aboutButton = (Button) findViewById(R.id.about_activity_button);
        loginButton.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        aboutButton.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        
        loginButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
	    		intent.putExtra("url", url);
				startActivity(intent);
			}
		});
        
        aboutButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
        
        SingletonDatabase.initialize(MainActivity.this);
        
    }
    
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("قصد خروج از برنامه را دارید؟");
        builder.setCancelable(true);
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                   }
               })
               .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.cancel();
                   }
               })
               .show();
    }
    
}
