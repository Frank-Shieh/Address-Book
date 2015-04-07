package com.reach.tong2;

import java.util.ArrayList;

import customadapter.MainPageAdapter;
import filefactory.UpDataExcel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OpenFile extends Activity implements OnItemClickListener{

	private ListView mList;
	private MainPageAdapter mAdapter;
	private ArrayList<Person> mPerson;

	// private
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openfile);
		mList = (ListView) this.findViewById(R.id.excelcontact);
		mList.setOnItemClickListener(this);
		mPerson = DataManager
				.getAllPerson(DataManager.RequestCode.SrcCode.SRC_EXCEL);
		mAdapter = new MainPageAdapter(this, mPerson);
		mList.setAdapter(mAdapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(DataManager.persentfilename);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.reach.tong2.PersonalInfo");
		DataManager.targetPerson = mPerson.get(position);
		intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
				DataManager.RequestCode.SrcCode.SRC_EXCEL);
		startActivityForResult(intent,
				DataManager.RequestCode.SrcCode.SRC_EXCEL);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case DataManager.RequestCode.SrcCode.SRC_EXCEL:
			mAdapter.notifyDataSetChanged();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.openfile, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case android.R.id.home:
			UpDataExcel temp = new UpDataExcel(UpDataExcel.CHANGE, DataManager.persentfilename);
			finish();
			return true;
		case R.id.openfile_menu_create:
			intent = new Intent("com.reach.tong2.NewPerson");
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.ADD);
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			startActivityForResult(intent,DataManager.RequestCode.SrcCode.SRC_EXCEL);
			break;
		case R.id.openfile_menu_edit:
			intent = new Intent("com.reach.tong2.BatchSelect");
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_EXCEL);
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.DELETE);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			break;
		case R.id.openfile_menu_input:
			intent = new Intent("com.reach.tong2.ChoiseSources");
			// intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
			// DataManager.RequestCode.SrcCode.SRC_EXCEL);
			// intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
			// DataManager.RequestCode.DstCode.DST_LOCAL);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
