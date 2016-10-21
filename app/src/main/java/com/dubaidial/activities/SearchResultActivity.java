package com.dubaidial.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class SearchResultActivity extends HeaderActivity implements OnClickListener,HttpConnectionResponse
{
	public static Bitmap img = null;
	public static boolean isFromFragment = false;
	public static boolean displayAdd = false;
	public static boolean isFromLocation = false;
	public static int position = 0;
	private ArrayList<SearchResultModel> searchresults;
	public static ArrayList<SubCategory> subCategory; 
	public static ArrayList<String> subCategoryName;  
	private String categoryID = "",cityID = "",searchTerm = "";
	private TransparentProgressDialog progressdialog;
	private ListView resultLists,resultLists1,subCatList;
	private Button searchIcon;
	private AutoCompleteTextView autoSearch;
	private ImageView searchResultHome,addbanner;
	private TextView searchName,dataCount,selectCity,shareapp,favorites,addbusinesslisting;
	private boolean isItemSelected = true;
	private ArrayList<String> searchResult;
	private AlertDialog.Builder builder;
	private RelativeLayout mainLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		searchTerm = this.getIntent().getStringExtra("searchTerm");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchresult);
		addbanner = (ImageView)findViewById(R.id.addbanner);
		if(displayAdd)
		{
			displayAdd = false;
			addbanner.setVisibility(View.VISIBLE);
			addbanner.setBackground(new BitmapDrawable(getResources(), img));
//			addbanner.setImageBitmap(img);
		}
		else
		{
			addbanner.setVisibility(View.GONE);
		}
//		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
//	    	getActionBar().hide();
//	    }
		mainLayout = (RelativeLayout)findViewById(R.id.inner_content);
		mainLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext())
		{
		    public void onSwipeTop() {
//		        Toast.makeText(SearchResultActivity.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    		try{
					int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
					SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
					Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
					startActivity(intent);
					overridePendingTransition(0, 0);
					}catch(Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),"Sub-Category list not available for this category.",Toast.LENGTH_SHORT).show();
					}
//		        Toast.makeText(SearchResultActivity.this, "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		        Toast.makeText(SearchResultActivity.this, "left", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeBottom() {
//		        Toast.makeText(SearchResultActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		    }

		public boolean onTouch(View v, MotionEvent event) {
			resultLists.onTouchEvent(event);
		    return gestureDetector.onTouchEvent(event);
		}
		});
		findViewById(R.id.slidermenu).setOnClickListener
		(
				new View.OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						try{
						int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
						SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
						Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
						startActivity(intent);
						overridePendingTransition(0, 0);
						}catch(Exception e)
						{
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),"Sub-Category list not available for this category.",Toast.LENGTH_SHORT).show();
						}
					}
				});
	        		
		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
//		ImageView im = new ImageView(getApplicationContext());
//		im.setImageResource(R.drawable.red_logo);
//		progressdialog.setContentView(im);
//		progressdialog.setTitle(null);
//		progressdialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.red_logo));
//		progressdialog.setMessage(getResources().getString(R.string.loading));
		builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to exit Dubai Dial application?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
		
		searchIcon = (Button)findViewById(R.id.search_searchicon);
		autoSearch = (AutoCompleteTextView)findViewById(R.id.main_autosearch);
		searchResultHome = (ImageView)findViewById(R.id.searchResultHome);
		searchName = (TextView)findViewById(R.id.searchName);
		dataCount = (TextView)findViewById(R.id.dataCount);
		addbusinesslisting = (TextView)findViewById(R.id.addbusinesslisting);
		favorites = (TextView)findViewById(R.id.favorites);
		shareapp = (TextView)findViewById(R.id.shareapp);
		selectCity = (TextView)findViewById(R.id.selectcity);
		searchResultHome.setOnClickListener(this);
		searchIcon.setOnClickListener(this);
		autoSearch.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				Log.e("---------------->", "item clicekddd ");
				isItemSelected = true;
				MainActivity.SearchTerm = searchResult.get(position);
				if(MainActivity.SearchTerm.equals(""))
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent = new Intent(SearchResultActivity.this,SearchResultActivity.class);
					intent.putExtra("searchTerm", MainActivity.SearchTerm);
					intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
					startActivity(intent);
					finish();
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
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.AUTOSEARCH, request, SearchResultActivity.this);
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
		
		resultLists = (ListView)findViewById(R.id.resultList);	
		resultLists.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext())
		{
		    public void onSwipeTop() {
//		        Toast.makeText(SearchResultActivity.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    	try{
					int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
					SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
					Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
					startActivity(intent);
					overridePendingTransition(0, 0);
					}catch(Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),"Sub-Category list not available for this category.",Toast.LENGTH_SHORT).show();
					}
//		        Toast.makeText(SearchResultActivity.this, "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		        Toast.makeText(SearchResultActivity.this, "left", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeBottom() {
//		        Toast.makeText(SearchResultActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		    }

		public boolean onTouch(View v, MotionEvent event) {
			resultLists.onTouchEvent(event);
		    return gestureDetector.onTouchEvent(event);
		}
		});
		
		resultLists.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				Log.e("onItemClick", "onItemClick ----> "+position);
				Intent intent = new Intent(SearchResultActivity.this,DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("resultdata", searchresults.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
//		subCatList = (ListView)findViewById(R.id.subCatList);	
//		subCatList.setOnItemClickListener(new OnItemClickListener()
//		{
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
//			{
//				try
//				{
//					SearchResultActivity.position = position;
//					Log.e("subCatList", "subCatList onItemClick ----> "+position);
//					categoryID = subCategory.get(position).getName();
//					if(categoryID.equals("All"))
//						categoryID = SearchResultActivity.this.getIntent().getStringExtra("category");
//					searchTerm = categoryID;
////					autoSearch.setText(categoryID);
////					autoSearch.dismissDropDown();
//						getSearchResultsByID(categoryID);
//				}
//				catch(Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
		shareapp.setOnClickListener(this);
		favorites.setOnClickListener(this);
		selectCity.setOnClickListener(this);
		addbusinesslisting.setOnClickListener(this);

		categoryID = this.getIntent().getStringExtra("category");
		searchTerm = this.getIntent().getStringExtra("searchTerm");
		cityID = this.getIntent().getStringExtra("cityID");
		if(searchTerm == null)
		{
			getSearchResultsByID(categoryID);
			searchTerm = categoryID;
		}
		else
			getSearchResultsBySearch(searchTerm);
			
			autoSearch.setText(searchTerm);
			autoSearch.setAdapter(null);
			autoSearch.dismissDropDown();

//			selectCity.setText(" "+CityUtilities.SelectedCity);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		if(searchTerm == null)
//		{
//			getSearchResultsByID(categoryID);
//			searchTerm = categoryID;
//		}
//		else
//			getSearchResultsBySearch(searchTerm);
//			
//			autoSearch.setText(searchTerm);
//			autoSearch.setAdapter(null);
//			autoSearch.dismissDropDown();
		try
		{
//			int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
//			SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
//			Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
//			startActivity(intent);
//			overridePendingTransition(0, 0);
			cityID = CityUtilities.getCityID(CityUtilities.SelectedCity.trim());
//			if(DetailActivity.isFromFavourite)
//			{
//				DetailActivity.isFromFavourite = false;
//				searchTerm = MainActivity.SearchTerm;
////					if(searchTerm == null)
////					{
////						getSearchResultsByID(categoryID);
////						searchTerm = categoryID;
////					}
////					else
//						getSearchResultsBySearch(searchTerm);
//						
//						autoSearch.setText(searchTerm);
//						autoSearch.setAdapter(null);
//						autoSearch.dismissDropDown();
//			}
			if(isFromFragment)
			{
				isFromFragment = false;
				Log.e("subCatList", "subCatList onItemClick ----> "+position);
				categoryID = subCategory.get(position).getName();
				if(categoryID.contains("SIGN OUT"))
				{

					builder.show();
				}
				else
				{
//				if(categoryID.equals("All"))
//					categoryID = SearchResultActivity.this.getIntent().getStringExtra("category");
				searchTerm = categoryID;
				autoSearch.setText(categoryID);
				autoSearch.dismissDropDown();
					getSearchResultsByID(categoryID);
				}
			}
//			else 
//			{
//				if(searchTerm == null)
//				{
//					getSearchResultsByID(categoryID);
//					searchTerm = categoryID;
//				}
//				else
//					getSearchResultsBySearch(searchTerm);
//			}
//			else if(isFromLocation)
//			{
//				autoSearch.dismissDropDown();
//				autoSearch.setAdapter(null);
				selectCity.setText(" "+CityUtilities.SelectedCity);
//			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchResultActivity.this,android.R.layout.simple_list_item_1,searchResult);
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
								subCategory = new ArrayList<SubCategory>();
								subCategoryName = new ArrayList<String>();
								searchresults = new ArrayList<SearchResultModel>();
								
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
									result.setDistance(jarray.getJSONObject(i).getString("distance"));
									result.setOther_url(jarray.getJSONObject(i).getString("other_url"));
									result.setWorking_hour(jarray.getJSONObject(i).getString("working_hour"));
									result.setWeekday_from(jarray.getJSONObject(i).getString("weekday_from"));
									result.setWeekday_to(jarray.getJSONObject(i).getString("weekday_to"));
									result.setWeekend_from(jarray.getJSONObject(i).getString("weekend_from"));
									result.setWeekend_to(jarray.getJSONObject(i).getString("weekend_to"));
	//								result.setOther_url("http:\\\\www.google.com");
									result.setUser_testimonial(jarray.getJSONObject(i).getString("user_testimonial"));
									result.setUser_favourite(jarray.getJSONObject(i).getString("user_favourite"));
									searchresults.add(result);
								}
								dataCount.setText(" Total "+searchresults.size()+" Results Found in "+CityUtilities.SelectedCity);
								resultLists.setAdapter(new ResultAdapter(getApplicationContext(), R.layout.adapter_searchresult,data, searchresults));
//								if(categoryID != null)
//								{
								try
								{
									JSONArray jarray1 = resp.getJSONArray("subcategories");
//									SubCategory result1 = new SubCategory();
//									result1.setId("0");
//									result1.setName("All");
//									subCategory.add(result1);
//									subCategoryName.add(result1.getName());
									
									for(int i=0;i<jarray1.length();i++)
									{
										SubCategory result = new SubCategory();
										result.setId(jarray1.getJSONObject(i).getString("id"));
										result.setName(jarray1.getJSONObject(i).getString("name"));
										subCategory.add(result);
										subCategoryName.add(result.getName());
									}
									
									if(categoryID.equals("Bank"))
									{
										SearchResultActivity.subCategoryName.clear();
										SearchResultActivity.subCategory.clear();
									}
									SubCategory resultsss = new SubCategory();
									resultsss.setId("2312312312");
									resultsss.setName("  SIGN OUT  ");
									SearchResultActivity.subCategoryName.add("  SIGN OUT  ");
									SearchResultActivity.subCategory.add(resultsss);
									
									subCatList.setAdapter(new SubCategoryAdapter(getApplicationContext(), R.layout.subcat_list, subCategoryName, subCategory));
									
//								}
								}
								catch (Exception e1) 
								{
									e1.printStackTrace();
								}
								progressdialog.dismiss();
								isItemSelected = true;
								autoSearch.setText(searchTerm);
								autoSearch.setAdapter(null);
								autoSearch.dismissDropDown();
							}
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
			searchName.setText(categoryID);
			String userID = new StoreData(SearchResultActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
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
//					request.add(new BasicNameValuePair("categoryid", categoryID));
					request.add(new BasicNameValuePair("lat", cityID.split(",")[0]));
					request.add(new BasicNameValuePair("lon", cityID.split(",")[1]));
					request.add(new BasicNameValuePair("q", categoryID));
//					request.add(new BasicNameValuePair("subcategoryid", subcat));
					request.add(new BasicNameValuePair("userid", userID));
//					request.add(new BasicNameValuePair("offset", ""));
//					request.add(new BasicNameValuePair("cityid", ""));
				}
				else
				{
//					request.add(new BasicNameValuePair("categoryid", categoryID));
					request.add(new BasicNameValuePair("lat", MainActivity.latitude));
					request.add(new BasicNameValuePair("lon",  MainActivity.longitude));
					request.add(new BasicNameValuePair("q", categoryID));
//					request.add(new BasicNameValuePair("subcategoryid", subcat));
					request.add(new BasicNameValuePair("userid", userID));
//					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", cityID));
				}
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SEARCHRESULTBYCATEGORY_URL, request, SearchResultActivity.this);
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
			searchName.setText(subcat);
			String userID = new StoreData(SearchResultActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
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
//					request.add(new BasicNameValuePair("offset", ""));
//					request.add(new BasicNameValuePair("cityid", ""));
				}
				else
				{
					request.add(new BasicNameValuePair("q", subcat));
					request.add(new BasicNameValuePair("lat", MainActivity.latitude));
					request.add(new BasicNameValuePair("lon",  MainActivity.longitude));
					request.add(new BasicNameValuePair("userid", userID));
//					request.add(new BasicNameValuePair("offset", ""));
					request.add(new BasicNameValuePair("cityid", cityID));
				}
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SEARCHRESULTBYCATEGORY_URL, request, SearchResultActivity.this);
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
		if(v == addbusinesslisting)
		{
			if(MainActivity.isGuestUser)
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(SearchResultActivity.this,ListYourBusinessActivity.class);
				startActivity(intent);
			}
		}
		else if(v == favorites)
		{
			Intent intent = new Intent(SearchResultActivity.this,FavouritesActivity.class);
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
			Intent intent = new Intent(SearchResultActivity.this,CitySelectActivity.class);
			startActivity(intent);
		}
		else if(v == searchIcon)
		{
			MainActivity.SearchTerm = autoSearch.getText().toString();
			if(MainActivity.SearchTerm.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(SearchResultActivity.this,SearchResultActivity.class);
				intent.putExtra("searchTerm", MainActivity.SearchTerm);
				intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
				startActivity(intent);
				finish();
			}
		}
		else if(v == searchResultHome)
		{
			Intent intent = new Intent(SearchResultActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
	
	@Override
	public void onBackPressed() 
	{
		MainActivity.SearchTerm = "";
		super.onBackPressed();
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
	{
	    @Override
	    public void onClick(DialogInterface dialog, int which) 
	    {
	        switch (which)
	        {
		        case DialogInterface.BUTTON_POSITIVE:
					new StoreData(SearchResultActivity.this).setSharedPrefrence(SaveddataModel.FILENAME, "", SaveddataModel.UserID_key);
					MainActivity.isFinish = true;
					finish(); 
		            break;
	
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
	        }
	    }
	};
	
//	@Override
//	public void onItemClick1(int position) {
//		try
//		{
//			int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
//			SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
//			Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
//			startActivity(intent);
//			overridePendingTransition(0, 0);
//			
//			SearchResultActivity.position = position;
//			Log.e("subCatList", "subCatList onItemClick ----> "+position);
//			categoryID = subCategory.get(position).getName();
//			if(categoryID.equals("All"))
//				categoryID = SearchResultActivity.this.getIntent().getStringExtra("category");
//			searchTerm = categoryID;
//			autoSearch.setText(categoryID);
//			autoSearch.dismissDropDown();
//				getSearchResultsByID(categoryID);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}	
//		
//	}

//	@Override
//	public void onItemClick(int position)
//	{
//		try
//		{
//			int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
//			SlideoutActivity.prepare(SearchResultActivity.this, R.id.inner_content, width);
//			Intent intent =new Intent(SearchResultActivity.this,MenuActivity.class);
//			startActivity(intent);
//			overridePendingTransition(0, 0);
//			
//			SearchResultActivity.position = position;
//			Log.e("subCatList", "subCatList onItemClick ----> "+position);
//			categoryID = subCategory.get(position).getName();
//			if(categoryID.equals("All"))
//				categoryID = SearchResultActivity.this.getIntent().getStringExtra("category");
//			searchTerm = categoryID;
//			autoSearch.setText(categoryID);
//			autoSearch.dismissDropDown();
//				getSearchResultsByID(categoryID);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}	
//	}
}