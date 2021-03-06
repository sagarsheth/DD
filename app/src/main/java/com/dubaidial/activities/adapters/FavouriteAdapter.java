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

import com.dubaidial.models.FavouriteModel;
import com.dubaidial.models.SearchResultModel;
import com.techpro.dubaidial.R;

public class FavouriteAdapter extends ArrayAdapter<String> 
{
	ArrayList<SearchResultModel> searchfavourite;
	public FavouriteAdapter(Context context, int resource,List<String> obj,ArrayList<SearchResultModel> searchfavourites) 
	{
		super(context, resource,obj);
		this.searchfavourite = searchfavourites;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View favouriteView = convertView;
        LayoutInflater inflater =   (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        favouriteView = inflater.inflate(R.layout.adapter_favourite, null);
        
        TextView favouriteName = (TextView) favouriteView.findViewById(R.id.favourite_name);
        TextView favouriteAddress = (TextView) favouriteView.findViewById(R.id.favourite_address);
        ImageView favouriteImage = (ImageView) favouriteView.findViewById(R.id.favourite_img);
        
        favouriteName.setText(searchfavourite.get(position).getName());
        favouriteAddress.setText(searchfavourite.get(position).getAddress());
        String imagePath = searchfavourite.get(position).getCompany_logo();
        if(imagePath != null)
        {
        	if(!imagePath.equals(""))
        	{
        	    new DownloadImage(favouriteImage).execute(imagePath);
        	}
        }
		return favouriteView;
	}
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
