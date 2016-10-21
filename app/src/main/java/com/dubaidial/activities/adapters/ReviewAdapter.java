package com.dubaidial.activities.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubaidial.models.ReviewDataModel;
import com.techpro.dubaidial.R;

public class ReviewAdapter extends ArrayAdapter<String> 
{
	ArrayList<ReviewDataModel> searchreview;
	public ReviewAdapter(Context context, int resource,List<String> obj,ArrayList<ReviewDataModel> searchreviews) 
	{
		super(context, resource,obj);
		this.searchreview = searchreviews;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View reviewView = convertView;
        LayoutInflater inflater =   (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        reviewView = inflater.inflate(R.layout.adapter_reviewlist, null);
        TextView Name = (TextView) reviewView.findViewById(R.id.review_name);
        TextView review_date = (TextView) reviewView.findViewById(R.id.review_date);
        TextView review_details = (TextView) reviewView.findViewById(R.id.review_details);
        
        ImageView reviewRating1 = (ImageView) reviewView.findViewById(R.id.review_rating_adapter1);
        ImageView reviewRating2 = (ImageView) reviewView.findViewById(R.id.review_rating_adapter2);
        ImageView reviewRating3 = (ImageView) reviewView.findViewById(R.id.review_rating_adapter3);
        ImageView reviewRating4 = (ImageView) reviewView.findViewById(R.id.review_rating_adapter4);
        ImageView reviewRating5 = (ImageView) reviewView.findViewById(R.id.review_rating_adapter5);
        
        Name.setText(searchreview.get(position).getName()+"  ");
        review_date.setText(searchreview.get(position).getCreateDate()+" -   ");
        review_details.setText(" "+searchreview.get(position).getUserComment());
        String rate = searchreview.get(position).getRatings();
        try
        {
	        if(rate != null)
	        {
//	        	int rateing = Integer.parseInt(rate);
	        	if(rate.startsWith("1"))
	        	{
	        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
	        	}
	        	else if(rate.startsWith("2"))
	        	{
	        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("3"))
	        	{
	        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("4"))
	        	{
	        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating4.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("5"))
	        	{
	        		reviewRating1.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating2.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating3.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating4.setImageResource(R.drawable.icon_star_small_active);
	        		reviewRating5.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return reviewView;
	}
}