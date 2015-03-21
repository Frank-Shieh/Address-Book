package com.reach.tong2;

import customadapter.PersonalAadapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Personal extends Fragment {

	private ListView mList1;
	private ListView mList2;
	private String[] mList1Data = {"本地文件","云盘文件"};
	private String[] mList2Data = {"个人资料","关于通讯君"};
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.personal, null);
		mList1 = (ListView) view.findViewById(R.id.list1);
		mList2 = (ListView) view.findViewById(R.id.list2);
		mList1.setAdapter(new PersonalAadapter(this.getActivity(), mList1Data, R.layout.personal_list));
		mList2.setAdapter(new PersonalAadapter(this.getActivity(), mList2Data, R.layout.personal_list));
		return view;
	}

}
