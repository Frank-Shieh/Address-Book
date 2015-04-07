package com.reach.tong2;

import java.util.ArrayList;
import java.util.HashMap;

import databasefactory.UpDataContact;
import datacontrol.Add;
import filefactory.UpDataExcel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class NewPerson extends Activity implements OnClickListener,
		DialogInterface.OnClickListener {

	private Person mPerson;
	private ImageView mHeadPhoto = null;
	private EditText mName = null;
	private Button mAddPhone = null;
	private Button mAdEmail = null;
	private Button mAdAddress = null;
	private LinearLayout mPhoneLayout;
	private LinearLayout mEmailLayout;
	private LinearLayout mAdressLayout;
	private int mRequestCode;
	private int mActionCode;
	private static final int FROMIMAGE = -1;
	private static final int FROMCCAMERA = -2;
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private String[] mHint;
	private Add mAdd = new Add();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newperson);
		mHeadPhoto = (ImageView) findViewById(R.id.newperson_headphoto);
		mName = (EditText) findViewById(R.id.newperson_name);
		mAddPhone = (Button) findViewById(R.id.newperson_button_addphone);
		mAdEmail = (Button) findViewById(R.id.newperson_button_addeamil);
		mAdAddress = (Button) findViewById(R.id.newperson_button_addaddress);
		mPhoneLayout = (LinearLayout) findViewById(R.id.newperson_context_phone);
		mEmailLayout = (LinearLayout) findViewById(R.id.newperson_context_eamil);
		mAdressLayout = (LinearLayout) findViewById(R.id.newperson_context_address);
		mHint = getResources().getStringArray(R.array.newperson_hint);
		getCode();
		setTitle();
		init();
		mHeadPhoto.setOnClickListener(this);
		mAddPhone.setOnClickListener(this);
		mAdEmail.setOnClickListener(this);
		mAdAddress.setOnClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private void setTitle() {
		switch (mActionCode) {
		case DataManager.ActionCode.ADD:
			getActionBar().setTitle(
					getResources().getString(R.string.newperson_title_create));
			mPerson = new Person();
			DataManager.mListTemp = new ArrayList<Person>();
			DataManager.mListTemp.add(mPerson);
			break;
		case DataManager.ActionCode.EDIT:
			getActionBar().setTitle(
					getResources().getString(R.string.newperson_title_edit));
			mPerson = DataManager.targetPerson;
			break;
		}
	}

	private void getCode() {
		mActionCode = getIntent().getIntExtra(
				DataManager.ActionCode.ACTIONCODE, 0);
		mRequestCode = getIntent().getIntExtra(
				DataManager.RequestCode.SrcCode.REQUESTCODE_SRC, 0);
	}

	private View createView(String type, String[] mSpinnerData) {
		LinearLayout temp = new LinearLayout(this);
		temp = (LinearLayout) View.inflate(this, R.layout.newperson_view_item,
				null);
		EditText editText = (EditText) temp
				.findViewById(R.id.newperson_edittext_data);
		editText.setHint(type);
		Spinner spinner = (Spinner) temp
				.findViewById(R.id.newperson_spinner_type);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				R.layout.newperson_layout_spinner, mSpinnerData);
		// 绑定 Adapter到控件
		spinner.setAdapter(spinnerAdapter);
		return temp;
	}

	private View createView(String data, int type, String[] mSpinnerData) {
		LinearLayout temp = new LinearLayout(this);
		temp = (LinearLayout) View.inflate(this, R.layout.newperson_view_item,
				null);
		EditText editText = (EditText) temp
				.findViewById(R.id.newperson_edittext_data);
		editText.setText(data);
		Spinner spinner = (Spinner) temp
				.findViewById(R.id.newperson_spinner_type);
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				R.layout.newperson_layout_spinner, mSpinnerData);
		// 绑定 Adapter到控件
		spinner.setAdapter(spinnerAdapter);
		spinner.setSelection(type);
		return temp;
	}

	private void init() {
		if (mPerson.getHeadPhoto() != null)
			mHeadPhoto.setImageBitmap(mPerson.getHeadPhoto());
		mName.setText(mPerson.getName());
		loopData(DataManager.FamilyType.FAMILY_PHONE, DataManager.PHONETYPE,
				mPhoneLayout);
		loopData(DataManager.FamilyType.FAMILY_EMAIL, DataManager.EMAILTYPE,
				mEmailLayout);
		loopData(DataManager.FamilyType.FAMILY_ADDRESS,
				DataManager.ADDRESSTYPE, mAdressLayout);
		// mPhoneLayout.addView(createView("请输入号码", DataManager.PHONETYPE), 0);
		// mEmailLayout.addView(createView("请输入邮箱", DataManager.EMAILTYPE), 0);
		// mAdressLayout.addView(createView("请输入地址", DataManager.ADDRESSTYPE),
		// 0);
	}

	private void loopData(int family, String[] mSpinnerData, LinearLayout target) {
		int index = 0;
		boolean hasData = false;
		for (int i = 0; i < mSpinnerData.length; i++) {
			if (mPerson.getData(family, i) == null)
				continue;
			for (int j = 0; j < mPerson.getData(family, i).size(); j++) {
				target.addView(
						createView(mPerson.getData(family, i).get(j), i,
								mSpinnerData), index++);
				hasData = true;
			}
		}
		if(!hasData)
			target.addView(createView(mHint[family-1], mSpinnerData), 0);
	}

	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, ArrayList<String>> getContextData(
			LinearLayout context) {
		HashMap<Integer, ArrayList<String>> target = new HashMap<Integer, ArrayList<String>>();
		LinearLayout tempLayout = null;
		EditText tempText = null;
		Spinner tempSpinner = null;
		int a = 0;
		String b = null;
		for (int i = 0; i < context.getChildCount(); i++) {
			tempLayout = (LinearLayout) context.getChildAt(i);
			tempText = (EditText) tempLayout
					.findViewById(R.id.newperson_edittext_data);
			tempSpinner = (Spinner) tempLayout
					.findViewById(R.id.newperson_spinner_type);
			a = tempSpinner.getSelectedItemPosition();
			ArrayList<String> tempList = target.get(tempSpinner
					.getSelectedItemPosition());
			b = tempText.getText().toString();
			System.out.println("i= " + i + " a = " + a + " b = " + b);
			if (b.equals("") || b == null)
				continue;
			if (tempList == null) {
				tempList = new ArrayList<String>();
				tempList.add(tempText.getText().toString());
				target.put(tempSpinner.getSelectedItemPosition(), tempList);
			} else {
				tempList.add(tempText.getText().toString());
			}
		}
		return target;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.newperson_button_addphone:
			mPhoneLayout.addView(createView("请输入号码", DataManager.PHONETYPE),
					mPhoneLayout.getChildCount());
			break;
		case R.id.newperson_button_addeamil:
			mEmailLayout.addView(createView("请输入邮箱", DataManager.EMAILTYPE),
					mEmailLayout.getChildCount());
			break;
		case R.id.newperson_button_addaddress:
			mAdressLayout.addView(createView("请输入地址", DataManager.ADDRESSTYPE),
					mAdressLayout.getChildCount());
			break;
		case R.id.newperson_headphoto:
			showdialog();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.newperson, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.newperson_menu_done:
			getData();
			setResult(mRequestCode);
			upDataCache();
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	private void upDataCache(){
		switch (mActionCode) {
		case DataManager.ActionCode.ADD:
			mAdd.add(mRequestCode, mPerson);
			mAdd.upData(this, mRequestCode);
			break;
		case DataManager.ActionCode.EDIT:
			switch (mRequestCode) {
			case DataManager.RequestCode.SrcCode.SRC_EXCEL:
				System.out.println(DataManager.persentfilename);
//				UpDataExcel temp = new UpDataExcel(UpDataExcel.CHANGE, DataManager.persentfilename);
				break;
			case DataManager.RequestCode.SrcCode.SRC_LOCAL:
				UpDataContact temp1 = new UpDataContact(this, mPerson);
			default:
				break;
			}
			break;
		}
	}
	
	private void getData() {
		mPerson.addName(mName.getText().toString());
		// mPerson.addHeadPhoto(headPhoto);
		mPerson.addAllFamliyData(DataManager.FamilyType.FAMILY_PHONE,
				getContextData(mPhoneLayout));
		mPerson.addAllFamliyData(DataManager.FamilyType.FAMILY_EMAIL,
				getContextData(mEmailLayout));
		mPerson.addAllFamliyData(DataManager.FamilyType.FAMILY_ADDRESS,
				getContextData(mAdressLayout));
	}

	private void showdialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("设置头像");
		dialog.setMessage("请选择图片源");
		dialog.setPositiveButton("照片", this);// -1
		dialog.setNegativeButton("相机", this);// -2
		dialog.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (which) {
		case FROMIMAGE:
			intent = new Intent(this, Headphotoget.class);
			intent.putExtra("code", IMAGE_REQUEST_CODE);
			intent.putExtra("from", FROMIMAGE);
			startActivityForResult(intent, 1);
			break;
		case FROMCCAMERA:
			intent = new Intent(this, Headphotoget.class);
			intent.putExtra("code", CAMERA_REQUEST_CODE);
			intent.putExtra("from", FROMCCAMERA);
			startActivityForResult(intent, 2);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		mPerson.addHeadPhoto(DataManager.getHeadphoto());
		mHeadPhoto.setImageBitmap(DataManager.getHeadphoto());
		super.onActivityResult(requestCode, resultCode, data);
	}
}
