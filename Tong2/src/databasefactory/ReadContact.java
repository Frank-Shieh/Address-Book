package databasefactory;

import java.io.InputStream;

import java.util.ArrayList;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class ReadContact {

	private Context mContext;
	private ContentResolver mResolver;
	private Cursor mCursorID;
	private Cursor mCursorPerson;
	private Person mPerson;
	private int mID;
	private Uri mHeadPhotoUri;
	private InputStream mInputStream;
	private static String EMAIL_V2 = "vnd.android.cursor.item/email_v2";
	private static String IM = "vnd.android.cursor.item/im";
	private static String NICKNAME = "vnd.android.cursor.item/nickname";
	private static String ORGANIZATION = "vnd.android.cursor.item/organization";
	private static String PHONE_V2 = "vnd.android.cursor.item/phone_v2";
	private static String SIP_ADDRESS = "vnd.android.cursor.item/sip_address";
	private static String NAME = "vnd.android.cursor.item/name";
	private static String POSTALADDRESS_V2 = "vnd.android.cursor.item/postal-address_v2";
	private static String IDENTITY = "vnd.android.cursor.item/identity";
	private static String PHOTO = "vnd.android.cursor.item/photo";
	private static String GROUP_MENMBERSHIP = "vnd.android.cursor.item/group_membership";

	public ReadContact() {

	}

	public ReadContact(Context context) {
		this.mContext = context;
	}

	public void getContact() {
		mResolver = mContext.getContentResolver();
		String[] contactsID = { ContactsContract.Contacts._ID };
		mCursorID = mResolver.query(ContactsContract.RawContacts.CONTENT_URI,
				contactsID, null, null, null);
		getPersonById();
	}

	private void getPersonById() {
		while (mCursorID.moveToNext()) {
			mPerson = new Person();
			mID = mCursorID.getInt(0);
			String[] projection = { ContactsContract.Data.DATA1,
					ContactsContract.Data.DATA2, ContactsContract.Data.MIMETYPE };
			String selection = "raw_contact_id=?";
			String[] selectionArgs = { String.valueOf(mID) };
			mCursorPerson = mResolver.query(ContactsContract.Data.CONTENT_URI,
					projection, selection, selectionArgs, null);
			getDataByPerson();
			if (mPerson.mIsFull)
				DataManager.addPerson(DataManager.LOCAL, mPerson);
		}
	}

	private void getDataByPerson() {
		while (mCursorPerson.moveToNext()) {
			mPerson.mIsFull = true;
			String data = mCursorPerson.getString(mCursorPerson
					.getColumnIndex("data1"));
			String mimeType = mCursorPerson.getString(mCursorPerson
					.getColumnIndex("mimetype"));
			int type = mCursorPerson.getInt(mCursorPerson
					.getColumnIndex("data2"));
			ArrayList<String> target = null;
			int index = 0;
			switch (checkType(mimeType)) {
			case 1:
				index = DataManager.getIndex(1, type);
				target = mPerson.getEmail(index);
				if (target == null) {
					target = new ArrayList<String>();
					target.add(data);
					mPerson.addEmail(target, index);
				}
				else
				target.add(data);
				target = null;
				break;
			case 5:
				index = DataManager.getIndex(5, type);
				target = mPerson.getPhone(index);
				if (target == null) {
					target = new ArrayList<String>();
					target.add(data);
					mPerson.addPhone(target, index);
				}
				else
				target.add(data);
				target = null;
				break;
			case 7:
				mPerson.addName(data);
				break;
			case 8:
				index = DataManager.getIndex(8, type);
				target = mPerson.getPostAddress(index);
				if (target == null) {
					target = new ArrayList<String>();
					target.add(data);
					mPerson.addAddress(target, index);
				}
				else
				target.add(data);
				target = null;
				break;
			case 10:
				getHeadPhoto();
				break;
			default:
				break;
			}
		}
	}

	private int checkType(String type) {
		if (type.equals(EMAIL_V2))
			return 1;
		if (type.equals(PHONE_V2))
			return 5;
		if (type.equals(NAME))
			return 7;
		if (type.equals(POSTALADDRESS_V2))
			return 8;
		if (type.equals(PHOTO))
			return 10;
		return 0;
	}

	private void getHeadPhoto() {
		Bitmap temp = null;
		mHeadPhotoUri = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, mID);
		mInputStream = ContactsContract.Contacts.openContactPhotoInputStream(
				mResolver, mHeadPhotoUri);
		temp = BitmapFactory.decodeStream(mInputStream);
		mPerson.addHeadPhoto(temp);
	}

}
