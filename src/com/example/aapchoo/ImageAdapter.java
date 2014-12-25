package com.example.aapchoo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {
	
	private Context mContext;
	private int numCells; 
	
	public ImageAdapter(Context c)
	{
		mContext=c;		
	}
	
	public void setCount(int num)
	{
		numCells=num;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return numCells;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
		System.out.println("Adapter called!!");
		if (convertView==null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(120,120));
			imageView.setBackgroundColor(Color.rgb(255, 255, 255));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setPadding(2,2,2,2);
		}
		else {
			imageView = (ImageView)convertView;
		}
		//imageView.setImageResource(R.drawable.sample_0);
		//imageView.setImageResource(R.drawable.sample_2);
		return imageView;
	}
	
}
