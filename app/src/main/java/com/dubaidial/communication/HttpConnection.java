package com.dubaidial.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.util.Log;

import com.dubaidial.Utils.Constants;
import com.dubaidial.listeners.HttpConnectionResponse;

public class HttpConnection implements Runnable
{
	private String Url;
	private List<NameValuePair> data;
	private HttpConnectionResponse httpResponse;
	
	public HttpConnection(String Url,List<NameValuePair> data,Activity activity)
	{
		this.Url = Url;
		this.data =  data;
		this.httpResponse = (HttpConnectionResponse) activity;
	}
	
	@Override
	public void run()
	{	   
		try
		{
			Log.e("----------------------------> ", "------>"+Url);
			Log.e("----------------------------> ", "------>"+data);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			if(data == null)
			{
				String SetServerString = "";
                HttpGet httpget = new HttpGet(Url);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                SetServerString = httpclient.execute(httpget, responseHandler);
                if (SetServerString != null) 
		    	{
		            Log.e("Response", SetServerString);
		            httpResponse.onHttpServiceResponse(true,SetServerString);
		    	}
		    	else
		    	{
		            httpResponse.onHttpServiceResponse(false,"Get Response is null");
		    	}
			}
			else
			{
				HttpPost httpost = new HttpPost(Url);
				
//		    	httpost.setEntity(new ByteArrayEntity(data.toString().getBytes("UTF8")));
				httpost.setEntity(new UrlEncodedFormEntity(data));
		    	httpost.setHeader("Accept", "application/json");
//		    	httpost.setHeader("Accept", "application/x-www-form-urlencoded");
		    	HttpParams params = httpclient.getParams();
	    	    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, new Integer(Constants.TIMEOUT));
	    	    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, new Integer(Constants.TIMEOUT));
	    	    httpclient.setParams(params);
		    	httpost.setHeader("Content-type", "application/json");
		    	httpost.setHeader("Content-type", "application/x-www-form-urlencoded");

	    		Log.e("----------------------------> ", ""+httpost.toString());
		    	HttpResponse response = httpclient.execute(httpost);
		    	HttpEntity entity = response.getEntity();
		    	if (entity != null) 
		    	{
		    		Log.e("----------------------------> ", "not null");
		            InputStream instream = entity.getContent();
		            String  result= convertStreamToString(instream);
		            Log.e("Response", result);
		            httpResponse.onHttpServiceResponse(true,result);
		            
		    	}
		    	else
		    	{
		            httpResponse.onHttpServiceResponse(false,"entity is null");
		    	}
			}
			
	    }
	    catch (Exception e)
	    {
            httpResponse.onHttpServiceResponse(false,e.toString());
	    	e.printStackTrace();
		}
	}
	
	private static String convertStreamToString(InputStream is) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try 
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				is.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
};