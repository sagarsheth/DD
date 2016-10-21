package com.dubaidial.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dubaidial.listeners.CategorySelectedListener;
import com.techpro.dubaidial.R;

public class MenuActivity extends FragmentActivity implements CategorySelectedListener
{
//	private CategorySelectedListener1 catListener11;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	    super.onCreate(savedInstanceState);
		try{
			
		    mSlideoutHelper = new SlideoutHelper(this);
//		    catListener11 = (CategorySelectedListener1) mSlideoutHelper.mActivity;
		    mSlideoutHelper.activate();
		    getSupportFragmentManager().beginTransaction().add(R.id.slideout_placeholder, new MenuFragment(), "menu").commit();
		    mSlideoutHelper.open();
			}catch(Exception e)
			{
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),"Sub-Category list not available for this category.",Toast.LENGTH_SHORT).show();
			}
		
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mSlideoutHelper.close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	public SlideoutHelper getSlideoutHelper(){
		return mSlideoutHelper;
	}
	
	private SlideoutHelper mSlideoutHelper;

	@Override
	public void onItemClick(int position) {
		try{
			if(position == 999999)
				mSlideoutHelper.close();
			else
			{
				SearchResultActivity.isFromFragment = true;
				SearchResultActivity.position = position;
				mSlideoutHelper.close();
			}
//		catListener11.onItemClick1(position);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
