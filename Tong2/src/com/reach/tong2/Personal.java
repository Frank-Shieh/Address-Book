package com.reach.tong2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import customadapter.PersonalAadapter;

public class Personal extends Fragment implements OnItemClickListener {

	private ListView mList1;
	private ListView mList2;
	private String[] mList1Data = { "本地文件", "云盘文件" };
	private String[] mList2Data = { "个人资料", "关于通讯君" };
	private TextView mUserName;
	private TextView mLocalFilesCount;
	private TextView mNetFilesCount;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.personal, null);
		mUserName = (TextView) view.findViewById(R.id.username_personal);
		mLocalFilesCount = (TextView) view
				.findViewById(R.id.localfile_personal);
		mNetFilesCount = (TextView) view.findViewById(R.id.netfile_personal);
		mList1 = (ListView) view.findViewById(R.id.list1_personal);
		mList2 = (ListView) view.findViewById(R.id.list2_personal);
		mList1.setAdapter(new PersonalAadapter(this.getActivity(), mList1Data,
				R.layout.personal_list));
		mList1.setOnItemClickListener(this);
		mList2.setAdapter(new PersonalAadapter(this.getActivity(), mList2Data,
				R.layout.personal_list));
		setValueToView();
		return view;
	}

	private void setValueToView() {
		mLocalFilesCount.setText(R.string.username_personal
				+ String.valueOf(DataManager.localFiles.getFilesCount()));
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
