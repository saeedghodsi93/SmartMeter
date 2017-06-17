package com.example.iotcup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MeterReadingInformationObject implements Parcelable, Comparable<MeterReadingInformationObject> {

	Calendar time;
	int sumActive, sumReactivePos, sumReactiveNeg;
	int L1Voltage, L2Voltage, L3Voltage;
	int L1Current, L2Current, L3Current;
	String id;
	
	public MeterReadingInformationObject() {
		time = Calendar.getInstance();
		sumActive = 0;
		sumReactivePos = 0;
		sumReactiveNeg = 0;
		L1Voltage = 0;
		L2Voltage = 0;
		L3Voltage = 0;
		L1Current = 0;
		L2Current = 0;
		L3Current = 0;
		id = "";
	}
	
	public boolean SetMeterReadingInformationObjectFromJSONObject(JSONObject jsonObject) {
		long timeStamp=0;
		JSONObject dataJSONObject = null;
		JSONObject electricalInformationsJSONObject = null;
		JSONObject errorInformationObject = null;
		
		try {
			timeStamp = jsonObject.getLong("timestamp");
			time.setTime(new Date(timeStamp));
			id = new String(jsonObject.getString("_id"));
		} catch (Exception e) {
        	Log.e("MeterReadingInformationObject","SetMeterReadingInformationObjectFromJSONObject-wrong timestamp: "+timeStamp);
			e.printStackTrace();
			return false;
		}
    	
		try {
			dataJSONObject = jsonObject.getJSONObject("data");
		} catch (JSONException e) {
        	Log.e("MeterReadingInformationObject","SetMeterReadingInformationObjectFromJSONObject-no data object");
			e.printStackTrace();
			return false;
		}
		
		try {
			electricalInformationsJSONObject = dataJSONObject.getJSONObject("electrical");
		} catch (JSONException e) {
        	Log.e("MeterReadingInformationObject","SetMeterReadingInformationObjectFromJSONObject-no electrical object");
			e.printStackTrace();
			return false;
		}
		
		sumActive = 0;
		sumReactivePos = 0;
		sumReactiveNeg = 0;
		L1Voltage = 0;
		L2Voltage = 0;
		L3Voltage = 0;
		L1Current = 0;
		L2Current = 0;
		L3Current = 0;
		
		try {
			sumActive = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("sum_active"))*1000));
		} catch (Exception e) {
		}
		
		try {
			sumReactivePos = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("sum_reactive_pos"))*1000));
		} catch (Exception e) {
		}

		try {
			sumReactiveNeg = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("sum_reactive_neg"))*1000));
		} catch (Exception e) {
		}

		try {
			L1Voltage = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L1_voltage"))));
		} catch (Exception e) {
		}

		try {
			L2Voltage = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L2_voltage"))));
		} catch (Exception e) {
		}

		try {
			L3Voltage = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L3_voltage"))));
		} catch (Exception e) {
		}

		try {
			L1Current = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L1_current"))*1000));
		} catch (Exception e) {
		}

		try {
			L2Current = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L2_current"))*1000));
		} catch (Exception e) {
		}

		try {
			L3Current = (int) ((Double.parseDouble(electricalInformationsJSONObject.getString("L3_current"))*1000));
		} catch (Exception e) {
		}
			
		return true;
	}
	
	public Calendar GetTime() {
		return time;
	}
	
	public String GetHumanReadableTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm",java.util.Locale.getDefault());
		return simpleDateFormat.format(time.getTime());
	}
	
	public int GetSumActive() {
		return sumActive;
	}
	
	public int GetSumReactivePos() {
		return sumReactivePos;
	}

	public int GetSumReactiveNeg() {
		return sumReactiveNeg;
	}

	public int GetSumReactive() {
		return sumReactiveNeg - sumReactivePos;
	}
	
	public int GetL1Voltage() {
		return L1Voltage;
	}

	public int GetL2Voltage() {
		return L2Voltage;
	}

	public int GetL3Voltage() {
		return L3Voltage;
	}

	public int GetL1Current() {
		return L1Current;
	}

	public int GetL2Current() {
		return L2Current;
	}

	public int GetL3Current() {
		return L3Current;
	}
	
	public String GetId() {
		return id;
	}
	
	public MeterReadingInformationObject(Parcel in) {
		this.time = Calendar.getInstance();
		try {
			this.time.setTime(new Date(in.readLong()));
			this.sumActive=in.readInt();
			this.sumReactivePos =in.readInt();
			this.sumReactiveNeg=in.readInt();
			this.L1Voltage=in.readInt();
			this.L2Voltage=in.readInt();
			this.L3Voltage=in.readInt();
			this.L1Current=in.readInt();
			this.L2Current=in.readInt();
			this.L3Current=in.readInt();
			this.id=in.readString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.time.getTime().getTime());
		out.writeInt(this.sumActive);
		out.writeInt(this.sumReactivePos);
		out.writeInt(this.sumReactiveNeg);
		out.writeInt(this.L1Voltage);
		out.writeInt(this.L2Voltage);
		out.writeInt(this.L3Voltage);
		out.writeInt(this.L1Current);
		out.writeInt(this.L2Current);
		out.writeInt(this.L3Current);
		out.writeString(this.id);
	}

	public static final Parcelable.Creator<MeterReadingInformationObject> CREATOR = 
			new Parcelable.Creator<MeterReadingInformationObject>() {
				@Override
				public MeterReadingInformationObject createFromParcel(Parcel in) {
					return new MeterReadingInformationObject(in);
				}
				@Override
				public MeterReadingInformationObject[] newArray(int size) {
					return new MeterReadingInformationObject[size];
				}
			};

	@Override
	public int compareTo(MeterReadingInformationObject obj) {
		return this.time.compareTo(obj.GetTime());
	}
	
}
