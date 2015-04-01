package databasefactory;

import java.util.ArrayList;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.reach.tong2.Person;

public class DeleteContact extends ContactContral {

	private ArrayList<Person> mPersons;
	private Person mPerson;

	@SuppressWarnings("unchecked")
	public DeleteContact(Context context, Object person) {
		super(context);
		if (person instanceof Person)
			mPerson = (Person) person;
		else if (person instanceof ArrayList<?>)
			mPersons = (ArrayList<Person>) person;
	}

	public void delete() {
		if (mPerson == null) {
			for (Person person : mPersons)
				deletePerson(person);
		}
		else{
			deletePerson(mPerson);
		}
	}

	private void deletePerson(Person person) {
		String name = person.getName();
		Uri uri = null;
		mCursor = mResolver.query(uriRawContact, new String[] { _ID },
				displayName + "=?", new String[] { name }, null);
		if (mCursor.moveToFirst()) {
			int id = mCursor.getInt(0);
			uri = ContentUris.withAppendedId(uriRawContact, id);
			Uri newUri = uri
					.buildUpon()
					.appendQueryParameter(
							ContactsContract.CALLER_IS_SYNCADAPTER, "true")
					.build();
			mResolver.delete(newUri, null, null);
		}
		Log.i("delete", "success");
	}
}
