package com.dubaidial.activities.controllers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.controllers.LinkedinDialog.OnVerifyListener;
import com.dubaidial.communication.HttpConnection;
import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Location;
import com.google.code.linkedinapi.schema.Person;

public class LinkedInController {
	
	String name ,email,gender;
	public static final String OAUTH_CALLBACK_HOST = "litestcalback";

	final LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
            .getInstance().createLinkedInOAuthService(
                    Config.LINKEDIN_CONSUMER_KEY,Config.LINKEDIN_CONSUMER_SECRET/*, Config.scopeParams*/);
	final LinkedInApiClientFactory factory = LinkedInApiClientFactory
			.newInstance(Config.LINKEDIN_CONSUMER_KEY,
					Config.LINKEDIN_CONSUMER_SECRET);
	LinkedInRequestToken liToken;
	LinkedInApiClient client;
	LinkedInAccessToken accessToken = null;
	
	public LinkedInController(Activity activity){
		linkedInLogin(activity);
	}
	
	private void linkedInLogin(final Activity activity) {
		ProgressDialog progressDialog = new ProgressDialog(activity);

		LinkedinDialog d = new LinkedinDialog(activity, progressDialog);
		d.show();

		// set call back listener to get oauth_verifier value
		d.setVerifierListener(new OnVerifyListener() {
			@Override
			public void onVerify(String verifier) {
				try {
					Log.i("LinkedinSample", "verifier: " + verifier);

					accessToken = LinkedinDialog.oAuthService
							.getOAuthAccessToken(LinkedinDialog.liToken,
									verifier);
					LinkedinDialog.factory.createLinkedInApiClient(accessToken);
					client = factory.createLinkedInApiClient(accessToken);
					// client.postNetworkUpdate("Testing by Mukesh!!! LinkedIn wall post from Android app");
					Log.i("LinkedinSample", "ln_access_token: " + accessToken.getToken());
					Log.i("LinkedinSample", "ln_access_token: " + accessToken.getTokenSecret());
					//Person p = client.getProfileForCurrentUser();


					Person profile = client.getProfileForCurrentUser(EnumSet.of(
			                ProfileField.ID, ProfileField.FIRST_NAME,
			                ProfileField.LAST_NAME, ProfileField.HEADLINE,
			                ProfileField.INDUSTRY, ProfileField.PICTURE_URL,
			                ProfileField.DATE_OF_BIRTH, ProfileField.LOCATION_NAME,
			                ProfileField.MAIN_ADDRESS, ProfileField.LOCATION_COUNTRY,
			                ProfileField.PUBLICATIONS_URL,
			                ProfileField.EMAIL_ADDRESS));
					
					
					name = profile.getFirstName() + " "
			                + profile.getLastName();
					email = profile.getEmailAddress();
					
					System.out.println("PersonID : " + profile.getId());
			        System.out.println("Name : " + profile.getFirstName() + " "
			                + profile.getLastName());
			        System.out.println("Headline : " + profile.getHeadline());
			        System.out.println("Industry : " + profile.getIndustry());
			        System.out.println("Picture : " + profile.getPictureUrl());
			        System.out.println("Email : " + profile.getEmailAddress());
			       
			        System.out.println("MAin Address : " + profile.getMainAddress());
			        Location location = profile.getLocation();
			        System.out.println("Location:" + location.getName() + " - "
			                + location.getCountry().getCode());
					
					//System.out.println("Welcome " + p.getFirstName() + " " + p.getLastName() + p.getId() +p.getMainAddress());
			        activity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							List<NameValuePair> request = new ArrayList<NameValuePair>(7);
							request.add(new BasicNameValuePair("emailid", email));
							request.add(new BasicNameValuePair("fullname", name));
							request.add(new BasicNameValuePair("mobileno", ""));
							request.add(new BasicNameValuePair("gender", ""));
							request.add(new BasicNameValuePair("platform ", "Lineked In"));
							request.add(new BasicNameValuePair("os", Config.PLATFORM));
//							
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SOCIALMEDIA, request, activity);
							Thread td = new Thread(connect);
							td.start();
							// TODO Auto-generated method stub
//							 TextView welcome = (TextView) activity.findViewById(R.id.welcome);
//		                        welcome.setText("Hello " + user.getName() + "!");
							
//							Log.e("fffffffffffffffffffff", user.getUsername());
//							Log.e("fffffffffffffffffffff", user.getName());
						}
					});

				} catch (Exception e) {
					Log.i("LinkedinSample", "error to get verifier");
					e.printStackTrace();
				}
			}
		});

		// set progress dialog
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}
}
