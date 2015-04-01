package com.reach.tong2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import customadapter.PersonalInfoAdapter;
import datacontrol.Delete;

public class PersonalInfo extends Activity implements OnItemClickListener,
		OnClickListener {

	private Person mPerson;
	private TextView mName;
	private ImageView mHeadphoto;
	private ListView mList;
	private PersonalInfoAdapter mAdapter;
	private int mRequestCodeSrc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalinfo);
		mPerson = DataManager.targetPerson;
		mRequestCodeSrc = getIntent().getIntExtra(
				DataManager.RequestCode.SrcCode.REQUESTCODE_SRC, 0);
		init();
	}

	private void init() {
		mName = (TextView) this.findViewById(R.id.name_info);
		mHeadphoto = (ImageView) this.findViewById(R.id.headphoto_info);
		setValueToView();
		mList = (ListView) this.findViewById(R.id.list_info);
		mAdapter = new PersonalInfoAdapter(this, mPerson);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(this);
	}

	private void setValueToView() {
		mName.setText(DataManager.targetPerson.getName());
		mHeadphoto.setImageBitmap(DataManager.targetPerson.getHeadPhoto());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		setValueToView();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (DataManager.TargetPerson.mType.get(position).matches(".*号码")) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:"
					+ DataManager.TargetPerson.mData.get(position)));
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater temp = getMenuInflater();
		temp.inflate(R.menu.personalinfo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item_personalinfo_edit:
			Intent intent = new Intent("com.reach.tong2.PersonalInfoChange");
			startActivityForResult(intent, 1);
			return true;
		case R.id.item_personalinfo_delete:
			showDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("注意");
		dialog.setMessage("确认要删除此联系人吗？");
		dialog.setPositiveButton("是", this);// -1
		dialog.setNegativeButton("否", this);// -2
		dialog.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Delete temp = new Delete();
		switch (which) {
		case -1:
			temp.delete(mRequestCodeSrc, mPerson);
			temp.upData(this, mRequestCodeSrc, mPerson);
			this.setResult(mRequestCodeSrc);
			finish();
			break;
		case -2:
			break;
		}
	}

}
