package com.dubaidial.activities.adapters;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.techpro.dubaidial.R;

public class ViewPagerAdapter extends PagerAdapter implements OnPageChangeListener{

	Activity activity;
	List<Bitmap> imageArray;
	LayoutInflater mInflater;
	ImageView imageView;
	public ViewPagerAdapter(Activity act, List<Bitmap> imgArra) {
		imageArray = imgArra;
		activity = act;
		mInflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return imageArray.size();
	}
	
	

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public Object instantiateItem(View collection, int position) {
/*		ImageView view = new ImageView(activity);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,	LayoutParams.FILL_PARENT));
		view.setScaleType(ScaleType.FIT_XY);
		view.setBackgroundResource(imageArray[position]);
		((ViewPager) collection).addView(view, 0);*/
		
		

	    View layout = mInflater.inflate(R.layout.tp_slider, null);
	    imageView = (ImageView)layout.findViewById(R.id.ivMainImage);
//	    TextView evaluationSummary = (TextView) layout.findViewById(R.id.evaluation_summary);
	    imageView.setImageBitmap(imageArray.get(position));
//	    Drawable drawable = activity.getResources().getDrawable(R.drawable.actionbar_logo);
//	    String imagePath = imageArray.get(position);
//		Log.e("iiii	", position+" "+imagePath);
//		 if(imagePath != null)
//	        {
//	        	if(!imagePath.equals(""))
//	        	{
//	        	    new DownloadImage().execute(imagePath);
//	        	}
//	        }
//	    Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
	    
	    
	    
	    
	    
	    ((ViewPager) collection).addView(layout);
		
		return layout;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		/*Drawable drawable = new BitmapDrawable(getResources(),bitmap);
		imageView.setImageDrawable(drawable);*/
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void setImage(Drawable drawable)
	{
		imageView.setImageDrawable(drawable);
	}
	public class DownloadImage extends AsyncTask<String, Integer, Drawable> {

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
	        setImage(image);
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
