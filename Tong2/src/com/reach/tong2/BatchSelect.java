package com.reach.tong2;

import java.util.ArrayList;

import customadapter.BatchSelectAdapter;
import datacontrol.Add;
import datacontrol.Delete;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;

public class BatchSelect extends Activity implements OnClickListener,
		DialogInterface.OnClickListener {

	private CheckBox mCheckBox;
	private ListView mList;
	private ArrayList<Person> mPersons;
	private int mRequestCodeSrc;
	private int mRequestCodeDst;
	private int mActionCode;
	private BatchSelectAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.batchselect);
		getRequestCode();
		mCheckBox = (CheckBox) this.findViewById(R.id.checkbox_batchselect);
		mCheckBox.setOnClickListener(this);
		mList = (ListView) this.findViewById(R.id.list_batchselect);
		myAdapter = new BatchSelectAdapter(this, mPersons);
		mList.setAdapter(myAdapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private void getRequestCode() {
		Intent intent = getIntent();
		mRequestCodeSrc = intent.getIntExtra(
				DataManager.RequestCode.SrcCode.REQUESTCODE_SRC, 0);
		mRequestCodeDst = intent.getIntExtra(
				DataManager.RequestCode.DstCode.REQUESTCODE_DST, 0);

		mActionCode = intent.getIntExtra(DataManager.ActionCode.ACTIONCODE, 0);
		mPersons = DataManager.getAllPerson(mRequestCodeDst);
	}

	@Override
	public void onClick(View v) {
		if (mCheckBox.isChecked()) {
			for (int i = 0; i < mPersons.size(); i++) {
				mPersons.get(i).mIsFull = true;
			}
		} else {
			for (int i = 0; i < mPersons.size(); i++) {
				mPersons.get(i).mIsFull = false;
			}
		}
		myAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater temp = getMenuInflater();
		switch (mActionCode) {
		case DataManager.ActionCode.ADD:
			temp.inflate(R.menu.batchadd, menu);
			break;
		case DataManager.ActionCode.DELETE:
			temp.inflate(R.menu.batchdelete, menu);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ArrayList<Person> target = new ArrayList<Person>();

		for (int i = 0; i < mPersons.size(); i++) {
			if (mPersons.get(i).mIsFull) {
				target.add(mPersons.get(i));
			}
		}
		DataManager.mListTemp = target;
		switch (item.getItemId()) {
		case R.id.add_bacthadd:
			showDialog();
			break;
		case R.id.delete_batchdelete:
			showDialog();
			break;
		case android.R.id.home:
			finish();
			return true;
		}
		return true;
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("注意");
		switch (mActionCode) {
		case DataManager.ActionCode.ADD:
			dialog.setMessage("确认要添加吗？");
			break;
		case DataManager.ActionCode.DELETE:
			dialog.setMessage("确认要删除吗？");
			break;
		default:
			break;
		}
		dialog.setPositiveButton("是", this);// -1
		dialog.setNegativeButton("否", this);// -2
		dialog.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		switch (which) {
		case -1:
			switch (mActionCode) {
			case DataManager.ActionCode.ADD:
				Add temp1 = new Add();
				temp1.add(mRequestCodeSrc, DataManager.mListTemp);
				temp1.upData(this, mRequestCodeSrc);
				break;
			case DataManager.ActionCode.DELETE:
				Delete temp2 = new Delete();
				temp2.delete(mRequestCodeSrc, DataManager.mListTemp);
				temp2.upData(this, mRequestCodeSrc, DataManager.mListTemp);
				break;
			default:
				break;
			}
			setResult(mRequestCodeSrc);
			finish();
			break;
		case -2:
			break;
		default:
			break;
		}

	}

}
