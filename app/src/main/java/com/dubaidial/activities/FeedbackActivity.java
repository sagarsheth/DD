package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
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
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class FeedbackActivity extends Activity implements OnClickListener,HttpConnectionResponse
{
	Button submit;
	EditText comment;
	ImageView feedbackHome;
	private TransparentProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		submit = (Button) findViewById(R.id.reset);
		comment = (EditText) findViewById(R.id.comment);
		feedbackHome = (ImageView)findViewById(R.id.feedbackHome);
		
		submit.setOnClickListener(this);
		feedbackHome.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (v == submit) 
		{
			try 
			{
				String comme = comment.getText().toString();
				if(comme.equals(""))
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.enter_all_details),Toast.LENGTH_SHORT).show();
				}
				else
				{
					StoreData save = new StoreData(FeedbackActivity.this);
					String userID = save.getSharedPreference(SaveddataModel.FILENAME,SaveddataModel.UserID_key);
					if(userID.equals(""))
					{
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
					}
					else
					{
	//					JSONObject request = new JSONObject();
	//					request.put("feedback", comme);
	//					request.put("userid", userID);	
	
						List<NameValuePair> request = new ArrayList<NameValuePair>(6);
						request.add(new BasicNameValuePair("feedback", comme));
						request.add(new BasicNameValuePair("userid", userID));
						
						progressdialog.show();
						HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.FEEDBACK_URL, request, FeedbackActivity.this);
						Thread td = new Thread(connect);
						td.start();
					}
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
			}
		}
		else if(v == feedbackHome)
		{
			Intent intent = new Intent(FeedbackActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}

	public void onHttpServiceResponse(final boolean status,final String response)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				progressdialog.dismiss();
				if (status)
				{
					try 
					{
						JSONObject resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							Toast.makeText(getApplicationContext(),"Thank You For Sending us Feed Back", Toast.LENGTH_SHORT).show();
						}
						else
						{
							Toast.makeText(getApplicationContext(),resp.getString("msg"), Toast.LENGTH_SHORT).show();
						}
						finish();
					}
					catch (Exception e) 
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