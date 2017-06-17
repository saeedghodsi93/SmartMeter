package com.example.iotcup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	
    public JSONParser() {
    	Log.i("JSONParser","JSONParser");
    	
    }
 
    public JSONParserReturnObject getJSONObjectFromUrl(String url) {
    	Log.i("JSONParser","getJSONObjectFromUrl: "+url);
    	
    	int statusCode = 0;
	    HttpGet httpGet=null;
	    HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
		int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
	    	httpGet = new HttpGet(url);
	    } catch(Exception e) {
			Log.e("JSONParser","getJSONObjectFromUrl-url to HttpPost");
			e.printStackTrace();
	    }
	    if(httpGet==null) {
			return jParserReturnObject;
		}
	    
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			Log.e("JSONParser","getJSONObjectFromUrl-httpClient.execute");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getJSONObjectFromUrl-httpClient.execute");
			e.printStackTrace();
		}
		if(httpResponse==null) {
			return jParserReturnObject;
		}
		
		statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
		try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","getJSONObjectFromUrl-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","getJSONObjectFromUrl-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","getJSONObjectFromUrl-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","getJSONObjectFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getJSONObjectFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}

    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
    	
        try {
			JSONObject jObj = new JSONObject(json);
			jParserReturnObject.setJSONObject(jObj);
		} catch (JSONException e) {
			Log.e("JSONParser","getJSONObjectFromUrl-string to JSONObject");
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
			e.printStackTrace();
		}
        return jParserReturnObject;
        
	}

    public JSONParserReturnObject getJSONArrayFromUrl(String url) {
    	Log.i("JSONParser","getJSONArrayFromUrl: "+url);
    	
    	int statusCode = 0;
	    HttpGet httpGet=null;
	    HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
		int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
	    	httpGet = new HttpGet(url);
	    } catch(Exception e) {
			Log.e("JSONParser","getJSONArrayFromUrl-url to HttpPost");
			e.printStackTrace();
	    }
	    if(httpGet==null) {
			return jParserReturnObject;
		}
	    
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			Log.e("JSONParser","getJSONArrayFromUrl-httpClient.execute");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getJSONArrayFromUrl-httpClient.execute");
			e.printStackTrace();
		}
		if(httpResponse==null) {
			return jParserReturnObject;
		}
		
		statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
		try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","getJSONArrayFromUrl-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","getJSONArrayFromUrl-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","getJSONArrayFromUrl-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","getJSONArrayFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getJSONArrayFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}
		
    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
    	
        try {
			JSONArray jObj = new JSONArray(json);
			jParserReturnObject.setJSONArray(jObj);
		} catch (JSONException e) {
			Log.e("JSONParser","getJSONArrayFromUrl-string to JSONArray");
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
			e.printStackTrace();
		}
        return jParserReturnObject;
        
	}
    
    public JSONParserReturnObject getStringFromUrl(String url) {
    	Log.i("JSONParser","getStringFromUrl: "+url);
    	
    	int statusCode = 0;
	    HttpGet httpGet=null;
	    HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
		int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
	    	httpGet = new HttpGet(url);
	    } catch(Exception e) {
			Log.e("JSONParser","getStringFromUrl-url to HttpPost");
			e.printStackTrace();
	    }
	    if(httpGet==null) {
			return jParserReturnObject;
		}
	    
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			Log.e("JSONParser","getStringFromUrl-httpClient.execute");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getStringFromUrl-httpClient.execute");
			e.printStackTrace();
		}
		if(httpResponse==null) {
			return jParserReturnObject;
		}
		
		statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
		try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","getStringFromUrl-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","getStringFromUrl-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","getStringFromUrl-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","getStringFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","getStringFromUrl-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}
		
    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);

		jParserReturnObject.setString(json);
		
        return jParserReturnObject;
        
	}
    
    public JSONParserReturnObject postJSONObjectToUrlAndGetJSONObject(String url,JSONObject jsonInput) {
    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONObject: "+url);

    	HttpPost httpPost = null;
		StringEntity stringEntity = null;
    	int statusCode = 0;
    	HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
        int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
			httpPost = new HttpPost(url);
	    } catch(Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-url to HttpPost");
			e.printStackTrace();
	    }
		if(httpPost==null) {
			return jParserReturnObject;
		}
		
    	httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-type","application/json");
		
		try {
			stringEntity = new StringEntity(jsonInput.toString(),"UTF-8");
		} catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-jsonInput.toString");
			e.printStackTrace();
		}
		if(stringEntity==null) {
			return jParserReturnObject;
		}
		
        try {
    	    httpPost.setEntity(stringEntity);
	    	httpResponse = httpClient.execute(httpPost);
	    	
        } catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-httpClient.execute");
        	e.printStackTrace();
        }
        if(httpResponse==null) {
			return jParserReturnObject;
		}
        
        statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
        try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}

    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
    	
        try {
        	JSONObject jsonObject = new JSONObject(json);
        	jParserReturnObject.setJSONObject(jsonObject);
        	
		} catch (JSONException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-string to JSONObject");
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONObject-returned string: "+json);
			e.printStackTrace();
		}
        return jParserReturnObject;
    }
    
    public JSONParserReturnObject postJSONObjectToUrlAndGetJSONArray(String url,JSONObject jsonInput) {
    	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONArray: "+url);

    	HttpPost httpPost = null;
		StringEntity stringEntity = null;
    	int statusCode = 0;
    	HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
        int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
			httpPost = new HttpPost(url);
	    } catch(Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-url to HttpPost");
			e.printStackTrace();
	    }
		if(httpPost==null) {
			return jParserReturnObject;
		}
		
    	httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-type","application/json");
		
		try {
			stringEntity = new StringEntity(jsonInput.toString(),"UTF-8");
		} catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-jsonInput.toString");
			e.printStackTrace();
		}
		if(stringEntity==null) {
			return jParserReturnObject;
		}
		
        try {
    	    httpPost.setEntity(stringEntity);
	    	httpResponse = httpClient.execute(httpPost);
	    	
        } catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-httpClient.execute");
        	e.printStackTrace();
        }
        if(httpResponse==null) {
			return jParserReturnObject;
		}
        
        statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
        try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}
		
        try {
        	JSONArray jsonArray = new JSONArray(json);
        	jParserReturnObject.setJSONArray(jsonArray);
        	Log.i("JSONParser","postJSONObjectToUrlAndGetJSONArray-returned string: "+json);
        	
		} catch (JSONException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-string to JSONArray");
			Log.e("JSONParser","postJSONObjectToUrlAndGetJSONArray-returned string: "+json);
			e.printStackTrace();
		}
        return jParserReturnObject;
    }
    
    public JSONParserReturnObject postJSONObjectToUrlAndGetString(String url,JSONObject jsonInput) {
    	Log.i("JSONParser","postJSONObjectToUrlAndGetString: "+url);

    	HttpPost httpPost = null;
		StringEntity stringEntity = null;
    	int statusCode = 0;
    	HttpResponse httpResponse = null;
    	HttpEntity httpEntity = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String json = "";
    	JSONParserReturnObject jParserReturnObject = new JSONParserReturnObject();
        
        int timeoutConnection = 15000;
		int timeoutSocket = 15000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
	    
	    try {
			httpPost = new HttpPost(url);
	    } catch(Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetString-url to HttpPost");
			e.printStackTrace();
	    }
		if(httpPost==null) {
			return jParserReturnObject;
		}
		
    	httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-type","application/json");
		
		try {
			stringEntity = new StringEntity(jsonInput.toString(),"UTF-8");
		} catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetString-jsonInput.toString");
			e.printStackTrace();
		}
		if(stringEntity==null) {
			return jParserReturnObject;
		}
		
        try {
    	    httpPost.setEntity(stringEntity);
	    	httpResponse = httpClient.execute(httpPost);
	    	
        } catch (Exception e) {
        	Log.e("JSONParser","postJSONObjectToUrlAndGetString-httpClient.execute");
        	e.printStackTrace();
        }
        if(httpResponse==null) {
			return jParserReturnObject;
		}
        
        statusCode = httpResponse.getStatusLine().getStatusCode();
    	jParserReturnObject.setStatusCode(statusCode);
    	
        try {
	    	httpEntity = httpResponse.getEntity();
		}  catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetString-httpResponse.getEntity");
			e.printStackTrace();
		}
		if(httpEntity==null) {
			return jParserReturnObject;
		}
		
		try {
			inputStream = httpEntity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetString-httpEntity.getContent");
			e.printStackTrace();
		}
		if(bufferedReader.toString().isEmpty()) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetString-"+bufferedReader.toString());
			return jParserReturnObject;
		}
		
		try {
	        StringBuilder stringBuilder= new StringBuilder();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	        	stringBuilder.append(line + "\n");
	        }
	        inputStream.close();
	        json = stringBuilder.toString();
		} catch (IllegalStateException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetString-bufferedReader.readLine");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("JSONParser","postJSONObjectToUrlAndGetString-bufferedReader.readLine");
			e.printStackTrace();
		}
		if(json.isEmpty()) {
			return jParserReturnObject;
		}

    	jParserReturnObject.setString(json);
    	
        return jParserReturnObject;
    }
    
    public class JSONParserReturnObject {
    	JSONObject jsonObject;
    	JSONArray jsonArray;
    	String string;
    	int statusCode;
    	
    	public JSONParserReturnObject() {
			jsonObject = null;
			jsonArray = null;
			string = "";
			statusCode = 0;
		}
    	
    	public void setJSONObject(JSONObject _jsonObject) {
    		jsonObject = _jsonObject;
    	}
    	
    	public void setJSONArray(JSONArray _jsonArray) {
    		jsonArray = _jsonArray;
    	}
    	
    	public void setString(String _string) {
    		string = _string;
    	}
    	
    	public void setStatusCode(int _statusCode) {
    		statusCode = _statusCode;
    	}
    	
    	public JSONObject getJSONObject() {
    		return jsonObject;
    	}

    	public JSONArray getJSONArray() {
    		return jsonArray;
    	}

    	public String getString() {
    		return string;
    	}
    	
    	public int getStatusCode() {
    		return statusCode;
    	}
    }
    
}
