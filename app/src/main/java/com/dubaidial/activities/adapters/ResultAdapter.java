package com.dubaidial.activities.adapters;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dubaidial.models.SearchResultModel;
import com.techpro.dubaidial.R;

public class ResultAdapter extends ArrayAdapter<String> 
{
//	ImageView resultImage;
	ArrayList<SearchResultModel> searchresult;
	public ResultAdapter(Context context, int resource,List<String> obj,ArrayList<SearchResultModel> searchresults) 
	{
		super(context, resource,obj);
		this.searchresult = searchresults;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View resultView = convertView;
        LayoutInflater inflater =   (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resultView = inflater.inflate(R.layout.adapter_searchresult, null);
        TextView resultName = (TextView) resultView.findViewById(R.id.result_name);
        TextView resultAddress = (TextView) resultView.findViewById(R.id.result_address);
        TextView details_phone = (TextView) resultView.findViewById(R.id.details_phone);
        TextView fax = (TextView) resultView.findViewById(R.id.fax);
        TextView mobile = (TextView) resultView.findViewById(R.id.mobile);
        TextView resultDistance = (TextView) resultView.findViewById(R.id.result_Distance);
        TextView website = (TextView) resultView.findViewById(R.id.details_site);
        ImageView resultRating1 = (ImageView) resultView.findViewById(R.id.result_rating1);
        ImageView resultRating2 = (ImageView) resultView.findViewById(R.id.result_rating2);
        ImageView resultRating3 = (ImageView) resultView.findViewById(R.id.result_rating3);
        ImageView resultRating4 = (ImageView) resultView.findViewById(R.id.result_rating4);
        ImageView resultRating5 = (ImageView) resultView.findViewById(R.id.result_rating5);
        ImageView resultImage = (ImageView) resultView.findViewById(R.id.result_img);
        
        resultName.setText(searchresult.get(position).getName());
        resultAddress.setText(searchresult.get(position).getAddress());
        String mob = searchresult.get(position).getContact_number();
        resultDistance.setText(" "+searchresult.get(position).getDistance());
		if(mob.equals(""))
			details_phone.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.get(position).getRegional_code()+""+mob;
			if(number.startsWith("+"))
				details_phone.setText(searchresult.get(position).getRegional_code()+""+mob);
			else
				details_phone.setText(" +"+searchresult.get(position).getRegional_code()+""+mob);
		}

        String mob1 = searchresult.get(position).getMobile();
		if(mob1.equals(""))
			mobile.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.get(position).getRegional_code()+""+mob1;
			if(number.startsWith("+"))
				mobile.setText(searchresult.get(position).getRegional_code()+""+mob1);
			else
				mobile.setText(" +"+searchresult.get(position).getRegional_code()+""+mob1);
		}
		
		String faxn = searchresult.get(position).getFax_number().trim();
		if(faxn.equals(""))
			fax.setVisibility(View.GONE);
		else
		{
			String number  = searchresult.get(position).getRegional_code()+""+faxn;
			if(number.startsWith("+"))
				fax.setText(searchresult.get(position).getRegional_code()+""+faxn);
			else
				fax.setText("+"+searchresult.get(position).getRegional_code()+""+faxn);
		}
		
		String web = searchresult.get(position).getWebsite();
		if(web.equals(""))
			website.setVisibility(View.GONE);
		else
			website.setText(" "+web);
		
        String rate = searchresult.get(position).getAverage_ratings();
        try
        {
	        if(rate != null)
	        {
//	        	int rateing = Float.parseInt(rate);
	        	if(rate.startsWith("1"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_small_active);
	        	}
	        	else if(rate.startsWith("2"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("3"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("4"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating4.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        	else if(rate.startsWith("5"))
	        	{
	        		resultRating1.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating2.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating3.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating4.setImageResource(R.drawable.icon_star_small_active);
	        		resultRating5.setImageResource(R.drawable.icon_star_small_active);	        		
	        	}
	        }
	        
	        String imagePath = searchresult.get(position).getCompany_logo();
	        if(imagePath != null)
	        {
	        	if(!imagePath.equals(""))
	        	{
	        	    new DownloadImage(resultImage).execute(imagePath);
	        	}
	        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
//        resultName.setText(searchresult.get(position).getName());
		return resultView;
	}
	
//	private void setImage(Drawable drawable)
//	{
//		resultImage.setImageDrawable(drawable);
//	}
	public class DownloadImage extends AsyncTask<String, Integer, Drawable> {
ImageView imgv;
	    public DownloadImage(ImageView resultImage) {
imgv = resultImage;
		}

		@Override
	    protected Drawable doInBackground(String... arg0) {
	        // This is done in a background thread
	        return downloadImage(arg0[0]);
	    }

	    /**
	     * Called after the image has been downloaded
	     * -> this calls a function on the main thread again
	     */
	    protected void onPostExecute(Drawable image)
	    {
	    	imgv.setImageDrawable(image);
	    }


	    /**
	     * Actually download the Image from the _url
	     * @param _url
	     * @return
	     */
	    private Drawable downloadImage(String _url)
	    {
	        //Prepare to download image
	        URL url;        
	        BufferedOutputStream out;
	        InputStream in;
	        BufferedInputStream buf;

	        //BufferedInputStream buf;
	        try {
	            url = new URL(_url);
	            in = url.openStream();

	            /*
	             * THIS IS NOT NEEDED
	             * 
	             * YOU TRY TO CREATE AN ACTUAL IMAGE HERE, BY WRITING
	             * TO A NEW FILE
	             * YOU ONLY NEED TO READ THE INPUTSTREAM 
	             * AND CONVERT THAT TO A BITMAP
	            out = new BufferedOutputStream(new FileOutputStream("testImage.jpg"));
	            int i;

	             while ((i = in.read()) != -1) {
	                 out.write(i);
	             }
	             out.close();
	             in.close();
	             */

	            // Read the inputstream 
	            buf = new BufferedInputStream(in);

	            // Convert the BufferedInputStream to a Bitmap
	            Bitmap bMap = BitmapFactory.decodeStream(buf);
	            if (in != null) {
	                in.close();
	            }
	            if (buf != null) {
	                buf.close();
	            }

	            return new BitmapDrawable(bMap);

	        } catch (Exception e) {
	            Log.e("Error reading file", e.toString());
	        }

	        return null;
	    }
	}
}