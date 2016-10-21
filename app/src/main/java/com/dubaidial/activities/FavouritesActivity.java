package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dubaidial.Utils.Config;
import com.dubaidial.activities.adapters.FavouriteAdapter;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.models.SearchResultModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class FavouritesActivity extends HeaderActivity implements HttpConnectionResponse
{
	private boolean isDeleteCalled = false;
	private ListView favouriteList;
	private TransparentProgressDialog progressdialog;
	private ImageView favouriteHome;
//	private ArrayList<FavouriteModel> favouriteresult;
	private ArrayList<SearchResultModel> searchresults;
	private AlertDialog.Builder builder;
	int position;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites);
		
		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to delete selected favourite?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
		
		favouriteList =(ListView)findViewById(R.id.favouriteList);
		favouriteList.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				FavouritesActivity.this.position = position;
				Intent intent = new Intent(FavouritesActivity.this,DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("resultdata", searchresults.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
//				builder.show();
			}
		});
		
		favouriteHome = (ImageView)findViewById(R.id.favouriteHome);
		favouriteHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(FavouritesActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		getFavourities();
	}
	private void getFavourities() 
	{
		try
		{
			String userID = new StoreData(FavouritesActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(userID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
				List<NameValuePair> request = new ArrayList<NameValuePair>(6);
				request.add(new BasicNameValuePair("userid", userID));
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.LISTFAVOURITE_URL, request, FavouritesActivity.this);
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
						if(isDeleteCalled)
						{
							progressdialog.dismiss();
							Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
							isDeleteCalled = false;
							getFavourities();
						}
						else
						{
							if(resp.getString("status").equals("success"))
							{
								
								searchresults = new ArrayList<SearchResultModel>();

								JSONArray jarray = resp.getJSONArray("msg");
								List<String> data = new ArrayList<String>();
								for(int i=0;i<jarray.length();i++)
								{
									SearchResultModel result = new SearchResultModel();
									data.add(jarray.getJSONObject(i).getString("name"));
									result.setId(jarray.getJSONObject(i).getString("id"));
									result.setName(jarray.getJSONObject(i).getString("name"));
									result.setContact_name(jarray.getJSONObject(i).getString("contact_name"));
									result.setAddress(jarray.getJSONObject(i).getString("address"));
									result.setArea(jarray.getJSONObject(i).getString("area"));
									result.setLandmark(jarray.getJSONObject(i).getString("landmark"));
									result.setContact_number(jarray.getJSONObject(i).getString("contact_number"));
									result.setFax_number(jarray.getJSONObject(i).getString("fax_number"));
									result.setPobox_no(jarray.getJSONObject(i).getString("pobox_no"));
									String sc =jarray.getJSONObject(i).getString("customer_service");
									if(sc==null)
										result.setCustomer_service("info@dubaidial.com");
									else if(sc.equals(""))
										result.setCustomer_service("info@dubaidial.com");
									else
										result.setCustomer_service(sc);
									result.setRegional_code(jarray.getJSONObject(i).getString("regional_code"));
									result.setMobile(jarray.getJSONObject(i).getString("mobile"));
									result.setCity(jarray.getJSONObject(i).getString("city"));
									result.setEmail_address(jarray.getJSONObject(i).getString("email_address"));
									result.setWebsite(jarray.getJSONObject(i).getString("website"));
									result.setDescription(jarray.getJSONObject(i).getString("description"));
									result.setCompany_logo(jarray.getJSONObject(i).getString("company_logo"));
									result.setProfile(jarray.getJSONObject(i).getString("profile"));
									result.setBrands(jarray.getJSONObject(i).getString("brands"));
									result.setPackage_type(jarray.getJSONObject(i).getString("package_type"));
									result.setLat(jarray.getJSONObject(i).getString("lat"));
									result.setLon(jarray.getJSONObject(i).getString("lon"));
									result.setStatus(jarray.getJSONObject(i).getString("status"));
									result.setCreatedate(jarray.getJSONObject(i).getString("createdate"));
									result.setAverage_ratings(jarray.getJSONObject(i).getString("average_ratings"));
									result.setTestimonials(jarray.getJSONObject(i).getString("testimonials"));
									result.setPackage_type(jarray.getJSONObject(i).getString("package_type"));
									result.setDistance(jarray.getJSONObject(i).getString("distance"));
									result.setOther_url(jarray.getJSONObject(i).getString("other_url"));
	//								result.setOther_url("http:\\\\www.google.com");
									result.setUser_testimonial(jarray.getJSONObject(i).getString("user_testimonial"));

									result.setWorking_hour(jarray.getJSONObject(i).getString("working_hour"));
									result.setWeekday_from(jarray.getJSONObject(i).getString("weekday_from"));
									result.setWeekday_to(jarray.getJSONObject(i).getString("weekday_to"));
									result.setWeekend_from(jarray.getJSONObject(i).getString("weekend_from"));
									result.setWeekend_to(jarray.getJSONObject(i).getString("weekend_to"));
									result.setUser_favourite(jarray.getJSONObject(i).getString("user_favourite"));
									searchresults.add(result);
								}
								favouriteList.setAdapter(new FavouriteAdapter(getApplicationContext(), R.layout.adapter_searchresult,data, searchresults));
								progressdialog.dismiss();
								
								
								
								
								
//								favouriteresult = new ArrayList<FavouriteModel>();
//								
//								List<String> data = new ArrayList<String>();
//								JSONArray jarray = resp.getJSONArray("msg");
//								for(int i=0;i<jarray.length();i++)
//								{
//									FavouriteModel result = new FavouriteModel();
//									data.add(jarray.getJSONObject(i).getString("name"));
//									result.setId(jarray.getJSONObject(i).getString("id"));
//									result.setName(jarray.getJSONObject(i).getString("name"));
//									result.setFav_id(jarray.getJSONObject(i).getString("fav_id"));
//									result.setAddress(jarray.getJSONObject(i).getString("address"));
//									result.setUser_id(jarray.getJSONObject(i).getString("user_id"));
//									result.setCompany_logo(jarray.getJSONObject(i).getString("company_logo"));
//									favouriteresult.add(result);
//								}
//								favouriteList.setAdapter(new FavouriteAdapter(getApplicationContext(), R.layout.adapter_searchresult,data, favouriteresult));
//								progressdialog.dismiss();
							}
							else
							{
								progressdialog.dismiss();
								Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
							}
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
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
	{
	    @Override
	    public void onClick(DialogInterface dialog, int which) 
	    {
	        switch (which)
	        {
		        case DialogInterface.BUTTON_POSITIVE:
					try
					{
						String userID = new StoreData(FavouritesActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
						if(userID.equals(""))
						{
							Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
						}
						else
						{
							Intent intent = new Intent(FavouritesActivity.this,DetailActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("resultdata", searchresults.get(position));
							intent.putExtras(bundle);
							startActivity(intent);
//							List<NameValuePair> request = new ArrayList<NameValuePair>(6);
//							request.add(new BasicNameValuePair("userid", userID));
//							request.add(new BasicNameValuePair("listingid", favouriteresult.get(position).getId()));
//							
//							isDeleteCalled = true;
//							progressdialog.show();
//							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.DELETEFAVOURITE_URL, request, FavouritesActivity.this);
//							Thread td = new Thread(connect);
//							td.start();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
					}
		            break;
	
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
	        }
	    }
	};
}