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
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class AddReviewActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{
	private int rating = 0;
	private TransparentProgressDialog progressdialog;
	private Button submitReview,cancelReview;
	private EditText addReview_comment,addReview_username;
	private ImageView addreview_rating1,addreview_rating2,addreview_rating3,addreview_rating4,addreview_rating5,addReviewHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addreview);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		submitReview =(Button)findViewById(R.id.submitReview);
		cancelReview =(Button)findViewById(R.id.cancelReview);
		addReview_comment =(EditText)findViewById(R.id.addReview_comment);
		addReview_username =(EditText)findViewById(R.id.addReview_username);
		addReviewHome =(ImageView)findViewById(R.id.addReviewHome);
		addreview_rating1 =(ImageView)findViewById(R.id.addreview_rating1);
		addreview_rating2 =(ImageView)findViewById(R.id.addreview_rating2);
		addreview_rating3 =(ImageView)findViewById(R.id.addreview_rating3);
		addreview_rating4 =(ImageView)findViewById(R.id.addreview_rating4);
		addreview_rating5 =(ImageView)findViewById(R.id.addreview_rating5);

		submitReview.setOnClickListener(this);
		cancelReview.setOnClickListener(this);
		addreview_rating1.setOnClickListener(this);
		addreview_rating2.setOnClickListener(this);
		addreview_rating3.setOnClickListener(this);
		addreview_rating4.setOnClickListener(this);
		addreview_rating5.setOnClickListener(this);
		addReviewHome.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v == submitReview)
		{
			String listingid = this.getIntent().getStringExtra("listingid");
			String UserID = new StoreData(AddReviewActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(UserID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
				String comment = addReview_comment.getText().toString();
				String name = addReview_username.getText().toString();
				if (comment.equals("") || addReview_comment.equals("")) 
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.enter_all_details),Toast.LENGTH_SHORT).show();
				}
				else
				{
					List<NameValuePair> request = new ArrayList<NameValuePair>(7);
					request.add(new BasicNameValuePair("userid", UserID));
					request.add(new BasicNameValuePair("listingid", listingid));
					request.add(new BasicNameValuePair("name", name));
					request.add(new BasicNameValuePair("ratings", ""+rating));
					request.add(new BasicNameValuePair("comment", comment));
					
					progressdialog.show();
					HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.ADDREVIEW_URL, request, AddReviewActivity.this);
					Thread td = new Thread(connect);
					td.start();
				}
			}
		}
		else if(v == addReviewHome)
		{
			Intent intent = new Intent(AddReviewActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		else if(v == cancelReview)
		{
			rating = 0;
			finish();
		}
		else if(v == addreview_rating1)
		{
			rating = 1;
			addreview_rating1.setImageResource(R.drawable.icon_star_active);
			addreview_rating2.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating3.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating4.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating5.setImageResource(R.drawable.icon_star_inactive);	
		}
		else if(v == addreview_rating2)
		{
			rating = 2;
			addreview_rating1.setImageResource(R.drawable.icon_star_active);
			addreview_rating2.setImageResource(R.drawable.icon_star_active);
			addreview_rating3.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating4.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating5.setImageResource(R.drawable.icon_star_inactive);	
		}
		else if(v == addreview_rating3)
		{
			rating = 3;
			addreview_rating1.setImageResource(R.drawable.icon_star_active);
			addreview_rating2.setImageResource(R.drawable.icon_star_active);
			addreview_rating3.setImageResource(R.drawable.icon_star_active);
			addreview_rating4.setImageResource(R.drawable.icon_star_inactive);
			addreview_rating5.setImageResource(R.drawable.icon_star_inactive);	
		}
		else if(v == addreview_rating4)
		{
			rating = 4;
			addreview_rating1.setImageResource(R.drawable.icon_star_active);
			addreview_rating2.setImageResource(R.drawable.icon_star_active);
			addreview_rating3.setImageResource(R.drawable.icon_star_active);
			addreview_rating4.setImageResource(R.drawable.icon_star_active);
			addreview_rating5.setImageResource(R.drawable.icon_star_inactive);	
		}
		else if(v == addreview_rating5)
		{
			rating = 5;
			addreview_rating1.setImageResource(R.drawable.icon_star_active);
			addreview_rating2.setImageResource(R.drawable.icon_star_active);
			addreview_rating3.setImageResource(R.drawable.icon_star_active);
			addreview_rating4.setImageResource(R.drawable.icon_star_active);
			addreview_rating5.setImageResource(R.drawable.icon_star_active);	
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
						Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
						if(resp.getString("status").equals("success"))
						{
							finish();
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