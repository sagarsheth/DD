package com.dubaidial.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StoreData 
{
	Activity activity;
	public StoreData(Activity activity)
	{
		this.activity = activity;
	}
	
	public void setSharedPrefrence(String fileName,String Data,String key) 
	{
		SharedPreferences  sh_Pref = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		Editor toEdit = sh_Pref.edit();
	    toEdit.putString(key, Data);
	    toEdit.commit();
	}
	
	public String getSharedPreference(String fileName,String key) 
	{
		SharedPreferences  sh_Pref = activity.getSharedPreferences(fileName, Context.MODE_PRIVATE);
		return sh_Pref.getString(key, "");
	}
}