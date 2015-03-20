package com.reach.tong2;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CustomActionBar {

	private Activity mContext;
	private View mView;
	private int mLayoutID;
	
	public CustomActionBar(Activity context,int layoutId){
		this.mContext = context;
		this.mLayoutID = layoutId;
		setActionBarLayout();
	}
	
	@SuppressWarnings("deprecation")
	public View setActionBarLayout() {
		ActionBar actionBar = mContext.getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflator.inflate(mLayoutID, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(mView, layout);
		}
		return mView;
	}

	
}
