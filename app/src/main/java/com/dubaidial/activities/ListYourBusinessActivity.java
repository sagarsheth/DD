package com.dubaidial.activities;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.Utils.Config;
import com.dubaidial.communication.HttpConnection;
import com.dubaidial.listeners.HttpConnectionResponse;
import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class ListYourBusinessActivity extends Activity implements OnClickListener,HttpConnectionResponse
{
	private Bitmap selectedImageBitmap;
	private Button choose, submit;
	private EditText compname,contactName, emailID,address,PoboxNo,landmark,landline,fax,mobile,customerservice,website,profiles,brands;
	private Spinner city,categorys;
	private ImageView companypic,listBusinessHome;
	private String base64EncodedString = "";
	private TransparentProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_listing);

		progressdialog =  new TransparentProgressDialog(this,R.drawable.red_logo);
		
		city = (Spinner)findViewById(R.id.citynm);
		categorys = (Spinner)findViewById(R.id.category);
		compname = (EditText) findViewById(R.id.companyname);
		contactName = (EditText) findViewById(R.id.contactname);
		emailID = (EditText) findViewById(R.id.emailadd);
		address = (EditText) findViewById(R.id.address);
		PoboxNo = (EditText) findViewById(R.id.Pobox);
		landmark = (EditText) findViewById(R.id.landmark);
		mobile = (EditText) findViewById(R.id.mobile);
		landline = (EditText) findViewById(R.id.landline);
		fax = (EditText) findViewById(R.id.faxx);
		customerservice = (EditText) findViewById(R.id.cservice);
		website = (EditText) findViewById(R.id.fax);
		profiles = (EditText) findViewById(R.id.profile);
		brands = (EditText) findViewById(R.id.brand);
		companypic = (ImageView) findViewById(R.id.companylogo);
		listBusinessHome = (ImageView) findViewById(R.id.listBusinessHome);
		submit = (Button) findViewById(R.id.submit);
		
		submit.setOnClickListener(this);
		listBusinessHome.setOnClickListener(this);
		companypic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		if (v == choose)
		{
			Intent gallery = new Intent(Intent.ACTION_VIEW,	Uri.parse("content://media/internal/images/media"));
			startActivityForResult(gallery,21);
		}
		else if (v == companypic)
		{
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);//
			startActivityForResult(Intent.createChooser(intent, "Select Picture"),11);
		}
		else if (v == listBusinessHome)
		{
			Intent intent = new Intent(ListYourBusinessActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
		else if (v == submit)
		{
			String userID = new StoreData(ListYourBusinessActivity.this).getSharedPreference(SaveddataModel.FILENAME, SaveddataModel.UserID_key);
			if(userID.equals(""))
			{
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.guest_user),Toast.LENGTH_SHORT).show();
			}
			else
			{
				String coname = compname.getText().toString();
				String emid = emailID.getText().toString();
				String addr = address.getText().toString();
				String cat = categorys.getSelectedItem().toString();
				int ind = categorys.getSelectedItemPosition();
				Log.e("index ---->", "index ---->"+ind);
				String cityName1 = city.getSelectedItem().toString();
				if(coname.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Please enter Company Name.",Toast.LENGTH_SHORT).show();
				}
				else if(emid.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Please enter Email Address.",Toast.LENGTH_SHORT).show();
				}
				else if(cityName1.equals("Select City"))
				{
					Toast.makeText(getApplicationContext(),"Please Select City.",Toast.LENGTH_SHORT).show();
				}
				else if(addr.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Please enter Address.",Toast.LENGTH_SHORT).show();
				}
				else if(cat.equals("Select Category"))
				{
					Toast.makeText(getApplicationContext(),"Please enter Categories.",Toast.LENGTH_SHORT).show();
				}
				else
				{
					String contName = contactName.getText().toString();
					String pobno = PoboxNo.getText().toString();
					String lmark = landmark.getText().toString();
					String mob = validateNumber(mobile.getText().toString());
					String landl = validateNumber(landline.getText().toString());
					String faxno = validateNumber(fax.getText().toString());
					String coustservice = customerservice.getText().toString();
					String webs = validateWebAddress(website.getText().toString());
					String pro = profiles.getText().toString();
					String brand = brands.getText().toString();
					try
					{
//						JSONObject request = new JSONObject();
//						request.put("logo", base64EncodedString);
//						request.put("companyname", coname);
//						request.put("contactname", contName);
//						request.put("email", emid);
//						request.put("cityid", CityUtilities.getCityID(cityName1));
//						request.put("address", addr);
//						request.put("poboxno", pobno);
//						request.put("landmark", lmark);
//						request.put("mobile", mob);
//						request.put("landline", landl);
//						request.put("fax", faxno);
//						request.put("customerservice", coustservice);
//						request.put("website", webs);
//						request.put("categoryid", ind);
//						request.put("profile", pro);
//						request.put("brands", brand);
//						request.put("userid",userID);
						

						List<NameValuePair> request = new ArrayList<NameValuePair>(15);
						request.add(new BasicNameValuePair("logo", base64EncodedString));
						request.add(new BasicNameValuePair("companyname", coname));
						request.add(new BasicNameValuePair("contactname", contName));
						request.add(new BasicNameValuePair("email", emid));
						request.add(new BasicNameValuePair("cityid", CityUtilities.getCityID(cityName1)));
						request.add(new BasicNameValuePair("address", addr));
						request.add(new BasicNameValuePair("poboxno", pobno));
						request.add(new BasicNameValuePair("landmark", lmark));
						request.add(new BasicNameValuePair("mobile", mob));
						request.add(new BasicNameValuePair("landline", landl));
						request.add(new BasicNameValuePair("fax", faxno));
						request.add(new BasicNameValuePair("customerservice", coustservice));
						request.add(new BasicNameValuePair("website", webs));
						request.add(new BasicNameValuePair("categoryid", ""+ind));
						request.add(new BasicNameValuePair("profile", pro));
						request.add(new BasicNameValuePair("brands", brand));
						request.add(new BasicNameValuePair("userid", userID));
						
						progressdialog.show();
						HttpConnection connect = new HttpConnection(Config.BASE_URL+Config.ADDLISTING_URL, request, ListYourBusinessActivity.this);
						Thread td = new Thread(connect);
						td.start();
					}
					catch(Exception e)
					{
						e.printStackTrace();
						Toast.makeText(getApplicationContext(),getResources().getString(R.string.logical_error),Toast.LENGTH_SHORT).show();
					}
				}
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			try
			{
				 Uri selectedImage = data.getData();
		         String[] filePathColumn = {MediaStore.Images.Media.DATA};
		         
		         Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		         cursor.moveToFirst();

		         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		         String filePath = cursor.getString(columnIndex);
		         cursor.close();

		         selectedImageBitmap = BitmapFactory.decodeFile(filePath);
		         if(selectedImageBitmap == null)
		         {
		        	 base64EncodedString = "";
				     companypic.setImageResource(R.drawable.actionbar_logo); 
		         }
		         else
		         {
			         companypic.setImageBitmap(selectedImageBitmap);
//			         new Thread(new Runnable() 
//			         {
//						@Override
//						public void run()
//						{
							Log.e("encoding ", "encoding start");
							ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
							Log.e("encoding ", "encoding ByteArrayOutputStream"); 
							selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
							Log.e("encoding ", "encoding selectedImageBitmap");
							byte[] byteArray = byteArrayOutputStream .toByteArray();
							Log.e("encoding ", "encoding byteArray");
							base64EncodedString = Base64.encodeToString(byteArray, Base64.URL_SAFE);
							Log.e("encoding ", "encoding done -> "+base64EncodedString);
//						}
//					});
		         }
		         
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Unable to set selected image please select different image.",Toast.LENGTH_SHORT).show();
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
				progressdialog.dismiss();
				if(status)
				{
					try
					{
						JSONObject resp = new JSONObject(response);
						if(resp.getString("status").equals("success"))
						{
							Toast.makeText(getApplicationContext(),"Listing Added Successfully",Toast.LENGTH_SHORT).show();
							finish();
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
	
	public String validateNumber(String number)
	{
		if(number.equals(""))
			return number;
		
		if(number.startsWith("+971"))
			return number;
		else
			return "+971"+number;
	}
	
	public String validateWebAddress(String address)
	{
		if(address.equals(""))
			return address;
		
		if(address.startsWith("http://"))
			return address;
		else
			return "http://"+address;
	}
}