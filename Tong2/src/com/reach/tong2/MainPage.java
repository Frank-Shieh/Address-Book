package com.reach.tong2;

import customadapter.MainPageAdapter;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainPage extends Fragment implements OnClickListener {

	private ListView mLocalContact;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mainpage, null);
		mLocalContact = (ListView) view.findViewById(R.id.localcontact);
		mLocalContact.setAdapter(new MainPageAdapter(getActivity()));
//		setActionBarLayout(R.layout.actionbar);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("deprecation")
	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActivity().getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) getActivity()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

}
