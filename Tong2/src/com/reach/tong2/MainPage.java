package com.reach.tong2;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import customadapter.MainPageAdapter;
import filefactory.WriteExcel;

public class MainPage extends Fragment implements OnItemClickListener,
		View.OnClickListener, OnMenuItemClickListener {

	private ListView mLocalContact;
	private ArrayList<Person> mPerson = DataManager
			.getAllPerson(DataManager.RequestCode.SrcCode.SRC_LOCAL);
	private MainPageAdapter mAdapter;
	private ImageView mMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mainpage, null);
		mMenu = (ImageView) view.findViewById(R.id.mainpage_menu);
		createPopUpWindow();
		mLocalContact = (ListView) view.findViewById(R.id.localcontact);
		mAdapter = new MainPageAdapter(getActivity(), mPerson);
		mLocalContact.setAdapter(mAdapter);
		mLocalContact.setOnItemClickListener(this);
		return view;
	}

	private void createPopUpWindow() {
		mMenu.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("com.reach.tong2.PersonalInfo");
		DataManager.targetPerson = mPerson.get(position);
		intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
				DataManager.RequestCode.SrcCode.SRC_LOCAL);
		startActivityForResult(intent,
				DataManager.RequestCode.SrcCode.SRC_LOCAL);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case DataManager.RequestCode.SrcCode.SRC_LOCAL:
			mAdapter.notifyDataSetChanged();
			break;
		}
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
		dialog.setTitle(getResources().getString(
				R.string.mainpage_dialog_entername));
		View temp = View.inflate(this.getActivity(),
				R.layout.mainpage_dialog_entername, null);
		final EditText target = (EditText) temp
				.findViewById(R.id.mainpage_dialog_entername);
		dialog.setView(temp);
		dialog.setPositiveButton(
				getResources().getString(R.string.mainpage_dialog_yesbutton),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String filename = target.getText().toString();
						filename += ".xls";
						WriteExcel temp = new WriteExcel(
								DataManager.RequestCode.SrcCode.SRC_LOCAL,
								filename);
						temp.writeFile();
					}
				});
		dialog.setNegativeButton(
				getResources().getString(R.string.mainpage_dialog_nobutton),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
		dialog.create().show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		PopupMenu temp = new PopupMenu(getActivity(), v);
		temp.getMenuInflater().inflate(R.menu.main, temp.getMenu());
		temp.setOnMenuItemClickListener(this);
		temp.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.mainpage_menu_create:
			intent = new Intent("com.reach.tong2.NewPerson");
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.ADD);
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			startActivityForResult(intent,DataManager.RequestCode.SrcCode.SRC_LOCAL);
			break;
		case R.id.mainpage_menu_edit:
			intent = new Intent("com.reach.tong2.BatchSelect");
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_LOCAL);
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.DELETE);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			break;
		case R.id.mainpage_menu_output:
			showDialog();
			break;
		case R.id.mainpage_menu_input:
			intent = new Intent("com.reach.tong2.LocalFile");
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.ADD);
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_EXCEL);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			break;
		}

		return true;
	}

}
