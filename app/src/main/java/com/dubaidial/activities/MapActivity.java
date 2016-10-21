package com.dubaidial.activities;

import com.dubaidial.Utils.CityUtilities;
import com.dubaidial.models.SearchResultModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techpro.dubaidial.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MapActivity extends Activity
{
	private SearchResultModel searchresult;
	private GoogleMap mapView;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);

		searchresult = (SearchResultModel) getIntent().getExtras().getSerializable("resultdata");
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
	}
}
