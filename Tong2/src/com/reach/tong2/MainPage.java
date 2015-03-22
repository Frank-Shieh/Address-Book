package com.reach.tong2;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import customadapter.MainPageAdapter;

public class MainPage extends Fragment implements OnItemClickListener {

	private ListView mLocalContact;
	private ArrayList<Person> mPerson = DataManager.getAllPerson(DataManager.LOCAL);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mainpage, null);
		mLocalContact = (ListView) view.findViewById(R.id.localcontact);
		mLocalContact.setAdapter(new MainPageAdapter(getActivity(), mPerson));
		mLocalContact.setOnItemClickListener(this);
		return view;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.reach.tong2.PersonalInfo");
		DataManager.targetPerson = mPerson.get(position);
		startActivity(intent);
	}

}
