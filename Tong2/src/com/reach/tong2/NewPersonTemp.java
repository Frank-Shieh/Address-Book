package com.reach.tong2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class NewPersonTemp extends Activity {

	
	private ViewGroup mListPhone;
	private ViewGroup mListEmail;
	private ViewGroup mListAddress;
	private FrameLayout mTemp;
	private LinearLayout mTemp1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newperson);
		mListPhone = (ViewGroup) this.findViewById(R.id.newperson_list_phone);
		mListEmail = (ViewGroup) this.findViewById(R.id.newperson_list_email);
		mListAddress = (ViewGroup) this.findViewById(R.id.newperson_list_address);
		
	}
	
	public void go(View view){
		mListPhone.addView(createView(), mListPhone.getChildCount());
	}
	
	private View createView(){
		mTemp =  (FrameLayout) View.inflate(this, R.layout.newperson_phone_list, null);
		return mTemp;
	}
	
}
