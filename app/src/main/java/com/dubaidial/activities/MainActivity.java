package com.dubaidial.activities;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.Utils.Config;
import com.dubaidial.Utils.Constants;
import com.dubaidial.activities.controllers.MenuController;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.location.GeoLocation;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

import tp.com.gpslib.gettersetter.GpsData;
import tp.com.gpslib.handler.GpsConfig;
import tp.com.gpslib.interfaces.NewLocation;

public class MainActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse,NewLocation
{
	public static String ImeiList = "";
	public static String CurrentImei = "";
	public static final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	public static final int MIN_TIME_BW_UPDATES = 10000 * 5 * 1;
	public static boolean isGuestUser = false;
	public static boolean isFinish = false;
	public static String SearchTerm = "";
	public static String latitude = "0.00";
	public static String longitude = "0.00";
	public static String areaName = "";
	private AlertDialog.Builder builder;
	private ImageView feedback,header_signout;
	private Button searchIcon;
	private TextView selectCity,usersOnline,shareapp,favorites,addbusinesslisting,blog;
	private AutoCompleteTextView autoSearch;
	public boolean isItemSelected = false;
	private ArrayList<String> searchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Intent in = new Intent(MainActivity.this,GeoLocation.class);
		startService(in);
		ImeiList = "356446057543501,359299054506253,358021058755152,352116063781643,352117063781641,911330050037703,911330050037711,351710050127843,355136059903339";
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		CurrentImei =  mngr.getDeviceId();
		if(ImeiList.contains(CurrentImei))
		{
		
		new MenuController(this);
		autoSearch = (AutoCompleteTextView)findViewById(R.id.main_autosearch);
		autoSearch.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Log.e("---------------->", "item clicekddd ");
				isItemSelected = true;

				SearchTerm = searchResult.get(position);
				if(SearchTerm.equals(""))
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
					intent.putExtra("searchTerm", SearchTerm);
					intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
					startActivity(intent);
				}
			
			}
		});

		TextWatcher textWatcher = new TextWatcher() 
		{
			@Override
			public void afterTextChanged(Editable s){}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(final CharSequence s, int start, int before, int count) 
			{
				try 
				{
					if(isItemSelected)
						isItemSelected = false;
					else
					{
						if (s.length()>3)
						{
							Log.e("--------->", "----------> "+s);
							List<NameValuePair> request = new ArrayList<NameValuePair>(3);
							request.add(new BasicNameValuePair("q", s.toString()));
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.AUTOSEARCH, request, MainActivity.this);
							Thread td = new Thread(connect);
							td.start();
						}
						else
						{
							autoSearch.dismissDropDown();
						}
					}
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				} 
			}
		};   

		autoSearch.addTextChangedListener(textWatcher);
//		feedback = (ImageView)findViewById(R.id.feedback);
//		header_signout = (ImageView)findViewById(R.id.header_signout);
		addbusinesslisting = (TextView)findViewById(R.id.addbusinesslisting);
		favorites = (TextView)findViewById(R.id.favorites);
		blog = (TextView)findViewById(R.id.blog);
		shareapp = (TextView)findViewById(R.id.shareapp);
		selectCity = (TextView)findViewById(R.id.selectcity);
		usersOnline = (TextView)findViewById(R.id.usersonline);
		searchIcon = (Button)findViewById(R.id.main_searchicon);
		
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to exit Dubai Dial application?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
		
//		feedback.setOnClickListener(this);
		searchIcon.setOnClickListener(this);
		shareapp.setOnClickListener(this);
		favorites.setOnClickListener(this);
		selectCity.setOnClickListener(this);
		addbusinesslisting.setOnClickListener(this);
		usersOnline.setOnClickListener(this);
		blog.setOnClickListener(this);
//		header_signout.setOnClickListener(this);
		String resp = new StoreData(MainActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.isGuest);
		if(resp.equals("1"))
			isGuestUser = false;
		else
			isGuestUser = true;
		GpsConfig g = new GpsConfig(this);
		g.setTimeInterval(MIN_TIME_BW_UPDATES);
		g.setMinDistForUpdate(MIN_DISTANCE_CHANGE_FOR_UPDATES);
//		g.setAccuracy(10);
		g.begin();
		
		areaName = "My Location";
		CityUtilities.SelectedCity = areaName;
		selectCity.setText(" "+areaName);
		}
		else
		{
			finish();
		}
//		downloadFile("http://iwabsolutions.com/best-dj-4.jpg");
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		if(Constants.backIMG == null)
		{
			downloadFile("http://iwabsolutions.com/best-dj-4.jpg");
		}
		if(Constants.backIMG_realestate == null)
		{
			downloadFile1("http://www.dubaidial.com/Banner/realestate_Android.jpg");
		}
		if(Constants.backIMG_Boutiques == null)
		{
			downloadFile2("http://www.dubaidial.com/Banner/Boutiques_Android.jpg");
		}
		if(isFinish)
		{
			isFinish = false;
			finish();
		}
		else
		{
			autoSearch.dismissDropDown();
			autoSearch.setAdapter(null);
			selectCity.setText(" "+CityUtilities.SelectedCity);
	//		autoSearch.setText(SearchTerm);
			HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.ONLINEUSER_URL, null, MainActivity.this);
			Thread td = new Thread(connect);
			td.start();
		}
	}
	
	@Override
	public void onClick(View v)
	{
		if(v == feedback)
		{
			Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
			startActivity(intent);
		}
		else if(v == addbusinesslisting)
		{
			if(isGuestUser)
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(MainActivity.this,ListYourBusinessActivity.class);
				startActivity(intent);
			}
		}
		else if(v == favorites)
		{
			Intent intent = new Intent(MainActivity.this,FavouritesActivity.class);
			startActivity(intent);
		}
		else if(v == shareapp)
		{
		    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
	        sharingIntent.setType("text/plain");
	        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DubaiDial Android");
	        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "www.dubaidial.com");
	        startActivity(Intent.createChooser(sharingIntent, "Share With"));
		}
		else if(v == selectCity)
		{
			Intent intent = new Intent(MainActivity.this,CitySelectActivity.class);
			startActivity(intent);
		}
		else if(v == header_signout)
		{
			builder.show();
		}
		else if(v == blog)
		{
			try{
				Intent intent = new Intent(MainActivity.this,MoreInfoWebViewActivity.class);
				intent.putExtra("additionalinfo", "http://www.dubaidial.com/blog");
				startActivity(intent);
				}catch(Exception e)
				{

					Toast.makeText(getApplicationContext(),"More info not available.",Toast.LENGTH_SHORT).show();
				}
		}
		else if(v == searchIcon)
		{
			SearchTerm = autoSearch.getText().toString();
			if(SearchTerm.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
				intent.putExtra("searchTerm", SearchTerm);
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				startActivity(intent);
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
				if(status)
				{
					try
					{
						JSONObject resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							JSONArray jarray = resp.getJSONArray("msg");
							String respo = jarray.toString();
							if(respo.contains("keyword"))
							{
								searchResult = new ArrayList<String>();
								searchResult.clear();
								for(int i=0;i<jarray.length();i++)
								{
									searchResult.add(jarray.getJSONObject(i).getString("keyword"));
								}
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,searchResult);
								if(isItemSelected)
								{
									isItemSelected = false;
									autoSearch.setAdapter(null);
									adapter.notifyDataSetChanged();
									autoSearch.dismissDropDown();
								}
								else
								{
									autoSearch.setAdapter(adapter);
									adapter.notifyDataSetChanged();
									autoSearch.showDropDown();
								}
							}
							else
							{
								String onlineUsers = jarray.getJSONObject(0).getString("onlineusers");
								usersOnline.setText(" "+onlineUsers);
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
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
					new StoreData(MainActivity.this).setSharedPrefrence(SaveddataModel.FILENAME, "", SaveddataModel.UserID_key);
					finish(); 
		            break;
	
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
	        }
	    }
	};

	@Override
	public void onBackPressed() 
	{
		try
		{
			Intent intent = new Intent(MainActivity.this,FullScreenImage.class);
//			intent.putExtra("additionalinfo", "http://iwabsolutions.com/best-dj-4.jpg");
			startActivity(intent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finish();
	};
	
	@Override
	public void updateLocation(GpsData gpsdata)
	{
		System.out.println("----->> "+" "+ gpsdata.getLatitude() +" "+gpsdata.getLongitude());
		latitude = ""+gpsdata.getLatitude();
		longitude = ""+gpsdata.getLongitude();
		Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
		List<Address> addresses;
		try 
		{
			addresses = gcd.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude), 1);
			areaName = addresses.get(0).getLocality();
			CityUtilities.SelectedCity = areaName;
			selectCity.setText(" "+areaName);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			areaName = "My Location";
			CityUtilities.SelectedCity = areaName;
			selectCity.setText(" "+areaName);
		}
	}

	@Override
	public void updateLocationFailed(Object o) {

	}

	void downloadFile(final String fileUrl)
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run()
			{
				 URL myFileUrl = null;
			     try 
			     {
			    	 myFileUrl = new URL(fileUrl.trim());
			         HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			         conn.setDoInput(true);
			         conn.connect();
			         InputStream is = conn.getInputStream();
			         Constants.backIMG = BitmapFactory.decodeStream(is);
			        
			      } 
			     catch (Exception e)
			     {
			         e.printStackTrace();
			     }
			}
		}).start();
	 }
	void downloadFile1(final String fileUrl)
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run()
			{
				 URL myFileUrl = null;
			     try 
			     {
			    	 myFileUrl = new URL(fileUrl.trim());
			         HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			         conn.setDoInput(true);
			         conn.connect();
			         InputStream is = conn.getInputStream();
			         Constants.backIMG_realestate = BitmapFactory.decodeStream(is);
			        
			      } 
			     catch (Exception e)
			     {
			         e.printStackTrace();
			     }
			}
		}).start();
	 }
	void downloadFile2(final String fileUrl)
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run()
			{
				 URL myFileUrl = null;
			     try 
			     {
			    	 myFileUrl = new URL(fileUrl.trim());
			         HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			         conn.setDoInput(true);
			         conn.connect();
			         InputStream is = conn.getInputStream();
			         Constants.backIMG_Boutiques = BitmapFactory.decodeStream(is);
			        
			      } 
			     catch (Exception e)
			     {
			         e.printStackTrace();
			     }
			}
		}).start();
	 }
}