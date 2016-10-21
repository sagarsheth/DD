package com.dubaidial.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dubaidial.activities.adapters.SubCategoryAdapter;
import com.dubaidial.listeners.CategorySelectedListener;
import com.techpro.dubaidial.R;

public class MenuFragment extends Fragment 
{
	private CategorySelectedListener catListener;
	private ListView subCatList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.drawer_layout, container, false);
		subCatList = (ListView)view.findViewById(R.id.subCatList);	
		LinearLayout mainmenuy = (LinearLayout)view.findViewById(R.id.mainmenuy);	
		catListener = (CategorySelectedListener) this.getActivity();
		subCatList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				catListener.onItemClick(position);
			}
		});
		subCatList.setOnTouchListener(new OnSwipeTouchListener(this.getActivity())
		{
		    public void onSwipeTop() {
//		        Toast.makeText(SearchResultActivity.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    		
//		        Toast.makeText(SearchResultActivity.this, "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		    	catListener.onItemClick(999999);
//		        Toast.makeText(SearchResultActivity.this, "left", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeBottom() {
//		        Toast.makeText(SearchResultActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		    }

		public boolean onTouch(View v, MotionEvent event) {
			subCatList.onTouchEvent(event);
		    return gestureDetector.onTouchEvent(event);
		}
		});
		mainmenuy.setOnTouchListener(new OnSwipeTouchListener(this.getActivity())
		{
		    public void onSwipeTop() {
//		        Toast.makeText(SearchResultActivity.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    		
//		        Toast.makeText(SearchResultActivity.this, "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		    	catListener.onItemClick(999999);
//		        Toast.makeText(SearchResultActivity.this, "left", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeBottom() {
//		        Toast.makeText(SearchResultActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		    }

		public boolean onTouch(View v, MotionEvent event) {
		    return gestureDetector.onTouchEvent(event);
		}
		});
		try{
		subCatList.setAdapter(new SubCategoryAdapter(this.getActivity(), R.layout.subcat_list, SearchResultActivity.subCategoryName, SearchResultActivity.subCategory));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return view;
//		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
