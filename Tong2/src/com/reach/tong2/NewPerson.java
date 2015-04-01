package com.reach.tong2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customadapter.NewPersonAdapter;
import customadapter.NewPersonAdapterAddress;
import customadapter.NewPersonAdapterEmail;
import customadapter.NewPersonAdapterPhone;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NewPerson extends Activity {

	private List<Map<Integer, String>> mData;
	private List<Map<Integer, Integer>> mType;
	private ListView mListPhone;
	private ListView mListEmail;
	private ListView mListAddress;
	private NewPersonAdapterPhone mAdapterPhone;
	private NewPersonAdapterEmail mAdapterEmail;
	private NewPersonAdapterAddress mAdapterAddress;
	private String PHONE = "phone";
	private String EMAIL = "email";
	private String ADDRESS = "address";
	private int mPhoneCount;
	private int mEmailCount;
	private int mAddressCount;
	private ViewGroup mBody;
	private LinearLayout mPhone;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newperson);
		mBody = (ViewGroup) this.findViewById(R.id.newperson_list_phone);
		init();
		mListPhone = (ListView) this.findViewById(R.id.newperson_list_phone);
		mListEmail = (ListView) this.findViewById(R.id.newperson_list_email);
		mListAddress = (ListView) this
				.findViewById(R.id.newperson_list_address);
		mListPhone.setAdapter(mAdapterPhone);
		mListEmail.setAdapter(mAdapterEmail);
		mListAddress.setAdapter(mAdapterAddress);
	}

	@SuppressLint("UseSparseArrays")
	private void init() {
		Map<Integer, String> temp1 = null;
		Map<Integer, Integer> temp2 = null;
		mData = new ArrayList<Map<Integer, String>>();
		temp1 = new HashMap<Integer, String>();
		temp1.put(0, "");
		mData.add(0, temp1);
		temp1 = new HashMap<Integer, String>();
		temp1.put(0, "");
		mData.add(1, temp1);
		temp1 = new HashMap<Integer, String>();
		temp1.put(0, "");
		mData.add(2, temp1);
		mType = new ArrayList<Map<Integer, Integer>>();
		temp2 = new HashMap<Integer, Integer>();
		temp2.put(0, 0);
		mType.add(0, temp2);
		temp2 = new HashMap<Integer, Integer>();
		temp2.put(0, 0);
		mType.add(1, temp2);
		temp2 = new HashMap<Integer, Integer>();
		temp2.put(0, 0);
		mType.add(2, temp2);
		mPhoneCount = 1;
		mEmailCount = 1;
		mAddressCount = 1;
		mAdapterPhone = new NewPersonAdapterPhone(this,
				R.layout.newperson_phone_list, mData, mType,
				DataManager.PHONETYPE, mPhoneCount);
		mAdapterEmail = new NewPersonAdapterEmail(this,
				R.layout.newperson_email_list, mData, mType,
				DataManager.EMAILTYPE, mEmailCount);
		mAdapterAddress = new NewPersonAdapterAddress(this,
				R.layout.newperson_address_list, mData, mType,
				DataManager.ADDRESSTYPE, mAddressCount);

	}

	public void go(View view) {
		for (int i = 0; i < mData.size(); i++) {
			for (int j = 0; j < mData.get(i).size(); j++) {
				System.out.println("data:" + i + " " + mData.get(i).get(j));
			}
		}
		for (int i = 0; i < mType.size(); i++) {
			for (int j = 0; j < mType.get(i).size(); j++) {
				System.out.println("type:"+i + " " + mType.get(i).get(j));
			}
		}

	}

}
