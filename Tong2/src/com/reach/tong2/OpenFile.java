package com.reach.tong2;

import java.util.ArrayList;

import customadapter.MainPageAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OpenFile extends Activity implements OnItemClickListener{

	private ListView mList;
	private MainPageAdapter mAdapter;
	private ArrayList<Person> mPerson;
	
//	private 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openfile);
		mList = (ListView) this.findViewById(R.id.excelcontact);
		mList.setOnItemClickListener(this);
		mPerson = DataManager.getAllPerson(DataManager.EXCEL);
		mAdapter = new MainPageAdapter(this, mPerson);
		mList.setAdapter(mAdapter);
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
