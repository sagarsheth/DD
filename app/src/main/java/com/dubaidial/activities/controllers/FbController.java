package com.dubaidial.activities.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.communication.HttpConnection;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;

public class FbController {
	String name ,email,gender;
	public FbController(Activity activity){
//		loginToFacebook(activity);
//		newWayToLoingInFb(activity);
		
		fb(activity);
	}
	
	
	private static Session openActiveSession(Activity activity, boolean allowLoginUI, Session.StatusCallback callback, List<String> permissions) {
	    Session.OpenRequest openRequest = new Session.OpenRequest(activity).setPermissions(permissions).setCallback(callback);
	    Session session = new Session.Builder(activity).build();
	    if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState()) || allowLoginUI) {
	        Session.setActiveSession(session);
	        session.openForRead(openRequest);
	        return session;
	    }
	    return null;
	}
	
	private void fb(final Activity activity){
		List<String> permissions = new ArrayList<String>();
		permissions.add("email");
		openActiveSession(activity, true, new Session.StatusCallback() {
    	    @SuppressWarnings("deprecation")
			@Override
    	    public void call(Session session, SessionState state, Exception exception) {
    	        if (session.isOpened()) {
    	            //make request to the /me API
    	            Log.e("sessionopened", "true");
    	            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
    	                @Override
    	                public void onCompleted(GraphUser user, Response response) {
    	                    if (user != null) {
    	                        name = user.getFirstName()+" "+user.getLastName();
    	                        email = user.getProperty("email").toString();
    	                        String json = response.getRawResponse();
    	    					System.out.println("=== jsong === " + json);
    	                    	
    	    					JSONObject profile;
    							try {
    								profile = new JSONObject(json);
    								gender = profile.getString("gender");    		    					
    							} catch (JSONException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    	                        

    	                        Log.e("facebookid", gender);
    	                        Log.e("firstName", name);
    	                        Log.e("email", email);
    	                        
    	                        activity.runOnUiThread(new Runnable() {
    								
    								@Override
    								public void run() {
    									List<NameValuePair> request = new ArrayList<NameValuePair>(7);
    									request.add(new BasicNameValuePair("emailid", email));
    									request.add(new BasicNameValuePair("fullname", name));
    									request.add(new BasicNameValuePair("mobileno", ""));
    									request.add(new BasicNameValuePair("gender", gender));
    									request.add(new BasicNameValuePair("platform ", "Facebook"));
    									request.add(new BasicNameValuePair("os", Config.PLATFORM));
//    									
    									HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SOCIALMEDIA, request, activity);
    									Thread td = new Thread(connect);
    									td.start();
    								}
    							});
    	                        
    	                    }
    	                }
    	            });
    	         }
    	     }
    	 }, permissions);
	}
	
	/*
	private void newWayToLoingInFb(final Activity activity){
		// start Facebook Login
		
        Session.openActiveSession(activity, true, new Session.StatusCallback() {

          // callback when session changes state
        	

		@Override
		public void call(Session session, SessionState state, Exception exception) {
            if (session.isOpened()) {

                // make request to the /me API
                Request.newMeRequest(session, new Request.GraphUserCallback() {

                  // callback after Graph API response with user object

				@Override
				public void onCompleted(final GraphUser user, Response response) {
					
                    if (user != null) {
                    	Log.e("rrrrrrrrrrrrrrrrrrr", response.toString());
                    	System.out.println("USER : "+user.getFirstName() +" "+response.getGraphObject().getInnerJSONObject().toString());
                    	
                    	String json = response.getRawResponse();

    					System.out.println("=== jsong === " + json);
                    	
    					JSONObject profile;
						try {
							profile = new JSONObject(json);
							// getting name of the user
	    					name = profile.getString("name");

	    					gender = profile.getString("gender");
	    					// getting email of the user
	    					 email = user.asMap().get("email").toString();

	                         System.out.println();
	    					
	    					System.out.println("email : "+ email);
	    					System.out.println("name : "+ name);
	                    	
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    					
    					
                    	
                       
                      }else System.out.println("onComplete null ");
                    }
                }).executeAsync();
              }else{
            	  System.out.println("else : "+state.isOpened() +" "+session.getAccessToken() +" "
            			  + session.getApplicationId() +" ");
              }
            }
        });
	}
		
	private SharedPreferences mPrefs;
	// Your Facebook APP ID 337661936386046
		private static String APP_ID = "337661936386046"; // Replace with your App ID 308180782571605

		// Instance of Facebook Class
		private Facebook facebook = new Facebook(APP_ID);
		private AsyncFacebookRunner mAsyncRunner;
	
	*//**
	 * Function to login into facebook
	 * *//*
	public void loginToFacebook(final Activity activity) {

		mPrefs = activity.getPreferences(activity.MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(activity,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",facebook.getAccessToken());
							editor.putLong("access_expires",facebook.getAccessExpires());
							editor.commit();
							getProfileInformation(activity) ;
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}
	
	
	*//**
	 * Get Profile information by making request to Facebook Graph API
	 * *//*
	public void getProfileInformation(final Activity activity) {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					
					// getting name of the user
					final String name = profile.getString("name");
					
					// getting email of the user
					final String email = profile.getString("email");
					
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(activity.getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
						}

					});

					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}*/
	
}
