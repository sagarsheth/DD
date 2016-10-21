package com.dubaidial.activities.header;

import com.techpro.dubaidial.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HeaderActivity extends Activity
{
	public ImageView headerHome;
	public TextView headerText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.header_layout);
		
		headerHome = (ImageView)findViewById(R.id.header_home);
		headerText = (TextView)findViewById(R.id.header_text);
	}
}
