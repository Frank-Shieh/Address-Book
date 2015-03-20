package databasefactory;

import java.util.ArrayList;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

public class WriteContact extends ContactContral{

	private ArrayList<Person> mPersons;
	private ContentValues mValues;
	private long mID;

	public WriteContact(Context context) {
		super(context);
		mPersons = DataManager.getAllPerson(DataManager.TEMP);
	}

	public void writeContact() {
		mValues = new ContentValues();
		for (Person person : mPersons) {
			writePerson(person);
		}
	}

	private void writePerson(Person target) {
		mID = ContentUris.parseId(mResolver.insert(
				ContactsContract.RawContacts.CONTENT_URI, mValues));
		new InsetPerson(target, mResolver, (int) mID);
	}

}