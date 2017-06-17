package com.example.iotcup;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class Database {
	private static final String DATABASE_NAME = "IOTCup";
	private static final int DATABASE_VERSION = 11;

	private OpenHelper mDatabaseOpenHelper;
	Context context;

	public Database(Context _context)  {
		Log.i("Database","Database");
		
		mDatabaseOpenHelper = new OpenHelper(_context);
		this.context = _context;
	}

	public void UpdateLoginInformationObject(final LoginInformationObject loginInformationObject) {
		Log.i("Database","UpdateLoginInformationObject");
		
		((Thread) new Thread() {
			public void run() {
				try {
					mDatabaseOpenHelper.getWritableDatabase();
					mDatabaseOpenHelper.UpdateLoginInformationObject(loginInformationObject);
				}
				catch(Exception e) {
					Log.e("Database","UpdateLoginInformationObject Exception");
				}
				finally {
					mDatabaseOpenHelper.close();
				}
			}
		}).start();
	}
	
	public void UpdateToken(final String token) {
		Log.i("Database","UpdateToken");
		
		((Thread) new Thread() {
			public void run() {
				try {
					mDatabaseOpenHelper.getWritableDatabase();
					mDatabaseOpenHelper.UpdateToken(token);
				}
				catch(Exception e) {
					Log.e("Database","UpdateToken Exception");
					e.printStackTrace();
				}
				finally {
					mDatabaseOpenHelper.close();
				}
			}
		}).start();
	}

	public void UpdateMeterInformationObjects(final List<MeterInformationObject> meterInformationObjects) {
		Log.i("Database","UpdateMeterInformationObject");
		
		((Thread) new Thread() {
			public void run() {
				try {
					mDatabaseOpenHelper.getWritableDatabase();
					mDatabaseOpenHelper.UpdateMeterInformationObjects(meterInformationObjects);
				}
				catch(Exception e) {
					Log.e("Database","UpdateMeterInformationObject Exception");
				}
				finally {
					mDatabaseOpenHelper.close();
				}
			}
		}).start();
	}
	
	public LoginInformationObject GetLoginInformationObject() {
		Log.i("Database","GetLoginInformationObject");
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables("LoginInformationObject");
		Cursor dbResults = builder.query(mDatabaseOpenHelper.getReadableDatabase(),new String[]{"user","pass"},null, null, null, null, null);
		dbResults.moveToFirst();
				
		LoginInformationObject loginInformationObject = null;
		if(dbResults.getCount() > 0) {
			try {
				loginInformationObject = new LoginInformationObject(
						dbResults.getString(0),
						dbResults.getString(1));
			}
			catch(Exception e) {
				Log.e("Database","GetLoginInformationObject-Exception");
				e.printStackTrace();
				loginInformationObject = null;
			}
		
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return loginInformationObject;
		}
		else {
			Log.e("Database","GetLoginInformationObject-no Object");
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return null;
		}
	}
	
	public String GetToken() {
		Log.i("Database","GetToken");
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables("Token");
		Cursor dbResults = builder.query(mDatabaseOpenHelper.getReadableDatabase(),new String[]{"token"},null, null, null, null, null);
		dbResults.moveToFirst();
				
		String token = null;
		if(dbResults.getCount() > 0) {
			try {
				token = new String(dbResults.getString(0));
			}
			catch(Exception e) {
				Log.e("Database","GetToken-Exception");
				e.printStackTrace();
				token = null;
			}
		
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return token;
		}
		else {
			Log.e("Database","GetToken-no Object");
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return null;
		}
	}
	
	public List<MeterInformationObject> GetMeterInformationObjects() {
		Log.i("Database","GetMeterInformationObject");
		
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables("MeterInformationObject");
		Cursor dbResults = builder.query(mDatabaseOpenHelper.getReadableDatabase(),new String[]{"userId","id","address"},null, null, null, null, null);
		dbResults.moveToFirst();
				
		List<MeterInformationObject> meterInformationObjects = new ArrayList<MeterInformationObject>();
		if(dbResults.getCount() > 0) {
			while(dbResults.moveToNext())
			{
				try {
					meterInformationObjects.add(new MeterInformationObject(
							dbResults.getString(0),
							dbResults.getString(1),
							dbResults.getString(2)));
				}
				catch(Exception e) {
					Log.e("Database","GetMeterInformationObject-Exception");
					e.printStackTrace();
				}
			}
			
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return meterInformationObjects;
		}
		else {
			Log.e("Database","GetMeterInformationObject-no Object");
			if(dbResults != null)
				dbResults.close();
			
			mDatabaseOpenHelper.close();
			return null;
		}
	}
	
	public void FlushDB() {
		Log.i("Database","FlushDB");
		
		try {
			mDatabaseOpenHelper.getWritableDatabase();
			mDatabaseOpenHelper.FlushDB();
			
		}
		catch(Exception e) {
			Log.e("Database","FlushDB Exception");
		}
		finally {
			if(mDatabaseOpenHelper != null)
				mDatabaseOpenHelper.close();
		}
	}
	
	public void Close() {
		Log.i("Database","Close");
		
		mDatabaseOpenHelper.close();
	}

	private static class OpenHelper extends SQLiteOpenHelper  {
		private SQLiteDatabase mDatabase;
		
		OpenHelper(Context _context) {
			super(_context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.i("OpenHelper","OpenHelper");
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			Log.i("OpenHelper","onOpen");
			
			mDatabase = db;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("OpenHelper","onCreate");
			
			mDatabase = db;
			try {
				Log.i("OpenHelper","Creating Tables");
				
				mDatabase.execSQL("CREATE TABLE \"LoginInformationObject\" (\"TABLEID\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," +
						"\"user\" TEXT," +
						"\"pass\" TEXT )");
				
				mDatabase.execSQL("CREATE TABLE \"Token\" (\"TABLEID\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," +
						"\"token\" TEXT )");
				
				mDatabase.execSQL("CREATE TABLE \"MeterInformationObject\" (\"TABLEID\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," +
						"\"userId\" TEXT," +
						"\"id\" TEXT," +
						"\"address\" TEXT )");
			}
			catch(SQLiteException s) {
				Log.e("OpenHelper","OnCreate SQLiteException");
			}
			catch(Exception e) {
				Log.e("OpenHelper","OnCreate Exception");
			}
		}
		
		private void UpdateLoginInformationObject(LoginInformationObject loginInformationObject) {
			Log.i("OpenHelper","UpdateLoginInformationObject");
			
			try {
				mDatabase.beginTransaction();
				mDatabase.delete("LoginInformationObject", null, null);
	
				ContentValues values = new ContentValues(2);
				try {
					values.put("user",loginInformationObject.Getuser());
					values.put("pass",loginInformationObject.Getpass());
					
					mDatabase.insert("LoginInformationObject", null, values);
					mDatabase.setTransactionSuccessful();
				}
				catch (Exception e) {
					Log.e("OpenHelper","UpdateLoginInformationObject Values Exception");
					mDatabase.delete("LoginInformationObject", null, null);
				}
				
			}
			catch(Exception e) {
				Log.e("OpenHelper","UpdateLoginInformationObject Exception");
			}
			finally {
				mDatabase.endTransaction();
			}
		}
		
		private void UpdateToken(String token) {
			Log.i("OpenHelper","UpdateToken");
			
			try {
				mDatabase.beginTransaction();
				mDatabase.delete("Token", null, null);
	
				ContentValues values = new ContentValues(2);
				try {
					values.put("token",token);
					
					mDatabase.insert("Token", null, values);
					mDatabase.setTransactionSuccessful();
				}
				catch (Exception e) {
					Log.e("OpenHelper","UpdateToken Values Exception");
					mDatabase.delete("Token", null, null);
				}
				
			}
			catch(Exception e) {
				Log.e("OpenHelper","UpdateToken Exception");
			}
			finally {
				mDatabase.endTransaction();
			}
		}
		
		private void UpdateMeterInformationObjects(List<MeterInformationObject> meterInformationObjects) {
			Log.i("OpenHelper","UpdateMeterInformationObject");
			
			int count = 0;
			
			if(meterInformationObjects != null)
				count = meterInformationObjects.size();
			
			try {
				mDatabase.beginTransaction();
				mDatabase.delete("MeterInformationObject", null, null);
				
				for(int i=0; i<count; i++)
				{
					MeterInformationObject currentMeterInformationObject = null;
	
					ContentValues values = new ContentValues(2);
					try {
						currentMeterInformationObject = meterInformationObjects.get(i);
						values.put("userId",currentMeterInformationObject.GetUserId());
						values.put("id",currentMeterInformationObject.GetId());
						values.put("address",currentMeterInformationObject.GetAddress());
						
						mDatabase.insert("MeterInformationObject", null, values);
					}
					catch (Exception e) {
						Log.e("OpenHelper","UpdateMeterInformationObject Values Exception");
						mDatabase.delete("MeterInformationObject", null, null);
					}
				}
				mDatabase.setTransactionSuccessful();
				
			}
			catch(Exception e) {
				Log.e("OpenHelper","UpdateMeterInformationObject Exception");
			}
			finally {
				mDatabase.endTransaction();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("OpenHelper","onUpgrade");
			
			try {
				db.execSQL("DROP TABLE IF EXISTS LoginInformationObject");
				db.execSQL("DROP TABLE IF EXISTS Token");
				db.execSQL("DROP TABLE IF EXISTS MeterInformationObject");
			}
			catch(Exception e) {
				Log.e("OpenHelper","onUpgrade Exception");
			}
			
			onCreate(db);
		}

		private void FlushDB() {
			Log.i("OpenHelper","FlushDB");
			
			try {
				mDatabase.beginTransaction();
				mDatabase.delete("LoginInformationObject", null, null);
				mDatabase.delete("Token", null, null);
				mDatabase.delete("MeterInformationObject", null, null);
				mDatabase.setTransactionSuccessful();
			}
			catch(Exception e) {
				Log.e("OpenHelper","FlushDB Exception");
			}
			finally {
				if(mDatabase != null)
					mDatabase.endTransaction();
			}
		}
	}
	
}
