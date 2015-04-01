package com.reach.tong2;

import databasefactory.DeleteContact;
import databasefactory.ReadContact;
import databasefactory.UpDataContact;
import databasefactory.WriteContact;
import filefactory.ReadExcel;
import filefactory.WriteExcel;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends FragmentActivity implements OnClickListener {

	private ImageView[] mTabButton = new ImageView[4];
	private int mPosition = 0;
	private Fragment[] mFragment = { new MainPage(), new Massage(),
			new Personal() };
	private FragmentManager mFManager;
	private TextView mTitle;
	private String[] mTitleData = { "", "消息", "个人中心" };

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main);
		mFManager = getSupportFragmentManager();
		init();
	}


	private void init() {
		mTitleData = getResources().getStringArray(R.array.title_main);
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
		mTitle.setText(mTitleData[mPosition]);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
		if(mPosition != 0){
			menu.clear();
		}
		else
			getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.mainpage_menu_create:
			
			break;
		case R.id.mainpage_menu_edit:
			intent = new Intent("com.reach.tong2.BatchSelect");
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_LOCAL);
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.DELETE);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			break;
		case R.id.mainpage_menu_output:
			showSubDialog();
			break;
		case R.id.mainpage_menu_input:
			intent = new Intent("com.reach.tong2.LocalFile");
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.ADD);
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_EXCEL);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_LOCAL);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showSubDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(
				R.string.mainpage_dialog_entername));
		View temp = View.inflate(this,
				R.layout.mainpage_dialog_entername, null);
		final EditText target = (EditText) temp.findViewById(R.id.mainpage_dialog_entername);
		dialog.setView(temp);
		dialog.setPositiveButton(
				getResources().getString(R.string.mainpage_dialog_yesbutton),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String filename = target.getText().toString();
						filename += ".xls";
						WriteExcel temp = new WriteExcel(
								DataManager.RequestCode.SrcCode.SRC_LOCAL,filename);
						temp.writeFile();
					}

				});
		dialog.setNegativeButton(
				getResources().getString(R.string.mainpage_dialog_nobutton),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
		dialog.create().show();
	}
	
}
