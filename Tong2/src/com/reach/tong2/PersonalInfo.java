package com.reach.tong2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalInfo extends Activity {

	private Person mPerson;
	private TextView mName;
	private ImageView mHeadphoto;
	private ListView mPhoneList;
	private ListView mEmailList;
	private ListView mAddressList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactinfo);
		Intent intent = getIntent();
		mPerson = (Person) intent.getBundleExtra("target").get("target");
	}
	
	private void init(){
		mName  = (TextView) this.findViewById(R.id.name_info);
		mHeadphoto = (ImageView) this.findViewById(R.id.headphoto_info);
		mName.setText(mPerson.getName());
		mHeadphoto.setImageBitmap(mPerson.getHeadPhoto());
		mPhoneList = (ListView) this.findViewById(R.id.phonelist_info);
		mEmailList = (ListView) this.findViewById(R.id.emaillist_info);
		mAddressList = (ListView) this.findViewById(R.id.addresslist_info);
		
	}
	
}
