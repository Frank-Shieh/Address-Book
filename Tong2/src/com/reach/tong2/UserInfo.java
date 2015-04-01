package com.reach.tong2;

import customadapter.UserInfoAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UserInfo extends Activity implements OnItemClickListener{

	private String[] mItem = new String[4];
	private String[] mValue = new String[4];
	private ListView list1;
	private ListView list2;
	private UserInfoAdapter mAdapter1;
	private UserInfoAdapter mAdapter2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		list1 = (ListView) this.findViewById(R.id.list1_userinfo);
		list1.setOnItemClickListener(this);
		mAdapter1 = new UserInfoAdapter(this, R.layout.userinfo_list1, null, null);
		list1.setAdapter(mAdapter1);
		list2 = (ListView) this.findViewById(R.id.list2_userinfo);
		list2.setOnItemClickListener(this);
		init();
		mAdapter2 = new UserInfoAdapter(this, R.layout.userinfo_list2, mItem, mValue);
		list2.setAdapter(mAdapter2);
	}

	private void init(){
		mValue[0] = DataManager.user.getUsername();
		mValue[1] = DataManager.user.getPhone();
		mValue[2] = DataManager.user.getEmail();
		mValue[3] = DataManager.user.getSex();
		mItem[0] = "用户名";
		mItem[1] = "电话号码";
		mItem[2] = "电子邮件";
		mItem[3] = "性别";
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
