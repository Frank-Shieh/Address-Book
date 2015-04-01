package databasefactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

import datacontrol.Add;
import datatypetransation.Transation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts.Photo;
import android.util.Log;

public class ReadContact {

	private Context mContext;
	private ContentResolver mResolver;
	private Cursor mCursorID;
	private Cursor mCursorPerson;
	private Person mPerson;
	private int mID;
	private Uri mHeadPhotoUri;
	private InputStream mInputStream;
	private Add mAdd = new Add();

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
			if (mPerson.mIsFull) {
				mAdd.add(DataManager.RequestCode.SrcCode.SRC_LOCAL, mPerson);
				mPerson.mIsFull = false;
			}

		}
	}

	private void getDataByPerson() {
		boolean temp1 = false;
		boolean temp2 = false;
		Transation temp = null;
		while (mCursorPerson.moveToNext()) {
			String data = mCursorPerson.getString(mCursorPerson
					.getColumnIndex("data1"));
			String mimeType = mCursorPerson.getString(mCursorPerson
					.getColumnIndex("mimetype"));
			int data2 = mCursorPerson.getInt(mCursorPerson
					.getColumnIndex("data2"));
			ArrayList<String> target = null;
			temp = new Transation(mimeType, data2);
			if (temp.getFamily() == 10) {
				getHeadPhoto();
			} else if (temp.getFamily() == 7) {
				mPerson.addName(data);
				temp1 = true;
			} else {
				target = mPerson.getData(temp.getFamily(), temp.getType());
				if (target == null) {
					target = new ArrayList<String>();
					target.add(data);
					mPerson.addData(temp.getFamily(), temp.getType(), target);
				} else
					target.add(data);
				temp2 = true;
				target = null;
			}
		}
		mPerson.mIsFull = temp1 && temp2;
	}

	private void getHeadPhoto() {
		Bitmap temp = null;
		mHeadPhotoUri = ContentUris.withAppendedId(
				ContactsContract.Contacts.CONTENT_URI, mID);
		System.out.println(mHeadPhotoUri.toString());
		mInputStream = ContactsContract.Contacts.openContactPhotoInputStream(
				mResolver, mHeadPhotoUri);
		temp = BitmapFactory.decodeStream(mInputStream);
		mPerson.addHeadPhoto(temp);

	}
}
