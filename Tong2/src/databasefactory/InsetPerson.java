package databasefactory;

import java.io.ByteArrayOutputStream;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.util.Log;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

public class InsetPerson {

	private ContentResolver mResolver;
	private ContentValues mValues;
	private int mID;
	private Uri uri = ContactsContract.Data.CONTENT_URI;
	
	public InsetPerson(Person target, ContentResolver resolver,int id){
		mValues = new ContentValues();
		mResolver = resolver;
		mID = id;
		insetName(target);
		insetPhone(target);
		insetEmail(target);
		insetAddress(target);
		insetHeadPhoto(target);
	}
	
	private void insetName(Person target) {
		// 添加姓名
		mValues.put("raw_contact_id", mID);
		mValues.put(ContactsContract.Data.MIMETYPE, DataManager.MimeType.NAME);
		mValues.put("data1", target.getName());
		mValues.put("data2", target.getName());
		mResolver.insert(uri, mValues);
		mValues.clear();
	}

	private void insetPhone(Person target) {
		// 添加电话
		for (int i = 0; i < DataManager.PHONETYPE.length; i++) {
			if (target.getPhone(i) != null) {
				for (int j = 0; j < target.getPhone(i).size(); j++) {
					mValues.put("raw_contact_id", mID);
					mValues.put(ContactsContract.Data.MIMETYPE,
							DataManager.MimeType.PHONE_V2);
					mValues.put("data1", target.getPhone(i).get(j));
					mValues.put("data2",
							DataManager.chToMath.get(DataManager.PHONETYPE[i]));
					mResolver.insert(uri, mValues);
					Log.i("phone", target.getPhone(i).get(j));
					mValues.clear();
				}
			}
		}
		Log.i("phone inset", "success");
	}

	private void insetEmail(Person target) {
		//添加邮件
		for (int i = 0; i < DataManager.EMAILTYPE.length; i++) {
			if (target.getEmail(i) != null) {
				for (int j = 0; j < target.getEmail(i).size(); j++) {
					mValues.put(ContactsContract.Data.MIMETYPE,
							DataManager.MimeType.EMAIL_V2);
					mValues.put("raw_contact_id", mID);
					mValues.put("data1", target.getEmail(i).get(j));
					mValues.put("data2",
							DataManager.chToMath.get(DataManager.EMAILTYPE[i]));
					mResolver.insert(uri, mValues);
					mValues.clear();
				}
			}
		}
	}

	private void insetAddress(Person target){
		//添加地址
		for (int i = 0; i < DataManager.ADDRESSTYPE.length; i++) {
			if (target.getPostAddress(i) != null) {
				for (int j = 0; j < target.getPostAddress(i).size(); j++) {
					mValues.put(ContactsContract.Data.MIMETYPE,
							DataManager.MimeType.POSTALADDRESS_V2);
					mValues.put("raw_contact_id", mID);
					mValues.put("data1", target.getPostAddress(i).get(j));
					mValues.put("data2",
							DataManager.chToMath.get(DataManager.ADDRESSTYPE[i]));
					mResolver.insert(uri, mValues);
					mValues.clear();
				}
			}
		}
	}

	private void insetHeadPhoto(Person target){
		//添加头像
		mValues.put("raw_contact_id", mID);
		mValues.put("data14", 1);
		mValues.put(ContactsContract.Data.MIMETYPE, DataManager.MimeType.PHOTO);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Bitmap temp = target.getHeadPhoto();
		temp.compress(Bitmap.CompressFormat.PNG, 100, os);
		mValues.put(Photo.PHOTO, os.toByteArray());
		mResolver.insert(uri, mValues);
		mValues.clear();
	}
}
