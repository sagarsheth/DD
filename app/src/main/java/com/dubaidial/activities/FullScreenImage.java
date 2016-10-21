package com.dubaidial.activities;

import com.dubaidial.Utils.Constants;
import com.techpro.dubaidial.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class FullScreenImage extends Activity
{
	ImageView bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreenimage);
		bg = (ImageView)findViewById(R.id.backg);
		bg.setImageBitmap(Constants.backIMG);
	}
}
