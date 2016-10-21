package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.activities.adapters.ExpandableListAdapter;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class CitySelectActivity extends HeaderActivity implements HttpConnectionResponse
{
	private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private ImageView citySelectorHome;
//	HashMap<String, String> listDataChildwithID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
//		HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.LOCATION_URL, null, CitySelectActivity.this);
//		Thread td = new Thread(connect);
//		td.start();
		
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cityselect);
		
		citySelectorHome = (ImageView)findViewById(R.id.citySelectorHome);
		citySelectorHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(CitySelectActivity.this,MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
//		String cityJson = new StoreData(CitySelectActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.CityJson_key);
//		if(cityJson.equals(""))
//		{
//			Toast.makeText(getApplicationContext(),getResources().getString(R.string.fetch_cityname),Toast.LENGTH_SHORT).show();
//		}
//		else
//		prepareListData(cityJson);
		
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		prepareListData();

//		listDataChildwithID = new HashMap<String, String>();
		
//		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
//		expListView.setAdapter(listAdapter);
		
		expListView.setOnGroupClickListener(new OnGroupClickListener() 
		{
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) 
			{
				return false;
			}
		});
		
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() 
		{
			@Override
			public void onGroupExpand(int groupPosition)
			{
				CityUtilities.SelectedCity = " "+listDataHeader.get(groupPosition);
//				Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition) + " Expanded",	Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() 
		{
			@Override
			public void onGroupCollapse(int groupPosition) 
			{
						Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition) + " Collapsed",Toast.LENGTH_SHORT).show();
			}
		});
		
		expListView.setOnChildClickListener(new OnChildClickListener() 
		{
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id)
			{
				Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition)+ " : "+ listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
	
//	private void prepareListData(String jsonData)
	private void prepareListData()
	{
		try
		{
			// Adding child data
			listDataHeader.add(MainActivity.areaName);
			listDataHeader.add("Abu Dhabi");
			listDataHeader.add("Ajman");
			listDataHeader.add("Dubai");
			listDataHeader.add("Fujairah");
			listDataHeader.add("Sharjah");
			listDataHeader.add("Ras Al Khaimah");
			listDataHeader.add("Umm Al Quwain");
			
			List<String> AbuDhabi = new ArrayList<String>();
			List<String> CurrentLocation = new ArrayList<String>();
			List<String> Ajman = new ArrayList<String>();
			List<String> Dubai = new ArrayList<String>();
			List<String> Fujairah = new ArrayList<String>();
			List<String> Sharjah = new ArrayList<String>();
			List<String> RasAlKhaimah = new ArrayList<String>();
			List<String> UmmAlQuwain = new ArrayList<String>();
			
//			JSONArray jarray = new JSONObject(jsonData).getJSONArray("msg");
//			for(int i=0;i<jarray.length();i++)
//			{
//				String city = jarray.getJSONObject(i).getString("city");
//				String area = "";
//				if(city.equals("abu-dhabi"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					AbuDhabi.add(area);
//				}
//				else if(city.equals("dubai"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					Dubai.add(area);
//				}
//				else if(city.equals("sharjah"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					Sharjah.add(area);
//				}
//				else if(city.equals("Ajman"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					Ajman.add(area);
//				}
//				else if(city.equals("dubai"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					Dubai.add(area);
//				}
//				else if(city.equals("dubai"))
//				{
//					area = jarray.getJSONObject(i).getString("area_name");
//					Dubai.add(area);
//				}
//				listDataChildwithID.put(area, jarray.getJSONObject(i).getString("id"));
//			}

			listDataChild.put(listDataHeader.get(0), CurrentLocation); // Header, Child data
			listDataChild.put(listDataHeader.get(1), AbuDhabi); // Header, Child data
			listDataChild.put(listDataHeader.get(2), Ajman);
			listDataChild.put(listDataHeader.get(3), Dubai);		
			listDataChild.put(listDataHeader.get(4), Fujairah); // Header, Child data
			listDataChild.put(listDataHeader.get(5), Sharjah);
			listDataChild.put(listDataHeader.get(6), RasAlKhaimah);
			listDataChild.put(listDataHeader.get(7), UmmAlQuwain);

			listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
			expListView.setAdapter(listAdapter);
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
				if(status)
				{
					try
					{
						JSONObject resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							new StoreData(CitySelectActivity.this).setSharedPrefrence(SaveddataModel.FILENAME, response, SaveddataModel.CityJson_key);
//							prepareListData(response);
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