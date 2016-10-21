package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.Utils.Config;
import com.dubaidial.activities.adapters.ResultAdapter;
import com.dubaidial.activities.adapters.SubCategoryAdapter;
import com.dubaidial.activities.header.HeaderActivity;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.models.SearchResultModel;
import com.dubaidial.models.SubCategory;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class SearchResultActivity1 extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{
	public static int position = 0;
	private ArrayList<SearchResultModel> searchresults;
	private ArrayList<SubCategory> subCategory; 
	private ArrayList<String> subCategoryName;  
	private String categoryID = "",cityID = "",searchTerm = "";
	private TransparentProgressDialog progressdialog;
	private ListView resultLists,subCatList;
	private Button searchIcon;
	private EditText autoSearch;
	private TextView searchName,searchResultHome,dataCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchresult1);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		searchIcon = (Button)findViewById(R.id.search_searchicon);
//		autoSearch = (EditText)findViewById(R.id.search_autosearch);
		searchResultHome = (TextView)findViewById(R.id.selectcity1);
		searchName = (TextView)findViewById(R.id.searchName);
		dataCount = (TextView)findViewById(R.id.dataCount);
		
		searchResultHome.setOnClickListener(this);
		searchIcon.setOnClickListener(this);
		
		resultLists = (ListView)findViewById(R.id.resultList);	
		resultLists.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				Log.e("onItemClick", "onItemClick ----> "+position);
				Intent intent = new Intent(SearchResultActivity1.this,DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("resultdata", searchresults.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		subCatList = (ListView)findViewById(R.id.subCatList);	
		subCatList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				try
				{
					SearchResultActivity1.position = position;
					Log.e("subCatList", "subCatList onItemClick ----> "+position);
					String data = subCategory.get(position).getId();
					if(data.equals("0"))
						data = "";
					
						getSearchResultsByID(data);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		categoryID = this.getIntent().getStringExtra("category");
		searchTerm = this.getIntent().getStringExtra("searchTerm");
		cityID = this.getIntent().getStringExtra("cityID");
		if(searchTerm == null)
		{
			searchTerm = "";
			getSearchResultsByID("");
		}
		else
			getSearchResultsBySearch(searchTerm);
			
			autoSearch.setText(searchTerm);
	}
	
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		if(searchTerm.equals(""))
//			dataCount.setText(" Total "+searchresults.size()+" Results Found in "+CityUtilities.SelectedCity);
//		else
//			dataCount.setText(" Total "+searchresults.size()+" "+searchTerm+" Found in "+CityUtilities.SelectedCity);
//		searchResultHome.setText(CityUtilities.SelectedCity);
//	}
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
							subCategory = new ArrayList<SubCategory>();
							subCategoryName = new ArrayList<String>();
							searchresults = new ArrayList<SearchResultModel>();
							
							List<String> data = new ArrayList<String>();
							JSONArray jarray = resp.getJSONArray("msg");
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
//								result.setCompany_logo(jarray.getJSONObject(i).getString("company_logo"));
								result.setProfile(jarray.getJSONObject(i).getString("profile"));
								result.setBrands(jarray.getJSONObject(i).getString("brands"));
								result.setPackage_type(jarray.getJSONObject(i).getString("package_type"));
								result.setLat(jarray.getJSONObject(i).getString("lat"));
								result.setLon(jarray.getJSONObject(i).getString("lon"));
								result.setStatus(jarray.getJSONObject(i).getString("status"));
								result.setCreatedate(jarray.getJSONObject(i).getString("createdate"));
								result.setAverage_ratings(jarray.getJSONObject(i).getString("average_ratings"));
								result.setTestimonials(jarray.getJSONObject(i).getString("testimonials"));
								result.setDistance(jarray.getJSONObject(i).getString("distance"));
								result.setOther_url(jarray.getJSONObject(i).getString("other_url"));
								result.setUser_testimonial(jarray.getJSONObject(i).getString("user_testimonial"));
								result.setUser_favourite(jarray.getJSONObject(i).getString("user_favourite"));
								searchresults.add(result);
							}
							resultLists.setAdapter(new ResultAdapter(getApplicationContext(), R.layout.adapter_searchresult,data, searchresults));
							if(categoryID != null)
							{
								JSONArray jarray1 = resp.getJSONArray("subcat_list");
								SubCategory result1 = new SubCategory();
								result1.setId("0");
								result1.setName("All");
								subCategory.add(result1);
								subCategoryName.add(result1.getName());
								
								for(int i=0;i<jarray1.length();i++)
								{
									SubCategory result = new SubCategory();
									result.setId(jarray1.getJSONObject(i).getString("keyword_id"));
									result.setName(jarray1.getJSONObject(i).getString("keyword_name"));
									subCategory.add(result);
									subCategoryName.add(result.getName());
								}
								subCatList.setAdapter(new SubCategoryAdapter(getApplicationContext(), R.layout.subcat_list, subCategoryName, subCategory));
							}
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
	
	private void getSearchResultsByID(String subcat) 
	{
		try
		{
			String userID = new StoreData(SearchResultActivity1.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(userID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
	//			JSONObject request = new JSONObject();
	//			request.put("q", category);
	//			request.put("lat", "");
	//			request.put("lon", "");
	//			request.put("offset", "");
	//			request.put("cityid", cityID);

				List<NameValuePair> request = new ArrayList<NameValuePair>(6);
				if(cityID.contains(","))
				{
					request.add(new BasicNameValuePair("categoryid", categoryID));
					request.add(new BasicNameValuePair("lat", cityID.split(",")[0]));
					request.add(new BasicNameValuePair("lon", cityID.split(",")[1]));
					request.add(new BasicNameValuePair("q", ""));
					request.add(new BasicNameValuePair("subcategoryid", subcat));
					request.add(new BasicNameValuePair("userid", userID));
					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", ""));
				}
				else
				{
					request.add(new BasicNameValuePair("categoryid", categoryID));
					request.add(new BasicNameValuePair("lat", "0.00"));
					request.add(new BasicNameValuePair("lon", "0.00"));
					request.add(new BasicNameValuePair("q", ""));
					request.add(new BasicNameValuePair("subcategoryid", subcat));
					request.add(new BasicNameValuePair("userid", userID));
					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", cityID));
				}
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SEARCHRESULTBYCATEGORY_URL, request, SearchResultActivity1.this);
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

	private void getSearchResultsBySearch(String subcat) 
	{
		try
		{
			String userID = new StoreData(SearchResultActivity1.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(userID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
	//			JSONObject request = new JSONObject();
	//			request.put("q", category);
	//			request.put("lat", "");
	//			request.put("lon", "");
	//			request.put("offset", "");
	//			request.put("cityid", cityID);
	
				List<NameValuePair> request = new ArrayList<NameValuePair>(6);
				if(cityID.contains(","))
				{
					request.add(new BasicNameValuePair("q", subcat));
					request.add(new BasicNameValuePair("lat", cityID.split(",")[0]));
					request.add(new BasicNameValuePair("lon", cityID.split(",")[1]));
					request.add(new BasicNameValuePair("userid", userID));
					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", cityID));
				}
				else
				{
					request.add(new BasicNameValuePair("q", subcat));
					request.add(new BasicNameValuePair("lat", "0.00"));
					request.add(new BasicNameValuePair("lon", "0.00"));
					request.add(new BasicNameValuePair("userid", userID));
					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", cityID));
				}
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SEARCHRESULT_URL, request, SearchResultActivity1.this);
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
	public void onClick(View v) 
	{
		if(v == searchIcon)
		{
			MainActivity.SearchTerm = autoSearch.getText().toString();
			if(MainActivity.SearchTerm.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(SearchResultActivity1.this,SearchResultActivity1.class);
				intent.putExtra("searchTerm", MainActivity.SearchTerm);
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				startActivity(intent);
				finish();
			}
		}
		else if(v == searchResultHome)
		{
			Intent intent = new Intent(SearchResultActivity1.this,CitySelectActivity.class);
			startActivity(intent);
		}
	}
}