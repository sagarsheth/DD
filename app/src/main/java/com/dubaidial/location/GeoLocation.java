package com.dubaidial.location;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.dubaidial.activities.MainActivity;

public class GeoLocation extends Service implements LocationListener
{
	
	private LocationManager locationManager;
	private String bestProvider;
//	private MyLocationListener locationlistener;
 
	@Override
	public void onCreate() 
	{
		try
		{
			super.onCreate();
			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			List<String> providers = locationManager.getAllProviders();
			for (String provider : providers) 
			{
				printProvider(provider);
			}
			Criteria criteria = new Criteria();
			bestProvider = locationManager.getBestProvider(criteria, false);
			printProvider(bestProvider);
			Location location = locationManager.getLastKnownLocation(bestProvider);
			printLocation(location);
			locationManager.requestLocationUpdates(bestProvider, 20000, 1, this);
		}
		catch (Exception e) 
		{
			System.out.println("----> "+e.toString());
		}
	}

	public void onLocationChanged(Location location) 
	{
		printLocation(location);
	}

	public void onProviderDisabled(String provider) {}

	public void onProviderEnabled(String provider) {}

	public void onStatusChanged(String provider, int status, Bundle extras) {}

	private void printProvider(String provider){}

	private void printLocation(Location location)
	{
		if (location == null)
		{
//			SendMultiPartSms(SharedData.Number_current,"MobiPro:Location-GPS is not enabled on that phone.");
		}
		else
		{
			try
			{
				String locn =location.toString();
				Log.e("index--> ", "add --> "+locn);
				String[] locat = locn.split(" ")[1].split(",");
				MainActivity.latitude = locat[0];
				MainActivity.longitude = locat[1];
				MainActivity.areaName = ConvertPointToLocation(Double.parseDouble(MainActivity.latitude), Double.parseDouble(MainActivity.latitude));
            	Log.e("index--> "+MainActivity.latitude, "add --> "+MainActivity.longitude);
			}
			catch (Exception e) 
			{
				Log.e("LOC error", e.toString());
			}	
		}
	}

	
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

    public String ConvertPointToLocation(double lat,double lon)
    {   
        String address = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try 
        {
            List<Address> addresses = geoCoder.getFromLocation(lat/1E6,lon/1E6,1);
            if (addresses.size() > 0) 
            {
                for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
                {
                	Log.e("index--> "+index, "add --> "+addresses.get(0).getAddressLine(index));
                    address += addresses.get(0).getAddressLine(index) + " ";
                }
            }
        }
        catch (IOException e) {                
            e.printStackTrace();
        }   
        Log.e("your address is", "------------->"+address);
        return address;
    }
}