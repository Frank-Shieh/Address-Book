package com.reach.tong2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import datacontrol.Delete;
import user.User;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import filefactory.LocalFiles;

public class DataManager {

	public static ArrayList<Person> mListExcel;
	private static ArrayList<Person> mListLocal = new ArrayList<Person>();
	public static ArrayList<Person> mListTemp;
	public static HashMap<String, Integer> chToMath = new HashMap<String, Integer>();
	public static String excelStorePath;
	public static final String[] PHONETYPE = { "移动号码", "家庭号码", "其他号码" };
	public static final String[] EMAILTYPE = { "家庭邮件", "工作邮件","其他邮件" };
	public static final String[] ADDRESSTYPE = { "家庭地址", "工作地址", "其他地址" };
	public static Person targetPerson;
	public static LocalFiles localFiles;
	public static User user;
	public static String userpath;
	private static Bitmap head;
	public static String APPPath;
	public static String targetFile;
	public static String persentfilename;
	
	public static void frist() {
		File temp;
		excelStorePath = Environment.getExternalStorageDirectory().toString();
		temp = new File(excelStorePath + "/Tong");
		if (!temp.exists())
			temp.mkdirs();
		excelStorePath = temp.getAbsolutePath();
		Log.i("apppath", APPPath);
		targetFile = APPPath + "/tamplate.xls";
		init();
	}

	@SuppressLint("SdCardPath")
	public static void init() {
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
		localFiles = new LocalFiles();
		if (user != null) {
			userpath = "/data/data/com.reach.tong2/user_" + user.getUsername();
			File file = new File(userpath);
			if (file.exists())
				setHeadphoto(userpath + "/headphoto_" + user.getUsername()
						+ ".png");
		}
	}

	public static ArrayList<Person> addParent(int model) {
		switch (model) {
		case RequestCode.SrcCode.SRC_EXCEL:
			return mListExcel;
		case RequestCode.SrcCode.SRC_LOCAL:
			return mListLocal;
		case RequestCode.SrcCode.SRC_TEMP:
			return mListTemp;
		}
		return null;
	}

	public static ArrayList<Person> deleteParent(int model) {
		switch (model) {
		case RequestCode.SrcCode.SRC_EXCEL:
			return mListExcel;
		case RequestCode.SrcCode.SRC_LOCAL:
			return mListLocal;
		case RequestCode.SrcCode.SRC_TEMP:
			return mListTemp;
		}
		return null;
	}

	public static ArrayList<Person> getAllPerson(int model) {
		switch (model) {
		case RequestCode.SrcCode.SRC_EXCEL:
			return mListExcel;
		case RequestCode.SrcCode.SRC_LOCAL:
			return mListLocal;
		case RequestCode.SrcCode.SRC_TEMP:
			return mListTemp;
		default:
			return null;
		}
	}

	public static class TargetPerson {
		public static ArrayList<String> mData;
		public static ArrayList<String> mType;
	}

	public class FamilyType {
		public static final int FAMILY_PHONE = 1;
		public static final int FAMILY_EMAIL = 2;
		public static final int FAMILY_ADDRESS = 3;
	}

	public class MimeType {
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

	public class RequestCode {
		public class SrcCode {
			public static final String REQUESTCODE_SRC = "requsecode_src";
			public static final int SRC_EXCEL = 1;
			public static final int SRC_LOCAL = 2;
			public static final int SRC_TEMP = 3;
		}

		public class DstCode {
			public static final String REQUESTCODE_DST = "requsecode_dst";
			public static final int DST_EXCEL = 1;
			public static final int DST_LOCAL = 2;
			public static final int DST_TEMP = 3;
		}
		
	}

	public class ActionCode {
		public static final String ACTIONCODE = "actioncode";
		public static final int DELETE = 1;
		public static final int ADD = 2;
		public static final int VISIT = 0;
		public static final int EDIT = 3;
	}

	// public class Model {
	// public static final int EXCEL = 1;
	// public static final int LOCAL = 2;
	// public static final int TEMP = 3;
	// }

	// 设置和获取头像
	public static void setHeadphoto(String headphoto) {
		head = BitmapFactory.decodeFile(headphoto);
	}

	public static void setHeadphoto(Bitmap headphoto) {
		head = headphoto;
	}

	public static Bitmap getHeadphoto() {
		return head;
	}

	// public static int getPersonNumber(){
	// return list.size();
	// }
}
