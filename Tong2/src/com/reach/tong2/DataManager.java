package com.reach.tong2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;

public class DataManager {

	private static ArrayList<Person> mListExcel = new ArrayList<Person>();
	private static ArrayList<Person> mListLocal = new ArrayList<Person>();
	private static ArrayList<Person> mListTemp = new ArrayList<Person>();
	public static HashMap<String, Integer> chToMath = new HashMap<String, Integer>();
	public static HashMap<String, Integer> phonePosition = new HashMap<String, Integer>();
	public static HashMap<String, Integer> emailPosition = new HashMap<String, Integer>();
	public static HashMap<String, Integer> addressPosition = new HashMap<String, Integer>();
	public static String excelStorePath;
	public static final int EXCEL = 1;
	public static final int LOCAL = 2;
	public static final int TEMP = 3;
	public static final String[] PHONETYPE = { "移动号码", "家庭号码", "其他号码" };
	public static final String[] EMAILTYPE = { "家庭邮件", "工作邮件" };
	public static final String[] ADDRESSTYPE = { "家庭地址", "工作地址", "其他地址" };

	static {
		chToMath.put(PHONETYPE[0],
				ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		chToMath.put(PHONETYPE[1],
				ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
		chToMath.put(PHONETYPE[2],
				ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
		chToMath.put(EMAILTYPE[0],
				ContactsContract.CommonDataKinds.Email.TYPE_HOME);
		chToMath.put(EMAILTYPE[1],
				ContactsContract.CommonDataKinds.Email.TYPE_WORK);
		chToMath.put(ADDRESSTYPE[0],
				ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME);
		chToMath.put(ADDRESSTYPE[1],
				ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK);
		chToMath.put(ADDRESSTYPE[2],
				ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM);
		for (int i = 0; i < PHONETYPE.length; i++) {
			phonePosition.put(PHONETYPE[i], i);
		}
		for(int i = 0;i<EMAILTYPE.length;i++){
			emailPosition.put(EMAILTYPE[i], i);
		}
		for(int i = 0;i<ADDRESSTYPE.length;i++){
			addressPosition.put(ADDRESSTYPE[i], i);
		}
		
		File temp;
		excelStorePath = Environment.getExternalStorageDirectory().toString();
		temp = new File(excelStorePath+"/Tong");
		temp.mkdirs();
		excelStorePath = temp.toString();
		Log.i("path", excelStorePath);
		
	}

	public static void addPerson(int model, Person person) {
		switch (model) {
		case EXCEL:
			mListExcel.add(person);
			break;
		case LOCAL:
			mListLocal.add(person);
			break;
		case TEMP:
			mListTemp.add(person);
		default:
			break;
		}
	}

	public static ArrayList<Person> getAllPerson(int model) {
		switch (model) {
		case EXCEL:
			return mListExcel;
		case LOCAL:
			return mListLocal;
		case TEMP:
			return mListTemp;
		default:
			return null;
		}
	}

	public static int getIndex(int type,int type1){
		switch (type) {
		case 1:
			switch (type1) {
			case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
				return emailPosition.get("家庭邮件");
			case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
				return emailPosition.get("工作邮件");
			}
		case 5:
			switch (type1) {
			case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
				return phonePosition.get("移动号码");
			case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
				return phonePosition.get("家庭号码");
			case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
				return phonePosition.get("其他号码");
			}
		case 8:
			switch (type1) {
			case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
				return addressPosition.get("家庭地址");
			case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
				return addressPosition.get("工作地址");
			case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM:
				return addressPosition.get("其他地址");
			}
		}
		return -1;
	}
	
	
	public class MimeType{
		public static final String EMAIL_V2 = "vnd.android.cursor.item/email_v2";
		public static final String IM = "vnd.android.cursor.item/im";
		public static final String NICKNAME = "vnd.android.cursor.item/nickname";
		public static final String ORGANIZATION = "vnd.android.cursor.item/organization";
		public static final String PHONE_V2 = "vnd.android.cursor.item/phone_v2";
		public static final String SIP_ADDRESS = "vnd.android.cursor.item/sip_address";
		public static final String NAME = "vnd.android.cursor.item/name";
		public static final String POSTALADDRESS_V2 = "vnd.android.cursor.item/postal-address_v2";
		public static final String IDENTITY = "vnd.android.cursor.item/identity";
		public static final String PHOTO = "vnd.android.cursor.item/photo";
		public static final String GROUP_MENMBERSHIP = "vnd.android.cursor.item/group_membership";

	}
	// public static int getPersonNumber(){
	// return list.size();
	// }
}
