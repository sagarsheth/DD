package com.dubaidial.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dubaidial.models.SaveddataModel;
import com.dubaidial.storage.StoreData;
import com.techpro.dubaidial.R;

public class SplashActivity extends Activity 
{
	protected boolean _active = true;
    protected int _splashTime = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
        Thread splashTread = new Thread() 
	    {
        	@Override
		    public void run() 
		    {
        		try 
		        {
        			int waited = 0;
		            while(_active && (waited < _splashTime)) 
		            {
		            	sleep(100);
		                if(_active) 
		                	waited += 100;
		            }
		        }
        		catch(InterruptedException e) 
        		{}
        		finally 
        		{
        			StoreData save = new StoreData(SplashActivity.this);
					String userID = save.getSharedPreference(SaveddataModel.FILENAME,SaveddataModel.UserID_key);
					if(userID.equals(""))
					{
						Intent in1 = new Intent(SplashActivity.this,SignInActivity.class);
        				startActivity(in1);
					}
					else
					{
						Intent in1 = new Intent(SplashActivity.this,MainActivity.class);
        				startActivity(in1);
					}
        			finish();
        		}
		    }
	    };
	    splashTread.start();
	}
}
