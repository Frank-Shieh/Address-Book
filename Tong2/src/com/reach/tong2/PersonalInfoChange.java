package com.reach.tong2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PersonalInfoChange extends Activity {

	private Person mPerson;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalinfochange);
		mPerson = DataManager.targetPerson;
	}
	
	public void go(View view){
		mPerson.getPhone(0).add("9876");
		mPerson.getPhone(0).get(0);
		mPerson.addName("ÀèÃ÷");
		DataManager.targetPerson = mPerson;
		finish();
	}
	
	
}
