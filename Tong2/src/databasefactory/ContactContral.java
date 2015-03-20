package databasefactory;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactContral {

	protected Context mContext;
	protected ContentResolver mResolver;
	protected Cursor mCursor;
	public static final String _ID = ContactsContract.RawContacts._ID;
	public static final String displayName = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY;
	public static final Uri uriRawContact = ContactsContract.RawContacts.CONTENT_URI;
	public static final Uri uriData = ContactsContract.Data.CONTENT_URI;
	

	public ContactContral(Context context){
		this.mContext = context;
		mResolver = mContext.getContentResolver();
	}
	
	
	
	
	
}
