package databasefactory;

import com.reach.tong2.Person;

import android.content.Context;
import android.provider.ContactsContract;

public class UpDataContact extends ContactContral {

	private Person mPerson;
	private int mID;

	public UpDataContact(Context context, Person person) {
		super(context);
		this.mPerson = person;
		upData();
	}

	private void upData() {
		String name = mPerson.getName();
		mCursor = mResolver.query(uriRawContact, new String[] { _ID },
				displayName + "=?", new String[] { name }, null);
		deleteAll();
		new InsetPerson(mPerson, mResolver, mID);
	}

	private void deleteAll() {
		if (mCursor.moveToFirst()) {
			mID = mCursor.getInt(0);
			mResolver.delete(uriData, ContactsContract.Data.RAW_CONTACT_ID + "=?", new String[]{mID+""});
		}
	}
}
