package com.example.iotcup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.iotcup.JSONParser.JSONParserReturnObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomeActivity extends Activity implements AsyncResponse {
	
	private String url, userId, token, selectedMeterId;
	private int selectedReportType;
	private LinkedHashMap<String, String> metersList;
	private List<MeterInformationObject> meterInformationObjects;
	private List<MeterReadingInformationObject> meterReadingInformationObjects;
	private final int GetMeterInformationObjectsAsyncTaskId = 1, GetMeterReadingInformationObjectsAsyncTaskId = 2;
	
	private Spinner metersListSpinner, reportTypeSpinner;
    private Calendar beginCalendar, endCalendar;
    
    private String[] menuButtons;
	private ListView drawerList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        url = getIntent().getExtras().getString("url");
        userId = getIntent().getExtras().getString("userId");
        token = getIntent().getExtras().getString("token");
        
        TextView metersListTextView = (TextView) findViewById(R.id.meter_list_textView);
        TextView reportTypeTextView = (TextView) findViewById(R.id.report_type_textView);
        metersListSpinner = (Spinner) findViewById(R.id.meters_list_spinner);
        reportTypeSpinner = (Spinner) findViewById(R.id.report_type_spinner);
        Button showReportButton = (Button) findViewById(R.id.show_report_button);
        metersListTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        reportTypeTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        showReportButton.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
        
    	metersListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    @Override
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	        selectedMeterId = metersList.get(parentView.getItemAtPosition(position).toString());
    	    }

    	    @Override
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	 	   selectedMeterId = parentView.getItemAtPosition(parentView.getFirstVisiblePosition()).toString();
    	    }

    	});
    	
        reportTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    @Override
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	        selectedReportType = position;
    	    }

    	    @Override
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	 	   selectedReportType = parentView.getFirstVisiblePosition();
    	    }

    	});
        
        beginCalendar = Calendar.getInstance();
        int currentDay = beginCalendar.get(Calendar.DAY_OF_MONTH);
        beginCalendar.set(Calendar.DAY_OF_MONTH, currentDay-1);
        endCalendar = Calendar.getInstance();
    	menuButtons = getResources().getStringArray(R.array.menu_buttons);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuButtons));
        drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int post, long arg3) {
				switch(post) {
					case 0:
					default:
			            int beginHour = beginCalendar.get(Calendar.HOUR_OF_DAY);
			            int beginMinute = beginCalendar.get(Calendar.MINUTE);
			            TimePickerDialog beginTimePickerDialog;
			            beginTimePickerDialog = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
			                @Override
			                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
			                   beginCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
			                   beginCalendar.set(Calendar.MINUTE, selectedMinute);
			                }
			            }, beginHour, beginMinute, true);
			            beginTimePickerDialog.setTitle("ساعت شروع قرائت");
			            beginTimePickerDialog.show();
						break;
						
					case 1:
			            int beginYear = beginCalendar.get(Calendar.YEAR);
			            int beginMonth = beginCalendar.get(Calendar.MONTH);
			            int beginDay = beginCalendar.get(Calendar.DAY_OF_MONTH);
			            DatePickerDialog beginDatePickerDialog;
			            beginDatePickerDialog = new DatePickerDialog(HomeActivity.this, new OnDateSetListener() {
		                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
		                        selectedmonth = selectedmonth + 1;
		                    }
		                }, beginYear, beginMonth, beginDay);
			            beginDatePickerDialog.setTitle("تاریخ شروع قرائت");
			            beginDatePickerDialog.show();
						break;
						
					case 2:
			            int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
			            int endMinute = endCalendar.get(Calendar.MINUTE);
			            TimePickerDialog endTimePickerDialog;
			            endTimePickerDialog = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
			                @Override
			                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
			                   endCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
			                   endCalendar.set(Calendar.MINUTE, selectedMinute);
			                }
			            }, endHour, endMinute, true);
			            endTimePickerDialog.setTitle("ساعت پایان قرائت");
			            endTimePickerDialog.show();
						break;
						
					case 3:
			            int endYear = endCalendar.get(Calendar.YEAR);
			            int endMonth = endCalendar.get(Calendar.MONTH);
			            int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
			            DatePickerDialog endDatePickerDialog;
			            endDatePickerDialog = new DatePickerDialog(HomeActivity.this, new OnDateSetListener() {
		                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
		                        selectedmonth = selectedmonth + 1;
		                    }
		                }, endYear, endMonth, endDay);
			            endDatePickerDialog.setTitle("تاریخ پایان قرائت");
			            endDatePickerDialog.show();
						break;
						
				}
			}
		});
        
    	showReportButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				GetMeterReadingInformationObjects();
			}
		});

        GetMeterInformationObjects();
        
    }

	private void GetMeterInformationObjects() {
    	Log.i("HomeActivity","GetMeterInformationObjects");
		
		String devicesUrl = url + "device?token=" + token;
		HTTPAsyncTask httpAsyncTask = new HTTPAsyncTask(GetMeterInformationObjectsAsyncTaskId,HomeActivity.this,devicesUrl,"GetJSONArray",(ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE));
		if(httpAsyncTask.checkConnectivity()) {
			try {
				httpAsyncTask.asyncResponse = this;
				httpAsyncTask.execute();
			}
			catch (Exception e) {
	        	Log.e("HomeActivity","HTTPAsyncTask-execute");
	        	e.printStackTrace();
	        }
		}
	}

	private void GetMeterReadingInformationObjects() {
    	Log.i("HomeActivity","GetMeterReadingInformationObjects");
    	
    	String beginEpochTime = String.valueOf(beginCalendar.getTime().getTime());
    	String endEpochTime = String.valueOf(endCalendar.getTime().getTime());
    	
    	//String Url = url + "feed/" + selectedMeterId + "?token=" + token;		
		String Url = url + "feed/" + selectedMeterId + "?token=" + token + "&from=" + beginEpochTime + "&to=" + endEpochTime;		
		HTTPAsyncTask httpAsyncTask = new HTTPAsyncTask(GetMeterReadingInformationObjectsAsyncTaskId,HomeActivity.this,Url,"GetJSONArray",(ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE));
		if(httpAsyncTask.checkConnectivity()) {
			try {
				httpAsyncTask.asyncResponse = this;
				httpAsyncTask.execute();
			}
			catch (Exception e) {
	        	Log.e("HomeActivity","HTTPAsyncTask-execute");
	        	e.printStackTrace();
	        }
		}
	}

	@Override
	public void processFinish(JSONParserReturnObject jsonParserReturnObject, int asyncTaskId) {
		switch(asyncTaskId) {
			case GetMeterInformationObjectsAsyncTaskId:
				if(jsonParserReturnObject.getStatusCode()==200) {
		        	meterInformationObjects = MeterInformationObjectsDeserialize(jsonParserReturnObject);
		    		if(meterInformationObjects!=null) {
		    	    	Log.i("HomeActivity","processFinish-successful");
		    	    	
		    	    	metersList = new LinkedHashMap<String, String>();
		    	    	List<String> metersListKeys = new ArrayList<String>();
			        	for(int i=0;i<meterInformationObjects.size();i++) {
			        		metersList.put(meterInformationObjects.get(i).GetName(),meterInformationObjects.get(i).GetId());
			        		metersListKeys.add(meterInformationObjects.get(i).GetName());
			        	}
			        	
			        	ArrayAdapter<String> metersListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, metersListKeys) {
			        		public View getView(int position, View convertView, ViewGroup parent) {
			                    View view = super.getView(position, convertView, parent);
			                    Typeface externalFont=Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address));
			                    ((TextView) view).setTypeface(externalFont);
			                    return view;
			        			}
					            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
					            	View view =super.getDropDownView(position, convertView, parent);
					            	Typeface externalFont=Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address));
				            		((TextView) view).setTypeface(externalFont);
					            	return view;
					            }
			        	};
			        	metersListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        	metersListSpinner.setAdapter(metersListAdapter);
			        	
			        	List<String> reportTypeList = new ArrayList<String>();
			            reportTypeList.add("گزارش جزئیات مصرف");
			            reportTypeList.add("نمودار تغییرات توان حقیقی");
			            reportTypeList.add("نمودار تغییرات توان راکتیو");
			            reportTypeList.add("نمودار تغییرات ولتاژ");
			            reportTypeList.add("نمودار تغییرات جریان");
			        	ArrayAdapter<String> reportTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,reportTypeList) {
			        		public View getView(int position, View convertView, ViewGroup parent) {
			                    View view = super.getView(position, convertView, parent);
			                    Typeface externalFont=Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address));
			                    ((TextView) view).setTypeface(externalFont);
			                    return view;
			        			}
					            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
					            	View view =super.getDropDownView(position, convertView, parent);
					            	Typeface externalFont=Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address));
				            		((TextView) view).setTypeface(externalFont);
					            	return view;
					            }
			        	};
			        	reportTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        	reportTypeSpinner.setAdapter(reportTypeAdapter);
			        	
			    		Toast toast = Toast.makeText(HomeActivity.this,"لیست کنتورها بروز رسانی شد",Toast.LENGTH_SHORT);
		        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		        		toast.show();
		    		}
		    		else {
		    			Log.e("HomeActivity","processFinish-can not parse jsonobject");
			    	
		    			Toast toast = Toast.makeText(HomeActivity.this,"اشکال در دریافت اطلاعات",Toast.LENGTH_SHORT);
		        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		        		toast.show();
		    		}
			        
			    }
			    else {
			    	Log.e("HomeActivity","processFinish-status code: "+jsonParserReturnObject.getStatusCode());
			    	
			    	Toast toast = Toast.makeText(HomeActivity.this,"اشکال در دریافت اطلاعات",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
			    }
				break;
				
			case GetMeterReadingInformationObjectsAsyncTaskId:
				if(jsonParserReturnObject.getStatusCode()==200) {
		        	meterReadingInformationObjects = MeterReadingInformationObjectsDeserialize(jsonParserReturnObject);
		    		if(meterReadingInformationObjects!=null) {
		    	    	Log.i("HomeActivity","processFinish-successful: "+String.valueOf(meterReadingInformationObjects.size())+" records");
		    	    	
		    	    	if(meterReadingInformationObjects.size()>0) {
			    	    	Intent intent;
					    	switch(selectedReportType) {
					    		case 0:
					    			intent = new Intent(HomeActivity.this, DetailsActivity.class);
					    		break;
					    		
					    		case 1:
					    		case 2:
					    		case 3:
					    		case 4:
						    		intent = new Intent(HomeActivity.this, GraphsActivity.class);
						    		intent.putExtra("selectedReportType", selectedReportType);
					    		break;
					    		
					    		default:
						    		intent = new Intent(HomeActivity.this, DetailsActivity.class);
				    			break;
					    			
					    	}
					    	intent.putExtra("meterReadingInformationObjects",(ArrayList<? extends Parcelable>) meterReadingInformationObjects);
					    	startActivity(intent);
		    	    	}
		    	    	else {
		    	    		Log.i("HomeActivity","processFinish-no reading");
					    	
			    			Toast toast = Toast.makeText(HomeActivity.this,"هیچ قرائتی در این بازه زمانی انجام نشده است",Toast.LENGTH_SHORT);
			        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
			        		toast.show();
		    	    	}
			    		
		    		}
		    		else {
		    			Log.e("HomeActivity","processFinish-can not parse jsonobject");
			    	
		    			Toast toast = Toast.makeText(HomeActivity.this,"اشکال در دریافت اطلاعات",Toast.LENGTH_SHORT);
		        		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		        		toast.show();
		    		}
			        
			    }
			    else {
			    	Log.e("HomeActivity","processFinish-status code: "+jsonParserReturnObject.getStatusCode());
			    	
			    	Toast toast = Toast.makeText(HomeActivity.this,"اشکال در دریافت اطلاعات",Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
			    }
				break;
				
			default :
		    	Log.e("HomeActivity","processFinish-asyncTaskId");
		    	Toast toast = Toast.makeText(HomeActivity.this,"اشکال در دریافت اطلاعات",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
				break;
		}
		
	}

	private List<MeterInformationObject> MeterInformationObjectsDeserialize(JSONParserReturnObject jsonParserReturnObject) {
		List<MeterInformationObject> meterInformationObjects = null;
		
	    if(jsonParserReturnObject.getJSONArray()!=null) {
			JSONArray jsonArray = jsonParserReturnObject.getJSONArray();
			meterInformationObjects = new ArrayList<MeterInformationObject>();
			try {
				for(int i=0;i<jsonArray.length();i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					MeterInformationObject meterInformationObject = new MeterInformationObject(userId);
					meterInformationObject.SetMeterInformationObjectFromJSONObject(jsonObject);
					meterInformationObjects.add(meterInformationObject);
				}
			} catch (JSONException e) {
				meterInformationObjects = null;
				e.printStackTrace();
			}
	    }
		return meterInformationObjects;
	}

	private List<MeterReadingInformationObject> MeterReadingInformationObjectsDeserialize(JSONParserReturnObject jsonParserReturnObject) {
		List<MeterReadingInformationObject> meterReadingInformationObjects = null;
        if(jsonParserReturnObject.getJSONArray()!=null) {
			JSONArray jsonArray = jsonParserReturnObject.getJSONArray();
			meterReadingInformationObjects = new ArrayList<MeterReadingInformationObject>();
			try {
				for(int i=0;i<jsonArray.length();i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					MeterReadingInformationObject meterReadingInformationObject = new MeterReadingInformationObject();
					if(meterReadingInformationObject.SetMeterReadingInformationObjectFromJSONObject(jsonObject))
						meterReadingInformationObjects.add(meterReadingInformationObject);

			    	Log.i("HomeActivity","MeterReadingInformationObjectsDeserialize-"+String.valueOf(i)+"'th jsonObject: "+jsonObject.toString());
				}
			} catch (JSONException e) {
				meterReadingInformationObjects = null;
				e.printStackTrace();
			}
        }
		return meterReadingInformationObjects;
	}

}
