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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.adapters.ReviewAdapter;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.ReviewDataModel;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class ReviewActivity extends HeaderActivity implements HttpConnectionResponse
{
	private boolean isReviewAvailable = false;
	private ArrayList<ReviewDataModel> reviewlist; 
	private TextView poiName,headerReviewPoiName;
	private ListView reviewList;
	private Button addReview;
	private TransparentProgressDialog progressdialog;
	private ImageView reviewRating1,reviewRating2,reviewRating3,reviewRating4,reviewRating5,shareReviewHeader,backShareHeader;;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		poiName =(TextView)findViewById(R.id.poiname);
		headerReviewPoiName =(TextView)findViewById(R.id.headerReviewPoiName);
		reviewList =(ListView)findViewById(R.id.addreviewlist);
		
		reviewRating1 = (ImageView) findViewById(R.id.review_rating1);
	    reviewRating2 = (ImageView) findViewById(R.id.review_rating2);
	    reviewRating3 = (ImageView) findViewById(R.id.review_rating3);
	    reviewRating4 = (ImageView) findViewById(R.id.review_rating4);
	    reviewRating5 = (ImageView) findViewById(R.id.review_rating5);
	    
	    addReview =  (Button)findViewById(R.id.addreview);
		addReview.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(MainActivity.isGuestUser)
				{
					Toast.makeText(getApplicationContext(),"Guest User Cant Add Reviews Please SignUp.",Toast.LENGTH_SHORT).show();
				}
				else if(isReviewAvailable)
				{
					Toast.makeText(getApplicationContext(),"You already added Review To This Business.",Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent = new Intent(ReviewActivity.this,AddReviewActivity.class);
					intent.putExtra("listingid",getIntent().getStringExtra("listingid"));
					startActivity(intent);
				}
			}
		});

		shareReviewHeader = (ImageView) findViewById(R.id.shareReviewHeader);
		shareReviewHeader.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
			}
		});
		
		backShareHeader = (ImageView) findViewById(R.id.backShareHeader);
		backShareHeader.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		getReviews();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		getReviews();
}
	@Override
	public void onHttpServiceResponse(final boolean status, final String response) 
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				if(status)
				{
					try
					{
						JSONObject resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							List<String> data = new ArrayList<String>();
							JSONArray jarray = resp.getJSONArray("msg");
							for(int i=0;i<jarray.length();i++)
							{
								ReviewDataModel result = new ReviewDataModel();
								data.add(jarray.getJSONObject(i).getString("name"));
								result.setId(jarray.getJSONObject(i).getString("id"));
								result.setUserId(jarray.getJSONObject(i).getString("user_id"));
								result.setName(jarray.getJSONObject(i).getString("name"));
								result.setRatings(jarray.getJSONObject(i).getString("ratings"));
								result.setCreateDate(jarray.getJSONObject(i).getString("create_date"));
								result.setUserComment(jarray.getJSONObject(i).getString("comment"));
								result.setListingid(jarray.getJSONObject(i).getString("listingid"));
								reviewlist.add(result);
							}
							reviewList.setAdapter(new ReviewAdapter(getApplicationContext(), R.layout.adapter_searchresult,data, reviewlist));
							progressdialog.dismiss();
						}
						else
						{
							progressdialog.dismiss();
							Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e)
					{
						progressdialog.dismiss();
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					progressdialog.dismiss();
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void getReviews() 
	{
		try
		{
			reviewlist = new ArrayList<ReviewDataModel>();
			String listingid = this.getIntent().getStringExtra("listingid");
			String name = this.getIntent().getStringExtra("name");
			String rate = this.getIntent().getStringExtra("ratings");
			String test = this.getIntent().getStringExtra("test");
			if(test.equals("1"))
				isReviewAvailable = true;
			else
				isReviewAvailable = false;
			poiName.setText(name);
			headerReviewPoiName.setText(name);
	        try
	        {
		        if(rate != null)
		        {
//			        	int rateing = Integer.parseInt(rate);
		        	if(rate.startsWith("1"))
		        	{
		        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
		        	}
		        	else if(rate.startsWith("2"))
		        	{
		        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);	        		
		        	}
		        	else if(rate.startsWith("3"))
		        	{
		        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);	        		
		        	}
		        	else if(rate.startsWith("4"))
		        	{
		        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating4.setImageResource(R.drawable.icon_star_small_active);	        		
		        	}
		        	else if(rate.startsWith("5"))
		        	{
		        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating4.setImageResource(R.drawable.icon_star_small_active);
		        		reviewRating5.setImageResource(R.drawable.icon_star_small_active);	        		
		        	}
		        }
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
			String UserID = new StoreData(ReviewActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(UserID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{	
				List<NameValuePair> request = new ArrayList<NameValuePair>(3);
				request.add(new BasicNameValuePair("userid", UserID));
				request.add(new BasicNameValuePair("listingid", listingid));
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.LISTREVIEW_URL, request, ReviewActivity.this);
				Thread td = new Thread(connect);
				td.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
		}
	}
}