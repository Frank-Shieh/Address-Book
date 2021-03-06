package com.reach.tong2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import customadapter.PersonalAadapter;

public class Personal extends Fragment implements OnItemClickListener {

	private ListView mList1;
	private ListView mList2;
	private String[] mList1Data;
	private String[] mList2Data;
	private TextView mUserName;
	private TextView mLocalFilesCount;
	private TextView mNetFilesCount;
	private ImageView mHeadPhoto;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.personal, null);
		mUserName = (TextView) view.findViewById(R.id.username_personal);
		mHeadPhoto = (ImageView) view.findViewById(R.id.headphoto);
		mLocalFilesCount = (TextView) view
				.findViewById(R.id.localfile_personal);
		mNetFilesCount = (TextView) view.findViewById(R.id.netfile_personal);
		mList1Data = getResources().getStringArray(R.array.personal_list1);
		mList2Data = getResources().getStringArray(R.array.personal_list2);
		mList1 = (ListView) view.findViewById(R.id.list1_personal);
		mList2 = (ListView) view.findViewById(R.id.list2_personal);
		mList1.setAdapter(new PersonalAadapter(this.getActivity(), mList1Data,
				R.layout.personal_list));
		mList1.setOnItemClickListener(this);
		mList2.setAdapter(new PersonalAadapter(this.getActivity(), mList2Data,
				R.layout.personal_list));
		mList2.setOnItemClickListener(this);
		setValueToView();
		return view;
	}

	private void setValueToView() {
		if(DataManager.localFiles == null)
			Log.i("file", "is null");
		mLocalFilesCount.setText(this.getResources().getText(R.string.personal_localfilescount)
				+ String.valueOf(DataManager.localFiles.getFilesCount()));
		if(DataManager.user == null){
			Log.i("file", "is null");
			return;
		}
		mUserName.setText(this.getResources().getText(R.string.personal_username)+DataManager.user.getUsername());
		mHeadPhoto.setImageBitmap(DataManager.getHeadphoto());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.list1_personal:
			switch (position) {
			case 0:
				Intent intent = new Intent("com.reach.tong2.LocalFile");
				startActivity(intent);
				break;
			case 1:
				break;
			default:
				break;
			}
			break;
		case R.id.list2_personal:
			switch (position) {
			case 0:
				Intent intent = new Intent("com.reach.tong2.UserInfo");
				startActivity(intent);
				break;
			case 1:
				break;
			default:
				break;
			}
		default:
			break;
		}

	}

}
