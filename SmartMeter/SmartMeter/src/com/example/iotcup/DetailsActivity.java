package com.example.iotcup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	
	private List<MeterReadingInformationObject> meterReadingInformationObjects;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        
        ListView detailsListView = (ListView) findViewById(R.id.details_listView);
        
        meterReadingInformationObjects = new ArrayList<MeterReadingInformationObject>();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        	meterReadingInformationObjects = bundle.getParcelableArrayList("meterReadingInformationObjects");
        
        Collections.sort(meterReadingInformationObjects);
        Collections.reverse(meterReadingInformationObjects);
        
        ListAdapter listAdapter = new ListAdapter(DetailsActivity.this, R.layout.details_activity_listview_item, meterReadingInformationObjects);
        detailsListView.setAdapter(listAdapter);
	}
	
	private class ListAdapter extends ArrayAdapter<MeterReadingInformationObject> {

	    public ListAdapter(Context context, int textViewResourceId) {
	        super(context, textViewResourceId);
	    }

	    public ListAdapter(Context context, int resource, List<MeterReadingInformationObject> items) {
	        super(context, resource, items);
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (view == null) {
	            LayoutInflater layoutInflater;
	            layoutInflater = LayoutInflater.from(getContext());
	            view = layoutInflater.inflate(R.layout.details_activity_listview_item, null);
	        }

	        MeterReadingInformationObject meterReadingInformationObject = getItem(position);

	        if (meterReadingInformationObject != null) {
	        	TextView timeNameTextView = (TextView) view.findViewById(R.id.time_name_textView);
	            TextView sumActiveNameTextView = (TextView) view.findViewById(R.id.sumActive_name_textView);
	            TextView sumReactiveNameTextView = (TextView) view.findViewById(R.id.sumReactive_name_textView);
	            TextView L1VoltageNameTextView = (TextView) view.findViewById(R.id.L1Voltage_name_textView);
	            TextView L2VoltageNameTextView = (TextView) view.findViewById(R.id.L2Voltage_name_textView);
	            TextView L3VoltageNameTextView = (TextView) view.findViewById(R.id.L3Voltage_name_textView);
	            TextView L1CurrentNameTextView = (TextView) view.findViewById(R.id.L1Current_name_textView);
	            TextView L2CurrentNameTextView = (TextView) view.findViewById(R.id.L2Current_name_textView);
	            TextView L3CurrentNameTextView = (TextView) view.findViewById(R.id.L3Current_name_textView);
	            TextView timeValueTextView = (TextView) view.findViewById(R.id.time_value_textView);
	            TextView sumActiveValueTextView = (TextView) view.findViewById(R.id.sumActive_value_textView);
	            TextView sumReactiveValueTextView = (TextView) view.findViewById(R.id.sumReactive_value_textView);
	            TextView L1VoltageValueTextView = (TextView) view.findViewById(R.id.L1Voltage_value_textView);
	            TextView L2VoltageValueTextView = (TextView) view.findViewById(R.id.L2Voltage_value_textView);
	            TextView L3VoltageValueTextView = (TextView) view.findViewById(R.id.L3Voltage_value_textView);
	            TextView L1CurrentValueTextView = (TextView) view.findViewById(R.id.L1Current_value_textView);
	            TextView L2CurrentValueTextView = (TextView) view.findViewById(R.id.L2Current_value_textView);
	            TextView L3CurrentValueTextView = (TextView) view.findViewById(R.id.L3Current_value_textView);
	            timeNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            sumActiveNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            sumReactiveNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L1VoltageNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L2VoltageNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L3VoltageNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L1CurrentNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L2CurrentNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L3CurrentNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            timeValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            sumActiveValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            sumReactiveValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L1VoltageValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L2VoltageValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L3VoltageValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L1CurrentValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L2CurrentValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            L3CurrentValueTextView.setTypeface(Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_address)));
	            
	            if (timeValueTextView!=null) {
	            	timeValueTextView.setText(String.valueOf(meterReadingInformationObject.GetHumanReadableTime()));
	            }
	            if (sumActiveValueTextView!=null) {
	            	sumActiveValueTextView.setText(String.valueOf(meterReadingInformationObject.GetSumActive()));
	            }
	            if (sumReactiveValueTextView!=null) {
	            	sumReactiveValueTextView.setText(String.valueOf(meterReadingInformationObject.GetSumReactive()));
	            }
	            if (L1VoltageValueTextView!=null) {
	            	L1VoltageValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL1Voltage()));
	            }
	            if (L2VoltageValueTextView!=null) {
	            	L2VoltageValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL2Voltage()));
	            }
	            if (L3VoltageValueTextView!=null) {
	            	L3VoltageValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL3Voltage()));
	            }
	            if (L1CurrentValueTextView!=null) {
	            	L1CurrentValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL1Current()));
	            }
	            if (L2CurrentValueTextView!=null) {
	            	L2CurrentValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL2Current()));
	            }
	            if (L3CurrentValueTextView!=null) {
	            	L3CurrentValueTextView.setText(String.valueOf(meterReadingInformationObject.GetL3Current()));
	            }
	            
	        }

	        return view;
	    }

	}
}