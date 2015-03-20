package databasefactory;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.reach.tong2.Person;

public class DeleteContact extends ContactContral{

	private Person mPerson;

	public DeleteContact(Context context, Person person) {
		super(context);
		this.mPerson = person;
		deletePerson();
	}

	private void deletePerson() {
		String name = mPerson.getName();
		Uri uri = null;
		mCursor = mResolver.query(uriRawContact,
				new String[] { _ID},
				displayName + "=?",
				new String[] { name }, null);
		if(mCursor.moveToFirst()){
					int id = mCursor.getInt(0);
		uri = ContentUris.withAppendedId(
				uriRawContact, id);
		Uri newUri = uri
				.buildUpon()
				.appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER,
						"true").build();
		mResolver.delete(newUri, null, null);
		}
		Log.i("delete", "success");
	}
}
