package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import com.techpro.dubaidial.R;

public class ForgotPasswordActivity extends HeaderActivity implements OnClickListener, HttpConnectionResponse 
{
	private EditText emailId;
	private Button resetPass, newuser_SignUp;
	private TransparentProgressDialog progressdialog;
	private ImageView fergotPassHome;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgotpassword);
		resetPass = (Button) findViewById(R.id.reset);
		newuser_SignUp = (Button) findViewById(R.id.newuser);
		emailId = (EditText) findViewById(R.id.username);
		fergotPassHome = (ImageView) findViewById(R.id.fergotPassHome);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
	
		resetPass.setOnClickListener(this);
		newuser_SignUp.setOnClickListener(this);
		fergotPassHome.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		headerText.setText("FORGOT PASSWORD");
	}
	
	@Override
	public void onClick(View v) 
	{
		if (v == newuser_SignUp) 
		{
			Intent intent = new Intent(ForgotPasswordActivity.this,SignUpActivity.class);
			startActivity(intent);
			finish();
		}
		else if (v == fergotPassHome) 
		{
			Intent intent = new Intent(ForgotPasswordActivity.this,SignInActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		else if (v == resetPass)
		{
			String username = emailId.getText().toString();
			if (username.equals("")) 
			{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_all_details), Toast.LENGTH_SHORT).show();
			}
			else
			{
				try
				{
//					JSONObject request = new JSONObject();
//					request.put("email", username);
					

					List<NameValuePair> request = new ArrayList<NameValuePair>(6);
					request.add(new BasicNameValuePair("email", username));

					HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.FORGOTPASS_URL, request, ForgotPasswordActivity.this);
					Thread td = new Thread(connect);
					td.start();
					progressdialog.show();
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
	public void onHttpServiceResponse( final boolean status, final String response)
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
							Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
//							Intent intent = new Intent(ForgotPasswordActivity.this,MainActivity.class);
//							startActivity(intent);
//							finish();
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