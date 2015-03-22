package com.reach.tong2;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import customadapter.LocalFileAdapter;
import filefactory.FileRead;
import filefactory.ReadExcel;

public class LocalFile extends Activity implements OnItemClickListener{

	private ListView mList;
	private LocalFileAdapter mLocalFileAdapter;
	private File[] mFiles;
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
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		FileRead temp = new ReadExcel(mFiles[position].getAbsolutePath());
		temp.readFile();
		Log.i("localfile", "in");
		Intent intent = new Intent("com.reach.tong2.OpenFile" );
		startActivity(intent);
		
	}
}
