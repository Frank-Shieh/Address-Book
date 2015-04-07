package com.reach.tong2;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import customadapter.LocalFileAdapter;
import filefactory.FileRead;
import filefactory.ReadExcel;

public class LocalFile extends Activity implements OnItemClickListener {

	private ListView mList;
	private LocalFileAdapter mLocalFileAdapter;
	private File[] mFiles;
	private int mActionCode;
	private int mRequestCodeSrc;
	private int mRequestCodeDst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localfile);
		mList = (ListView) this.findViewById(R.id.list_localfile);
		mFiles = DataManager.localFiles.getFilesName();
		mLocalFileAdapter = new LocalFileAdapter(this, mFiles);
		mList.setAdapter(mLocalFileAdapter);
		mList.setOnItemClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getCode();
	}

	private void getCode() {
		Intent intent = getIntent();
		mActionCode = intent.getIntExtra(DataManager.ActionCode.ACTIONCODE, 0);
		mRequestCodeSrc = intent.getIntExtra(
				DataManager.RequestCode.SrcCode.REQUESTCODE_SRC, 0);
		mRequestCodeDst = intent.getIntExtra(
				DataManager.RequestCode.DstCode.REQUESTCODE_DST, 0);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		DataManager.persentfilename = mFiles[position].getName();
		System.out.println(DataManager.persentfilename);
		FileRead temp = new ReadExcel(DataManager.persentfilename);
		temp.readFile();
		Intent intent = null;
		switch (mActionCode) {
		case DataManager.ActionCode.VISIT:
			intent = new Intent("com.reach.tong2.OpenFile");
			startActivity(intent);
			break;
		case DataManager.ActionCode.ADD:
			intent = new Intent("com.reach.tong2.BatchSelect");
			intent.putExtra(DataManager.ActionCode.ACTIONCODE, mActionCode);
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					mRequestCodeSrc);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					mRequestCodeDst);
			startActivityForResult(intent, mRequestCodeSrc);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		setResult(mRequestCodeSrc);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
