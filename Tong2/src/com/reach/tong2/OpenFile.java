package com.reach.tong2;

import java.util.ArrayList;

import customadapter.MainPageAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class OpenFile extends Activity implements OnItemClickListener,
		OnItemLongClickListener, OnClickListener {

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
		mList.setOnItemLongClickListener(this);
		mPerson = DataManager
				.getAllPerson(DataManager.RequestCode.SrcCode.SRC_EXCEL);
		mAdapter = new MainPageAdapter(this, mPerson);
		mList.setAdapter(mAdapter);
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
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		showDialog();
		return true;
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("«Î—°‘Ò“ªœÓ");
		String[] temp = getResources().getStringArray(
				R.array.openfile_dialog_show);
		dialog.setItems(temp, this);
		dialog.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		Intent intent = null;
		switch (which) {
		case 0:
			break;
		case 1:
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
		case 2:
			intent = new Intent("com.reach.tong2.ChoiseSources");
//			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
//					DataManager.RequestCode.SrcCode.SRC_EXCEL);
//			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
//					DataManager.RequestCode.DstCode.DST_LOCAL);
			startActivityForResult(intent, DataManager.RequestCode.SrcCode.SRC_EXCEL);
			break;
		}
	}

}
