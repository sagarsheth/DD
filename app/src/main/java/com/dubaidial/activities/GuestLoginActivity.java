package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class GuestLoginActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{
	private Button login, cancel;
	private EditText userName, email, mobile;
	private TransparentProgressDialog progressdialog;
	private ImageView guestHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guestlogin);
		
		login = (Button) findViewById(R.id.guestlogin);
		cancel = (Button) findViewById(R.id.cancel);
		userName = (EditText) findViewById(R.id.username);
		email = (EditText) findViewById(R.id.emailid);
		mobile = (EditText) findViewById(R.id.mobileno);
		guestHome = (ImageView) findViewById(R.id.guestHome);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);

		login.setOnClickListener(this);
		guestHome.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		headerText.setText("GUEST LOGIN");
	}
	
	@Override
	public void onClick(View v) 
	{
		if (v == cancel) 
		{
			finish();
		}
		else if(v == guestHome)
		{
			Intent intent = new Intent(GuestLoginActivity.this,SignInActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		else if (v == login)
		{
			String username = userName.getText().toString();
			String emailid = email.getText().toString();
			String mobileno = mobile.getText().toString();
			if (username.equals("") || emailid.equals("")|| mobileno.equals("")) 
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.enter_all_details),Toast.LENGTH_SHORT).show();
			}
			else
			{
				try
				{
//					JSONObject request = new JSONObject();
//					request.put("fullname", username);
//					request.put("emailid", emailid);
//					request.put("mobileno", mobileno);
					
					List<NameValuePair> request = new ArrayList<NameValuePair>(6);
					request.add(new BasicNameValuePair("fullname", username));
					request.add(new BasicNameValuePair("emailid", emailid));
					request.add(new BasicNameValuePair("mobileno", mobileno));
					
					progressdialog.show();
					HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.LOGINGUEST_URL, request, GuestLoginActivity.this);
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
	public void onHttpServiceResponse(final boolean status, final String response)
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
							JSONArray jarray = resp.getJSONArray("details");
							StoreData save = new StoreData(GuestLoginActivity.this);
							save.setSharedPrefrence(SaveddataModel.FILENAME, "0", SaveddataModel.isGuest);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("id"), SaveddataModel.UserID_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("email_address"), SaveddataModel.Email_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("fullname"), SaveddataModel.Name_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("mobile_no"), SaveddataModel.Mobile_key);
					
							Intent intent = new Intent(GuestLoginActivity.this,MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
}