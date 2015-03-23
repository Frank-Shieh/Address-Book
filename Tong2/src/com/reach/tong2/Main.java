package com.reach.tong2;

import java.util.ArrayList;

import databasefactory.DeleteContact;
import databasefactory.ReadContact;
import databasefactory.UpDataContact;
import databasefactory.WriteContact;
import filefactory.WriteExcel;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends FragmentActivity implements OnClickListener {

	private ImageView[] mTabButton = new ImageView[4];
	private int mPosition = 0;
	private Fragment[] mFragment = { new MainPage(), new Massage(),
			new Personal() };
	private FragmentManager mFManager;
	private TextView mTitle;
	private final String[] mTitles = { "首页", "消息", "个人中心" };

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		mFManager = getSupportFragmentManager();
		init();
	}

	private Person add() {
		Person person = new Person();
		ArrayList<String> value = new ArrayList<String>();
		value.add("12345678");
		person.addPhone(value, 0);
		person.addName("niliu");
		value = new ArrayList<String>();
		value.add("qwertyuio");
		person.addAddress(value, 0);
		value = new ArrayList<String>();
		value.add("456@163.com");
		person.addEmail(value, 0);
		DataManager.addPerson(DataManager.TEMP, person);
		return person;
	}

	private void init() {
		mTitle = (TextView) setActionBarLayout().findViewById(R.id.title);
		mTabButton[0] = (ImageView) this.findViewById(R.id.mainpage);
		mTabButton[0].setOnClickListener(this);
		mTabButton[1] = (ImageView) this.findViewById(R.id.massage);
		mTabButton[1].setOnClickListener(this);
		mTabButton[2] = (ImageView) this.findViewById(R.id.personal);
		mTabButton[2].setOnClickListener(this);
		FragmentTransaction transaction = null;
		transaction = mFManager.beginTransaction();
		for (int i = 0; i < mFragment.length; i++) {
			transaction.add(R.id.fragment, mFragment[i]);
			transaction.hide(mFragment[i]);
		}
		transaction.commit();
		setSelection(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setSelection(int index) {
		FragmentTransaction transaction = mFManager.beginTransaction();
		cleanUpStep(transaction);
		mPosition = index;
		setNextStep(transaction);
		transaction.commit();
	}

	private void cleanUpStep(FragmentTransaction transaction) {
		mTabButton[mPosition].setSelected(false);
		transaction.hide(mFragment[mPosition]);
	}

	private void setNextStep(FragmentTransaction transaction) {
		mTabButton[mPosition].setSelected(true);
		transaction.show(mFragment[mPosition]);
		mTitle.setText(mTitles[mPosition]);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.mainpage:
			setSelection(0);
			break;
		case R.id.massage:
			setSelection(1);
			break;
		case R.id.personal:
			setSelection(2);
			break;
		default:
			break;
		}
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public View setActionBarLayout() {
		ActionBar actionBar = this.getActionBar();
		View view = null;
		actionBar = this.getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		view = getLayoutInflater().inflate(R.layout.actionbar, null);
		actionBar.setCustomView(view, new ActionBar.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ActionBar.LayoutParams mP = (ActionBar.LayoutParams) view
				.getLayoutParams();
		mP.gravity = mP.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK
				| Gravity.CENTER_HORIZONTAL;

		actionBar.setCustomView(view, mP);
		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
