package datatypetransation;

import java.util.HashMap;

import android.provider.ContactsContract;

import com.reach.tong2.DataManager;

public class Transation {

	private int mFamily;
	private int mType;
	private static final String[] PHONETYPE = { "移动号码", "家庭号码", "其他号码" };
	private static final String[] EMAILTYPE = { "家庭邮件", "工作邮件" };
	private static final String[] ADDRESSTYPE = { "家庭地址", "工作地址", "其他地址" };
	private static HashMap<String, Integer> phonePosition = new HashMap<String, Integer>();
	private static HashMap<String, Integer> emailPosition = new HashMap<String, Integer>();
	private static HashMap<String, Integer> addressPosition = new HashMap<String, Integer>();

	static{
		for (int i = 0; i < PHONETYPE.length; i++) {
			phonePosition.put(PHONETYPE[i], i);
		}
		for (int i = 0; i < EMAILTYPE.length; i++) {
			emailPosition.put(EMAILTYPE[i], i);
		}
		for (int i = 0; i < ADDRESSTYPE.length; i++) {
			addressPosition.put(ADDRESSTYPE[i], i);
		}

	}
	
	public Transation(String header) {
		confirmString(header);
	}

	public Transation(String Type, int data2) {
		confirmNumber(Type, data2);
	}

	private void confirmString(String target) {
		if (target.matches(".*号码")) {
			mFamily = DataManager.FamilyType.FAMILY_PHONE;
			for (int i = 0; i < DataManager.PHONETYPE.length; i++) {
				if (target.equals(DataManager.PHONETYPE[i])) {
					mType = i;
					break;
				}
			}
		} else if (target.matches(".*邮件")) {
			mFamily = DataManager.FamilyType.FAMILY_EMAIL;
			for (int i = 0; i < DataManager.EMAILTYPE.length; i++) {
				if (target.equals(DataManager.EMAILTYPE[i])) {
					mType = i;
					break;
				}
			}
		} else if (target.matches(".*地址")) {
			mFamily = DataManager.FamilyType.FAMILY_ADDRESS;
			for (int i = 0; i < DataManager.ADDRESSTYPE.length; i++) {
				if (target.equals(DataManager.ADDRESSTYPE[i])) {
					mType = i;
					break;
				}
			}
		}
	}

	private void confirmNumber(String Type, int data2) {
		mFamily = checkType(Type);
		mType = getIndex(mFamily, data2);
	}

	private  int getIndex(int type, int data2) {
		switch (type) {
		case DataManager.FamilyType.FAMILY_EMAIL:
			switch (data2) {
			case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
				return emailPosition.get("家庭邮件");
			case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
				return emailPosition.get("工作邮件");
			}
		case DataManager.FamilyType.FAMILY_PHONE:
			switch (data2) {
			case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
				return phonePosition.get("移动号码");
			case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
				return phonePosition.get("家庭号码");
			case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
				return phonePosition.get("其他号码");
			}
		case DataManager.FamilyType.FAMILY_ADDRESS:
			switch (data2) {
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
	
	private int checkType(String type) {
		if (type.equals(DataManager.MimeType.EMAIL_V2))
			return DataManager.FamilyType.FAMILY_EMAIL;
		if (type.equals(DataManager.MimeType.PHONE_V2))
			return DataManager.FamilyType.FAMILY_PHONE;
		if (type.equals(DataManager.MimeType.NAME))
			return 7;
		if (type.equals(DataManager.MimeType.POSTALADDRESS_V2))
			return DataManager.FamilyType.FAMILY_ADDRESS;
		if (type.equals(DataManager.MimeType.PHOTO))
			return 10;
		return 0;
	}

	public int getFamily() {
		return mFamily;
	}

	public int getType() {
		return mType;
	}

}
