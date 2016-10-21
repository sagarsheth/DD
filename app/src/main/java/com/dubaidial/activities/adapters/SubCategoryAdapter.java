package com.dubaidial.activities.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dubaidial.models.SubCategory;
import com.techpro.dubaidial.R;

public class SubCategoryAdapter extends ArrayAdapter<String> 
{
	ArrayList<SubCategory> searchresult;
	public SubCategoryAdapter(Context context, int resource,List<String> obj,ArrayList<SubCategory> searchresults) 
	{
		super(context, resource,obj);
		this.searchresult = searchresults;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View resultView = convertView;
        LayoutInflater inflater =   (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resultView = inflater.inflate(R.layout.subcat_list, null);
        
        TextView resultName = (TextView) resultView.findViewById(R.id.subcatName);  
        resultName.setTextSize(15);
        resultName.setText(searchresult.get(position).getName());
        resultName.setTextColor(Color.WHITE);
//        if(position == SearchResultActivity.position)
//        	resultName.setTextColor(Color.parseColor("#E71E24"));
        
		return resultView;
	}
}