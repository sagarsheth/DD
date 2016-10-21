package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.controllers.FbController;
import com.dubaidial.activities.controllers.LinkedInController;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.facebook.Session;
import com.techpro.dubaidial.R;

@SuppressLint("NewApi")
public class SignInActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{
	String get_id, get_name, get_gender, get_email, get_birthday, get_locale, get_location;

	private Button guestLogin,login,signUp;
	private EditText userName,passWord;
	private TransparentProgressDialog progressdialog;
	private TextView forgotPassword;
//	private LinearLayout signUp;
	String username,password;
	private ImageView fblogin,twitterlogin,linkedinlogin;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		try{
			 if (android.os.Build.VERSION.SDK_INT > 9) {
			        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
			        StrictMode.setThreadPolicy(policy);
			        }
			
		}catch(Exception excep){
			excep.printStackTrace();
		}
		
		login = (Button)findViewById(R.id.Login);
		signUp = (Button)findViewById(R.id.signup);
		guestLogin = (Button)findViewById(R.id.guestlogin);
		forgotPassword = (TextView)findViewById(R.id.forgotpass);
		userName = (EditText)findViewById(R.id.username);
		passWord = (EditText)findViewById(R.id.password);
		fblogin = (ImageView)findViewById(R.id.fblogin);
		twitterlogin = (ImageView)findViewById(R.id.twitterlogin);
		linkedinlogin = (ImageView)findViewById(R.id.linkedinlogin);
		
		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		login.setOnClickListener(this);
		signUp.setOnClickListener(this);
		guestLogin.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		fblogin.setOnClickListener(this);
		twitterlogin.setOnClickListener(this);
		linkedinlogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		if(v == guestLogin)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "2", SaveddataModel.isSocial);
			Intent intent = new Intent(SignInActivity.this,GuestLoginActivity.class);
			startActivity(intent);
		}
		else if(v == fblogin)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "2", SaveddataModel.isSocial);
			progressdialog.show();
			new FbController(SignInActivity.this);
		}
		else if(v == twitterlogin)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "2", SaveddataModel.isSocial);
			
		}
		else if(v == linkedinlogin)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "2", SaveddataModel.isSocial);
			progressdialog.show();
			new LinkedInController(SignInActivity.this);
		}
		else if(v == forgotPassword)
		{
			Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
			startActivity(intent);
		}
		else if(v == signUp)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "1", SaveddataModel.isSocial);
			Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
			startActivity(intent);
		}
		else if(v == login)
		{
			StoreData save = new StoreData(SignInActivity.this);
			save.setSharedPrefrence(SaveddataModel.FILENAME, "1", SaveddataModel.isSocial);
//			Intent intent = new Intent(SignInActivity.this,MainActivity.class);
//			startActivity(intent);
			username = userName.getText().toString();
			password = passWord.getText().toString();
			if (username.equals("") || password.equals("")) 
			{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.enter_all_details),Toast.LENGTH_SHORT).show();
			}
			else
			{
				try
				{
					List<NameValuePair> request = new ArrayList<NameValuePair>(5);
					request.add(new BasicNameValuePair("email", username));
					request.add(new BasicNameValuePair("pwd", password));
					
//					JSONObject request = new JSONObject();
//					request.put("email", username);
//					request.put("pwd", password);
					
					progressdialog.show();
					HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SIGNIN_URL, request, SignInActivity.this);
					Thread td = new Thread(connect);
					td.start();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
		
		@Override
		public void onHttpServiceResponse(final boolean status,final String response)
		{
			runOnUiThread(new Runnable()
			{	
				@Override
				public void run() 
				{
					progressdialog.dismiss();
					if(status)
					{
						try
						{
							JSONObject resp = new JSONObject(response);
							
							if(resp.getString("status").equals("success"))
							{
								JSONArray jarray;
								if(resp.toString().contains("details"))
									jarray = resp.getJSONArray("details");
								else
									jarray = resp.getJSONArray("msg");
								
								StoreData save = new StoreData(SignInActivity.this);
								save.setSharedPrefrence(SaveddataModel.FILENAME, "1", SaveddataModel.isGuest);
								save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("email_address"), SaveddataModel.Email_key);
								save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("id"), SaveddataModel.UserID_key);
								save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("fullname"), SaveddataModel.Name_key);
								save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("mobile_no"), SaveddataModel.Mobile_key);
								save.setSharedPrefrence(SaveddataModel.FILENAME, username, SaveddataModel.Email_key);
								save.setSharedPrefrence(SaveddataModel.FILENAME, password, SaveddataModel.Password_key);
								Intent intent = new Intent(SignInActivity.this,MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
		 @Override
		 public void onActivityResult(int requestCode, int resultCode, Intent data)
		 {
		     super.onActivityResult(requestCode, resultCode, data);
		     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		 }
}