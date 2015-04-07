package com.reach.tong2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Main extends FragmentActivity implements OnClickListener {

	private ImageView[] mTabButton = new ImageView[4];
	private int mPosition = 0;
	private Fragment[] mFragment = { new MainPage(), new Massage(),
			new Personal() };
	private FragmentManager mFManager;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		mFManager = getSupportFragmentManager();
		init();
	}

	private void init() {
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

}
