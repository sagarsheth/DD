package com.dubaidial.activities;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.Utils.Config;
import com.dubaidial.activities.adapters.ViewPagerAdapter;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.models.SearchResultModel;
import com.dubaidial.storage.StoreData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techpro.dubaidial.R;

public class DetailActivity extends Activity implements OnClickListener,HttpConnectionResponse 
{
//	Steps for mapview
//	MD5 = 69:C2:87:F7:75:D6:FF:69:FD:38:FE:17:C1:95:1B:FE:23:FA:42:1E
//	key = AIzaSyAQj0kpnnleDTYyfrFSzlOXlVW3FU9TZR0

//	private GoogleMap map;
	
//	private MapView mapView;
	private LinearLayout ll_txt_indicator;
	private int selcted_index = 0;
	private List<Bitmap> urls = new ArrayList<Bitmap>();
	public boolean isItemSelected = false;
	private AutoCompleteTextView autoSearch;
	private String numberss = "";
	private ScrollView scroll;
	private TextView address,name,website,support,phone,rating,headerPoiName,poBox,details_landmark,landline,fax,selectCity,shareapp,favorites1,addbusinesslisting;
	private Button searchIcon,testimonials,favourite,direction,moreInfo,transparentImageView;
	private ImageView left,right,resultRating1,resultRating2,resultRating3,resultRating4,resultRating5,shareDetailsHeader,backDetailsHeader;
	private SearchResultModel searchresult;
	private TransparentProgressDialog progressdialog;
	private boolean isFavourite = false;
	public static boolean isFromFavourite = false;
	private GoogleMap mapView;
    private GestureDetector gestureDetector;
	private ArrayList<String> searchResult;
	private TextView companykeywords,companycategories,weekendTiming,weekdayTiming,workinghrs,companyProfile;
	private RelativeLayout mainView;
	private String mobileee = "";
	private Drawable imageArra[];
	ImageView imageView [];
	TextView txtView;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
//		gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());

//		mainView = (RelativeLayout)findViewById(R.id.mainView);
//		mainView.setOnTouchListener( new DetailActivity() {
//		    public void onSwipeTop() {
//		        Toast.makeText(DetailActivity.this, "top", Toast.LENGTH_SHORT).show();
//		    }
//		    public void onSwipeRight() {
//		        Toast.makeText(DetailActivity.this, "right", Toast.LENGTH_SHORT).show();
//		    }
//		    public void onSwipeLeft() {
//		        Toast.makeText(DetailActivity.this, "left", Toast.LENGTH_SHORT).show();
//		    }
//		    public void onSwipeBottom() {
//		        Toast.makeText(DetailActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//		    }
//
//		public boolean onTouch(View v, MotionEvent event) {
//		    return gestureDetector.onTouchEvent(event);
//		}
//		});
		searchresult = (SearchResultModel) getIntent().getExtras().getSerializable("resultdata");
		List<NameValuePair> request = new ArrayList<NameValuePair>(6);
		request.add(new BasicNameValuePair("listingid", searchresult.getId()));
		
		progressdialog.show();
		HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.COMPONYDATA, request, DetailActivity.this);
		Thread td = new Thread(connect);
		td.start();
//        mapView = (MapView) findViewById(R.id.mapView);
//        mapView.setBuiltInZoomControls(true);
//        MapController mc = mapView.getController();
//        double lat = Double.parseDouble(searchresult.getLat()); // latitude
//        double lon = Double.parseDouble(searchresult.getLon()); // longitude
//        GeoPoint geoPoint = new GeoPoint((int)(lat * 1E6), (int)(lon * 1E6));
//        mc.animateTo(geoPoint);
//        mc.setZoom(15);
//        mapView.invalidate(); 
//        List<Overlay> mapOverlays = mapView.getOverlays();
//        Drawable drawable = this.getResources().getDrawable(R.drawable.actionbar_logo);
//        AddItemizedOverlay itemizedOverlay = new AddItemizedOverlay(drawable, this);
                 
                 
//        OverlayItem overlayitem = new OverlayItem(geoPoint, "Hello", "Sample Overlay item");
//                 
//        itemizedOverlay.addOverlay(overlayitem);
//        mapOverlays.add(itemizedOverlay);

	
		
		mapView = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
		if (mapView!=null)
		{
			LatLng HAMBURG = null,KIEL = null;
//			int lat,lng;
			if(searchresult.getLat() == null || searchresult.getLon() == null)
			{
				String[] loc = CityUtilities.getCityLocation(searchresult.getCity()).split(",");
				HAMBURG = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));
				KIEL = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));

				CameraUpdate center = CameraUpdateFactory.newLatLng(HAMBURG);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
				mapView.moveCamera(center);
				mapView.animateCamera(zoom);
			    Marker kiel = mapView.addMarker(new MarkerOptions().position(KIEL).title(searchresult.getName()).snippet(searchresult.getAddress()));
			}
			else if(searchresult.getLat().equals("") || searchresult.getLon().equals(""))
			{
				String[] loc = CityUtilities.getCityLocation(searchresult.getCity()).split(",");
				HAMBURG = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));
				KIEL = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));

				CameraUpdate center = CameraUpdateFactory.newLatLng(HAMBURG);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
				mapView.moveCamera(center);
				mapView.animateCamera(zoom);
			    Marker kiel = mapView.addMarker(new MarkerOptions().position(KIEL).title(searchresult.getName()).snippet(searchresult.getAddress()));
			}
			else if(searchresult.getLat().startsWith("0.00") || searchresult.getLon().startsWith("0.00"))
			{
				String[] loc = CityUtilities.getCityLocation(searchresult.getCity()).split(",");
				HAMBURG = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));
				KIEL = new LatLng(Double.parseDouble(loc[0]),Double.parseDouble(loc[1]));

				CameraUpdate center = CameraUpdateFactory.newLatLng(HAMBURG);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);
				mapView.moveCamera(center);
				mapView.animateCamera(zoom);
			    Marker kiel = mapView.addMarker(new MarkerOptions().position(KIEL).title(searchresult.getName()).snippet(searchresult.getAddress()));
			}
			else
			{
				HAMBURG = new LatLng(Double.parseDouble(searchresult.getLat()),Double.parseDouble(searchresult.getLon()));
				KIEL = new LatLng(Double.parseDouble(searchresult.getLat()),Double.parseDouble(searchresult.getLon()));

				CameraUpdate center = CameraUpdateFactory.newLatLng(HAMBURG);
				CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
				mapView.moveCamera(center);
				mapView.animateCamera(zoom);
			    Marker kiel = mapView.addMarker(new MarkerOptions().position(KIEL).title(searchresult.getName()).snippet(searchresult.getAddress()));
			}
		}
		
		searchIcon = (Button)findViewById(R.id.main_searchicon);
		searchIcon.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v) 
			{

				MainActivity.SearchTerm = autoSearch.getText().toString();
				if(MainActivity.SearchTerm.equals(""))
				{
					Toast.makeText(getApplicationContext(),getResources().getString(R.string.blankSearhString),Toast.LENGTH_SHORT).show();
				}
				else
				{
					isFromFavourite = true;
//					Intent intent = new Intent(DetailActivity.this,SearchResultActivity.class);
//					intent.putExtra("searchTerm", MainActivity.SearchTerm);
//					intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
//					startActivity(intent);
					finish();
				}
			
			}
		});
		
		autoSearch = (AutoCompleteTextView)findViewById(R.id.main_autosearch);
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
					isFromFavourite = true;
//					Intent intent = new Intent(DetailActivity.this,SearchResultActivity.class);
//					intent.putExtra("searchTerm", MainActivity.SearchTerm);
//					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//					intent.putExtra("cityID", CityUtilities.getCityID(CityUtilities.SelectedCity.trim()));
//					startActivity(intent);
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
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.AUTOSEARCH, request, DetailActivity.this);
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
		addbusinesslisting = (TextView)findViewById(R.id.addbusinesslisting);
		favorites1 = (TextView)findViewById(R.id.favorites);
		workinghrs = (TextView)findViewById(R.id.workinghrs);
		weekdayTiming = (TextView)findViewById(R.id.weekdayTiming);
		weekendTiming = (TextView)findViewById(R.id.weekendTiming);
		shareapp = (TextView)findViewById(R.id.shareapp);
		selectCity = (TextView)findViewById(R.id.selectcity);
		autoSearch.addTextChangedListener(textWatcher);
		scroll = (ScrollView)findViewById(R.id.theScroller);
		address = (TextView)findViewById(R.id.details_address);
		poBox = (TextView)findViewById(R.id.poBox);
		landline = (TextView)findViewById(R.id.landline);
		fax = (TextView)findViewById(R.id.fax);
		details_landmark = (TextView)findViewById(R.id.details_landmark);
		name = (TextView)findViewById(R.id.details_name1);
		website = (TextView)findViewById(R.id.details_site);
		support = (TextView)findViewById(R.id.details_support);
		phone = (TextView)findViewById(R.id.details_phone);
		rating = (TextView)findViewById(R.id.details_rating);
//		headerPoiName = (TextView)findViewById(R.id.headerPoiName);
		testimonials = (Button)findViewById(R.id.testimonials);
		favourite = (Button)findViewById(R.id.details_favourite);
		direction = (Button)findViewById(R.id.details_direction);
		moreInfo = (Button)findViewById(R.id.moreInfo);
//		shareDetailsHeader = (ImageView) findViewById(R.id.shareDetailsHeader);
//		listingImage = (ImageView) findViewById(R.id.listingImage);
		backDetailsHeader = (ImageView) findViewById(R.id.backDetailsHeader);
		resultRating1 = (ImageView) findViewById(R.id.details_rating1);
	    resultRating2 = (ImageView) findViewById(R.id.details_rating2);
	    resultRating3 = (ImageView) findViewById(R.id.details_rating3);
	    resultRating4 = (ImageView) findViewById(R.id.details_rating4);
	    resultRating5 = (ImageView) findViewById(R.id.details_rating5);
	    left = (ImageView) findViewById(R.id.left);
	    right = (ImageView) findViewById(R.id.right);
	    companykeywords = (TextView)findViewById(R.id.company_keywords);
	    companyProfile = (TextView)findViewById(R.id.profile);
	    companycategories = (TextView)findViewById(R.id.company_categories);
		transparentImageView = (Button)findViewById(R.id.transparent_image);
	    
		testimonials.setText(searchresult.getTestimonials()+" Reviews");
		testimonials.setOnClickListener(this);
		favourite.setOnClickListener(this);
		direction.setOnClickListener(this);
//		shareDetailsHeader.setOnClickListener(this);
		backDetailsHeader.setOnClickListener(this);
		moreInfo.setOnClickListener(this);
		website.setOnClickListener(this);
		support.setOnClickListener(this);
		phone.setOnClickListener(this);
		landline.setOnClickListener(this);
		transparentImageView.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
//		transparentImageView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    //Button Pressed
//                }
//                if(event.getAction() == MotionEvent.ACTION_UP){
//                	Intent intent = new Intent(DetailActivity.this,MapActivity.class);
//        			Bundle bundle = new Bundle();
//        			bundle.putSerializable("resultdata", searchresult);
//        			intent.putExtras(bundle);
//        			startActivity(intent);
//                }
//                return false;
//            }
//        });
//		fax.setOnClickListener(this);
//		transparentImageView.setOnTouchListener(new View.OnTouchListener() {
//
//		    @Override
//		    public boolean onTouch(View v, MotionEvent event) {
//		        int action = event.getAction();
//		        switch (action) {
//		           case MotionEvent.ACTION_DOWN:
//		                // Disallow ScrollView to intercept touch events.
//		        	   scroll.requestDisallowInterceptTouchEvent(true);
//		                // Disable touch on transparent view
//		                return false;
//
//		           case MotionEvent.ACTION_UP:
//		                // Allow ScrollView to intercept touch events.
//		        	   scroll.requestDisallowInterceptTouchEvent(false);
//		                return true;
//
//		           case MotionEvent.ACTION_MOVE:
//		        	   scroll.requestDisallowInterceptTouchEvent(true);
//		                return false;
//
//		           default: 
//		                return true;
//		        }   
//		    }
//		});
		

//	    Display display = getWindowManager().getDefaultDisplay();
//	    int width = display.getWidth();
//	    final Animation posX = new TranslateAnimation(0, width - 50, 0, 0);
//	    posX.setDuration(1000);
//	    posX.setFillAfter(true);
//		String imagePath = searchresult.getCompany_logo();
//		Log.e("iiiiiiiiiiiii", "i       patgh --> "+imagePath);
//        if(imagePath != null)
//        {
//        	if(!imagePath.equals(""))
//        	{
//        	    new DownloadImage().execute(imagePath);
//        	}
//        }
	        
		if(searchresult.getUser_favourite().equals("1"))
		{
			isFavourite = true;
			favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_star_active, 0, 0, 0);
		}
		
		String pobox = searchresult.getPobox_no();
		if(pobox.equals(""))
			poBox.setVisibility(View.GONE);
		else
			poBox.setText("PO Box No.:"+pobox);
		
		String landm = searchresult.getLandmark();
		if(landm.equals(""))
			details_landmark.setVisibility(View.GONE);
		else
			details_landmark.setText(landm);
		
		String landl = searchresult.getContact_number();
		if(landl.equals(""))
			landline.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.getRegional_code()+""+landl;
			if(!number.startsWith("+"))
				landline.setText("+"+searchresult.getRegional_code()+""+landl);
			else
				landline.setText(""+searchresult.getRegional_code()+""+landl);
		}

		String workingHours = searchresult.getWorking_hour();
		if(workingHours.equals(""))
			workinghrs.setVisibility(View.GONE);
		else
			workinghrs.setText("Working Hours - "+workingHours);
		
		String weekdaytF= searchresult.getWeekday_from();
		String weekdaytT= searchresult.getWeekday_to();
		if(weekdaytF.equals("") || weekdaytT.equals(""))
			weekdayTiming.setVisibility(View.GONE);
		else
			weekdayTiming.setText("Weekday Timings From - "+weekdaytF+" TO "+weekdaytT);
		
		String weekendtF = searchresult.getWeekend_from();
		String weekendtT = searchresult.getWeekend_to();
		if(weekendtF.equals("") || weekendtT.equals(""))
			weekendTiming.setVisibility(View.GONE);
		else
			weekendTiming.setText("Weekend Timings From - "+weekendtF+" TO "+weekendtT);
		
		String faxn = searchresult.getFax_number();
		if(faxn.equals(""))
			fax.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.getRegional_code()+""+faxn;
			if(!number.startsWith("+"))
				fax.setText("+"+searchresult.getRegional_code()+""+faxn);
			else
				fax.setText(""+searchresult.getRegional_code()+""+faxn);
		}

		String company_profile = searchresult.getProfile();
		companyProfile.setText(company_profile);
		
		name.setText(searchresult.getName());
//		headerPoiName.setText(searchresult.getName());
		try{
		if(searchresult.getOther_url().equals(""))
			moreInfo.setVisibility(View.INVISIBLE);
		}catch(Exception e)
		{
			moreInfo.setVisibility(View.INVISIBLE);
			e.printStackTrace();
		}
		String addr = searchresult.getAddress();
		if(addr.equals(""))
			address.setVisibility(View.GONE);
		else
			address.setText(addr);
		
//		String landm = searchresult.getLandmark();
//		if(!landm.equals(""))
//		{
//			address.setText(address.getText().toString()+", "+landm);
//		}
//		
//		String pobox = searchresult.getPobox_no();
//		if(!pobox.equals(""))
//		{
//			
//			address.setText(address.getText().toString()+", PO Box No.:"+pobox);
//		}
		
		String web = searchresult.getWebsite();
		if(web.equals(""))
			website.setVisibility(View.GONE);
		else
			website.setText(" "+web);
		
//		String cs = searchresult.getEmail_address();
//		if(cs.equals(""))
//			support.setVisibility(View.GONE);
//		else
//			support.setText(" "+cs);
		
		String mob = searchresult.getMobile();
		if(mob.equals(""))
			phone.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.getRegional_code()+""+mob;
			if(!number.startsWith("+"))
				phone.setText(" +"+searchresult.getRegional_code()+""+mob);
		}
		
        String rate = searchresult.getAverage_ratings();
        try
        {
	        if(rate != null)
	        {
//	        	int rateing = Integer.parseInt(rate);
	        	if(rate.startsWith("1"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_active);
	        	}
	        	else if(rate.startsWith("2"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_active);	        		
	        	}
	        	else if(rate.startsWith("3"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_active);	        		
	        	}
	        	else if(rate.startsWith("4"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_active);
	        		resultRating4.setImageResource(R.drawable.icon_star_active);	        		
	        	}
	        	else if(rate.startsWith("5"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_active);
	        		resultRating4.setImageResource(R.drawable.icon_star_active);
	        		resultRating5.setImageResource(R.drawable.icon_star_active);	        		
	        	}
	        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        shareapp.setOnClickListener(this);
		favorites1.setOnClickListener(this);
		selectCity.setOnClickListener(this);
		addbusinesslisting.setOnClickListener(this);
	}
	
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			selectCity.setText(" "+CityUtilities.SelectedCity);
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
				Intent intent = new Intent(DetailActivity.this,ListYourBusinessActivity.class);
				startActivity(intent);
			}
		}
		else if(v == transparentImageView)
		{
			Intent intent = new Intent(DetailActivity.this,MapActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("resultdata", searchresult);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else if(v == favorites1)
		{
			Intent intent = new Intent(DetailActivity.this,FavouritesActivity.class);
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
			Intent intent = new Intent(DetailActivity.this,CitySelectActivity.class);
			startActivity(intent);
		}
		else if(v == testimonials)
		{
			Intent intent = new Intent(DetailActivity.this,ReviewActivity.class);
			intent.putExtra("listingid", searchresult.getId());
			intent.putExtra("ratings", searchresult.getAverage_ratings());
			intent.putExtra("name", searchresult.getName());
			intent.putExtra("test", searchresult.getUser_testimonial());
			startActivity(intent);
		}
		else if(v == left)
		{
			try{
//			if(selcted_index > 0)
//			{
//				selcted_index = selcted_index-1;
//				String imagePath = urls.get(selcted_index);
//				Log.e("iiii	", selcted_index+" "+imagePath);
//				 if(imagePath != null)
//			        {
//			        	if(!imagePath.equals(""))
//			        	{
//			        	    new DownloadImage().execute(imagePath);
//			        	}
//			        }
//			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		else if(v == right)
		{
			try{
//			if(selcted_index < urls.size())
//			{
//				selcted_index = selcted_index+1;
//				String imagePath = urls.get(selcted_index);
//				Log.e("iiii	", selcted_index+" "+imagePath);
//				 if(imagePath != null)
//			        {
//			        	if(!imagePath.equals(""))
//			        	{
//			        	    new DownloadImage().execute(imagePath);
//			        	}
//			        }
//			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(v == website)
		{
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http:\\"+searchresult.getWebsite()));
			startActivity(browserIntent);
		}
		else if(v == support)
		{
			StoreData save = new StoreData(DetailActivity.this);
			String mob = save.getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.Mobile_key);
			if(mob.equals(""))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Please enter your mobile number so that it would be easy to contact you.");
//				alert.setMessage("Please enter your mobile number so that it would be easy to contact you.");
				// Create TextView
				final EditText input = new EditText (this);
				alert.setView(input);
				
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					mobileee = input.getText().toString();
				     Log.e("-->", ""+mobileee);
				     if(mobileee.equals(""))
				     {
				    	 Toast.makeText(getApplicationContext(), "Please enter all details.", Toast.LENGTH_SHORT).show();
				     }
				     else
				     {
				    	 	String userID = new StoreData(DetailActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
				    	 	List<NameValuePair> request = new ArrayList<NameValuePair>(6);
				    	 	request.add(new BasicNameValuePair("userid", userID));
				    	 	request.add(new BasicNameValuePair("mobile", mobileee));
						
				    	 	progressdialog.show();
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.ADDMOBILE, request, DetailActivity.this);
							Thread td = new Thread(connect);
							td.start();
				     }
				    // Do something with value!
				  }
				});
				
				  alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				      // Canceled.
				  }
				});
				alert.show();
			}
			else
			{
				String logintype = save.getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.isSocial);
				String userID = save.getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
				List<NameValuePair> request = new ArrayList<NameValuePair>(6);
				request.add(new BasicNameValuePair("listingid", searchresult.getId()));
				request.add(new BasicNameValuePair("userid", userID));
				request.add(new BasicNameValuePair("logintype", logintype));
				
				progressdialog.show();
				HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.SENDENQUIRY, request, DetailActivity.this);
				Thread td = new Thread(connect);
				td.start();
			}
//			Intent i = new Intent(Intent.ACTION_SEND);
//			i.setType("message/rfc822");
//			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{searchresult.getCustomer_service()});
//			i.putExtra(Intent.EXTRA_SUBJECT, "");
//			i.putExtra(Intent.EXTRA_TEXT   , "");
//			try 
//			{
//			    startActivity(Intent.createChooser(i, "Send mail..."));
//			} catch (android.content.ActivityNotFoundException ex) 
//			{
//			    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//			}
		}
		else if(v == phone)
		{
			numberss = phone.getText().toString();
			showDialog("You can call or Save the number.");
		}
		else if(v == landline)
		{
			numberss = landline.getText().toString();
			showDialog("You can call or Save the number.");
		}
		else if(v == fax)
		{
			numberss = fax.getText().toString();
			showDialog("You can call or Save the number.");
		}
		else if(v == shareDetailsHeader)
		{
			
		}
		else if(v == backDetailsHeader)
		{
			finish();
		}
		else if(v == moreInfo)
		{
			try{
			Intent intent = new Intent(DetailActivity.this,MoreInfoWebViewActivity.class);
			intent.putExtra("additionalinfo", searchresult.getOther_url().trim());
			startActivity(intent);
			}catch(Exception e)
			{

				Toast.makeText(getApplicationContext(),"More info not available.",Toast.LENGTH_SHORT).show();
			}
		}
		else if(v == favourite)
		{
			if(MainActivity.isGuestUser)
			{
				Toast.makeText(getApplicationContext(),"Guest User Can't Add To Favorites Please SignUp.",Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(isFavourite)
				{
					Toast.makeText(getApplicationContext(),"You already added this as Favorite.",Toast.LENGTH_SHORT).show();
	//				try
	//				{
	//					String userID = new StoreData(DetailActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
	//					if(userID.equals(""))
	//					{
	//						Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
	//					}
	//					else
	//					{			
	//						List<NameValuePair> request = new ArrayList<NameValuePair>(6);
	//						request.add(new BasicNameValuePair("userid", userID));
	//						request.add(new BasicNameValuePair("listingid", searchresult.getId()));
	//						
	//						progressdialog.show();
	//						HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.DELETEFAVOURITE_URL, request, DetailActivity.this);
	//						Thread td = new Thread(connect);
	//						td.start();
	//					}
	//				}
	//				catch(Exception e)
	//				{
	//					e.printStackTrace();
	//					Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
	//				}
				}
				else
				{
					try
					{
						String userID = new StoreData(DetailActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
						if(userID.equals(""))
						{
							Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
						}
						else
						{			
							List<NameValuePair> request = new ArrayList<NameValuePair>(6);
							request.add(new BasicNameValuePair("userid", userID));
							request.add(new BasicNameValuePair("listingid", searchresult.getId()));
							
							progressdialog.show();
							HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.ADDFAVOURITE_URL, request, DetailActivity.this);
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
		}
		else if(v == direction)
		{
//			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?saddr="+searchresult.getLat()+","+searchresult.getLon()+"&daddr="+MainActivity.latitude+","+MainActivity.longitude));
//			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?f=d&daddr="+searchresult.getLat()+","+searchresult.getLon()));
//			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?f=d&daddr="+searchresult.getAddress()));
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?f=d&daddr="+searchresult.getName()));
			intent.setComponent(new ComponentName("com.google.android.apps.maps","com.google.android.maps.MapsActivity"));
			startActivity(intent);
		}
	}
	JSONObject resp;
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
						progressdialog.dismiss();
						resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							if(resp.toString().contains("company_keywords"))
							{
								JSONArray jarray = resp.getJSONArray("msg");
								String company_categories = jarray.getJSONObject(0).getString("company_categories");
								String company_keywords = jarray.getJSONObject(0).getString("company_keywords");
								
								companykeywords.setText(company_keywords);
								companycategories.setText(company_categories);
								Thread td = new Thread(new Runnable() {
									
									@Override
									public void run() {
										JSONArray jarray1;
										try {
											jarray1 = resp.getJSONArray("images");
										try{
										 URL url = new URL(searchresult.getCompany_logo().trim());
											HttpURLConnection connection = (HttpURLConnection) url.openConnection();
										    connection.setDoInput(true);
										    connection.connect();
										    InputStream input = connection.getInputStream();
										    urls.add(BitmapFactory.decodeStream(input));
										}catch(Exception e)
										{
											e.printStackTrace();
										}
//										urls.add(getBitmapFromURL(searchresult.getCompany_logo().trim()));
										
										for(int i=0;i<jarray1.length();i++)
										{
											try{
											 URL url1 = new URL(jarray1.getJSONObject(i).getString("image_url").trim());
												HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
											    connection1.setDoInput(true);
											    connection1.connect();
											    InputStream input1 = connection1.getInputStream();
											    urls.add(BitmapFactory.decodeStream(input1));
											}catch(Exception e)
											{
												e.printStackTrace();
											}
//											urls.add(getBitmapFromURL(jarray1.getJSONObject(i).getString("image_url")));
										}
										runOnUiThread( new Runnable() {
											public void run() {
										ViewPagerAdapter adapter = new ViewPagerAdapter(DetailActivity.this, urls);
										ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
										myPager.setAdapter(adapter);
										myPager.setCurrentItem(0);
										
										ll_txt_indicator =(LinearLayout)findViewById(R.id.ll_txt_indicators);
										initImageHolders();
										myPager.setOnPageChangeListener(mOnPageChangeListener);
										
										}
									
								});
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
								td.start();

//							    new Thread(new Runnable() 
//							    {
//									@Override
//									public void run()
//									{
//										for(int i=0;i<src.size();i++)
//										{
//											Bitmap myBitmap = null;
//											try 
//											{
//												Log.e("--------------------------", "--------------------------"+src.get(i));
//										        URL url = new URL(src.get(i));
//												HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//											    connection.setDoInput(true);
//											    connection.connect();
//											    InputStream input = connection.getInputStream();
//											    myBitmap = BitmapFactory.decodeStream(input);
//											} 
//											catch (IOException e) 
//											{
//										        e.printStackTrace();
//										        myBitmap = null;
//											}
//											urls.add(myBitmap);
//											if(i == src.size()-1)
//											{
//												runOnUiThread(new Runnable() {
//													public void run() {
//														Log.e("--------------------------", "--------------------------");
//														ViewPagerAdapter adapter = new ViewPagerAdapter(DetailActivity.this, urls);
//														ViewPager myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
//														myPager.setAdapter(adapter);
//														myPager.setCurrentItem(0);
//														
//														ll_txt_indicator =(LinearLayout)findViewById(R.id.ll_txt_indicators);
//														initImageHolders();
//														myPager.setOnPageChangeListener(mOnPageChangeListener);
//													}
//												});
//												
//											}
//										}
//									}
//								}); 
							
//								try
//								{
//									String imagePath = urls.get(0);
//									Log.e("iiii	", selcted_index+" "+imagePath);
//									if(imagePath != null)
//							        {
//							        	if(!imagePath.equals(""))
//							        	{
//							        	    new DownloadImage().execute(imagePath);
//							        	}
//							        }
//								}
//								catch(Exception e)
//								{
//									try
//									{
//										String imagePath = searchresult.getCompany_logo();
//										Log.e("iiiiiiiiiiiii", "i       patgh --> "+imagePath);
//								        if(imagePath != null)
//								        {
//								        	if(!imagePath.equals(""))
//								        	{
//								        	    new DownloadImage().execute(imagePath);
//								        	}
//								        }
//									}
//									catch(Exception e1)
//									{
//										e1.printStackTrace();
//									}
//								e.printStackTrace();
//							}
						}
						else if(resp.toString().contains("keyword"))
						{
							JSONArray jarray = resp.getJSONArray("msg");
								searchResult = new ArrayList<String>();
								searchResult.clear();
								for(int i=0;i<jarray.length();i++)
								{
									searchResult.add(jarray.getJSONObject(i).getString("keyword"));
								}
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailActivity.this,android.R.layout.simple_list_item_1,searchResult);
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
							else if(resp.toString().contains("Email Sent Successful"))
							{
								Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
							}
							else if(resp.toString().contains("Mobile No Updated Successfully"))
							{
								StoreData save = new StoreData(DetailActivity.this);
								save.setSharedPrefrence(SaveddataModel.FILENAME, mobileee, SaveddataModel.Mobile_key);
								Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
							}
							else
							{
//							if(isFavourite)
//							{
//								isFavourite = false;
//								favourite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.icon_star_inactive, 0, 0, 0);								
//							}
//							else
//							{
								isFavourite = true;
								favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_star_active, 0, 0, 0);								
//							}
							Toast.makeText(getApplicationContext(),resp.getString("msg"),Toast.LENGTH_SHORT).show();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			Log.e("result is ok", "result is ok");
		}
		else
		{
			Log.e("result is not ok", "result is not ok");
		}
	}
	
	public void showDialog(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false).setPositiveButton("Call Number", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id) 
		    {
	            dialog.cancel();
	            Intent callIntent = new Intent(Intent.ACTION_CALL);
	            callIntent.setData(Uri.parse("tel:"+numberss));
	            startActivity(callIntent);
			}
		})
		.setNegativeButton("Save Number", new DialogInterface.OnClickListener() 
		{
		    public void onClick(DialogInterface dialog, int id)
		    {
	            dialog.cancel();
		    	Intent intent = new Intent(Intent.ACTION_INSERT);
	            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
	            intent.putExtra(ContactsContract.Intents.Insert.NAME, searchresult.getContact_name());
	            intent.putExtra(ContactsContract.Intents.Insert.PHONE,searchresult.getMobile());
	            intent.putExtra(ContactsContract.Intents.Insert.COMPANY,searchresult.getContact_number());
	            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, searchresult.getCustomer_service());
	            startActivityForResult(intent, 1);
		    }
		});
		AlertDialog alert = builder.create();
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}

//	@Override
//	protected boolean isRouteDisplayed() 
//	{
//		return false;
//	}
	
	
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		 return gestureDetector.onTouchEvent(event);
//	}
//	 private final class GestureListener extends SimpleOnGestureListener {
//
//	        private static final int SWIPE_THRESHOLD = 100;
//	        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//	        @Override
//	        public boolean onDown(MotionEvent e) {
//	            return true;
//	        }
//
//	        @Override
//	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//	            boolean result = false;
//	            try {
//	                float diffY = e2.getY() - e1.getY();
//	                float diffX = e2.getX() - e1.getX();
//	                if (Math.abs(diffX) > Math.abs(diffY)) {
//	                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//	                        if (diffX > 0) {
//	                            onSwipeRight();
//	                        } else {
//	                            onSwipeLeft();
//	                        }
//	                    }
//	                } else {
//	                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//	                        if (diffY > 0) {
//	                            onSwipeBottom();
//	                        } else {
//	                            onSwipeTop();
//	                        }
//	                    }
//	                }
//	            } catch (Exception exception) {
//	                exception.printStackTrace();
//	            }
//	            return result;
//	        }
//	    }
//
//	    public void onSwipeRight() {
//	    }
//
//	    public void onSwipeLeft() {
//	    }
//
//	    public void onSwipeTop() {
//	    }
//
//	    public void onSwipeBottom() {
//	    }
	
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//	    int action = ev.getAction();
//	    switch (action) {
//	    case MotionEvent.ACTION_DOWN:
//	        // Disallow ScrollView to intercept touch events.
//	    	scroll.getParent().requestDisallowInterceptTouchEvent(true);
//	        break;
//
//	    case MotionEvent.ACTION_UP:
//	        // Allow ScrollView to intercept touch events.
//	    	scroll.getParent().requestDisallowInterceptTouchEvent(false);
//	        break;
//	    }
//
//	    // Handle MapView's touch events.
//	    super.onTouchEvent(ev);
//	    return true;
//	}
	
	@SuppressLint("NewApi")
	private void initImageHolders(){
//		imageView= new ImageView[urls.size()];
		txtView= new TextView(this);
		txtView.setTextColor(Color.BLACK);
		txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		txtView.setText("Photo 0 of "+urls.size());
		
		
//		Drawable drawable = getResources().getDrawable(R.drawable.grey);	    
//		for(int i =0;i<urls.size();i++){
//			imageView[i]= new ImageView(this);
//			imageView[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
//			if(i==0)
//				setImage(imageView[i], R.drawable.green);
//			else 
//				setImage(imageView[i], R.drawable.grey);
			
//			ll_img_indicator.addView(imageView[i]);			
//		}
		ll_txt_indicator.addView(txtView);
//		ll_img_indicator.invalidate();
	}
	
//	@SuppressLint("NewApi")
//	private void setImage(ImageView iv ,int drawableId){
//		Drawable drawable = getResources().getDrawable(drawableId);
//		if(Build.VERSION.SDK_INT >= 16){
//			iv.setBackground(drawable);
//        }else{
//        	iv.setBackgroundDrawable(drawable);
//        }
//	}
	
	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           System.out.println("---------------- pagen a "+position);
        }
        @Override
        public void onPageSelected(int position) {
            // Since a new page has been selected by the {@link ViewPager},
            // update the tab selection in the carousel.
        	System.out.println("---------------- pagen onPageSelected "+position);
        	
//        	for(int i =0;i<urls.size();i++){
//        		if(i==position){
//        			setImage(imageView[i], R.drawable.green);
//        		}
//        		else {
//        			setImage(imageView[i], R.drawable.grey);
//        		}
//        	}
        	txtView.setText("Photo "+(position+1)+" of "+urls.size());
        	
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}