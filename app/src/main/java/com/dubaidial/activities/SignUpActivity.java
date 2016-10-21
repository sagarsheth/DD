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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class SignUpActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{	
	private TextView tc,pp;
	private EditText userName, email, mobile, password, confpaswd;
	private Button signup, cancel;
	private TransparentProgressDialog progressdialog;
	private ImageView signupHome;
	private CheckBox checkBox1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		signup = (Button) findViewById(R.id.signup);
		cancel = (Button) findViewById(R.id.cancel);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		
		tc = (TextView)findViewById(R.id.tc);
		pp = (TextView)findViewById(R.id.pp);
		
		signupHome = (ImageView) findViewById(R.id.signupHome);
		userName = (EditText) findViewById(R.id.username);
		email = (EditText) findViewById(R.id.emailid);
		mobile = (EditText) findViewById(R.id.mobileno);
		password = (EditText) findViewById(R.id.password);
		confpaswd = (EditText) findViewById(R.id.cpaswd);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);

		signup.setOnClickListener(this);
		cancel.setOnClickListener(this);
		signupHome.setOnClickListener(this);
		tc.setOnClickListener(this);
		pp.setOnClickListener(this);
		super.headerText.setText("SIGN UP");
		
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		super.headerText.setText("SIGN UP");
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == cancel) 
		{
			finish();
		}
		else if (v == tc) 
		{
	        new SimpleEula(this).show(); 
		}
		else if (v == pp) 
		{
	        new SimpleEula1(this).show(); 
		}
		else if (v == signupHome) 
		{
			Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
		else if (v == signup) 
		{
			if(checkBox1.isChecked())
			{
				String username = userName.getText().toString();
				String emailid = email.getText().toString();
				String mobileno = mobile.getText().toString();
				String password1 = password.getText().toString();
				String cpaswd = confpaswd.getText().toString();

				if (username.equals("") || emailid.equals("")|| mobileno.equals("") || password1.equals("")|| cpaswd.equals("")) 
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.enter_all_details),Toast.LENGTH_SHORT).show();
				}
				else if(!password1.equals(cpaswd))
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.invalid_pass),Toast.LENGTH_SHORT).show();
				}
				else
				{
					try
					{
//						JSONObject request = new JSONObject();
//						request.put("emailid", emailid);
//						request.put("password", password1);
//						request.put("fullname", username);
//						request.put("mobileno", mobileno);
						
						List<NameValuePair> request = new ArrayList<NameValuePair>(5);
						request.add(new BasicNameValuePair("emailid", emailid));
						request.add(new BasicNameValuePair("password", password1));
						request.add(new BasicNameValuePair("fullname", username));
						request.add(new BasicNameValuePair("mobileno", mobileno));
						
						progressdialog.show();
						HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.REGISTRATION_URL, request, SignUpActivity.this);
						Thread td = new Thread(connect);
						td.start();
						
						StoreData save = new StoreData(SignUpActivity.this);
						save.setSharedPrefrence(SaveddataModel.FILENAME, password1, SaveddataModel.Password_key);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
					}
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Please accept terms and conditions.",Toast.LENGTH_SHORT).show();
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
							StoreData save = new StoreData(SignUpActivity.this);
							save.setSharedPrefrence(SaveddataModel.FILENAME, "1", SaveddataModel.isGuest);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("id"), SaveddataModel.UserID_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("email_address"), SaveddataModel.Email_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("fullname"), SaveddataModel.Name_key);
							save.setSharedPrefrence(SaveddataModel.FILENAME, jarray.getJSONObject(0).getString("mobile_no"), SaveddataModel.Mobile_key);
							
							Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
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
}