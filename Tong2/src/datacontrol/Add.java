package datacontrol;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

import databasefactory.WriteContact;
import filefactory.UpDataExcel;

public class Add {

	public Add() {
	}

	@SuppressWarnings("unchecked")
	public boolean add(int model, Object target) {
		ArrayList<Person> parent = DataManager.addParent(model);
		if (parent == null)
			return false;
		else if (target instanceof Person)
			addPerson(parent, (Person) target);
		else if (target instanceof ArrayList<?>)
			addChild(parent, (ArrayList<Person>) target);
		return true;
	}

	private void addPerson(ArrayList<Person> parent, Person target) {
		parent.add(target);
	}

	private void addChild(ArrayList<Person> parent, ArrayList<Person> target) {
		parent.addAll(target);
	}

	public void upData(Context context,int model) {
		switch (model) {
		case DataManager.RequestCode.SrcCode.SRC_EXCEL:
			UpDataExcel temp1 = new UpDataExcel(UpDataExcel.INCREASE, DataManager.persentfilename);
			temp1.UpData();
			break;
		case DataManager.RequestCode.SrcCode.SRC_LOCAL:
			WriteContact temp2 = new WriteContact(context);
			temp2.writeContact();
			Log.i("local", "has");
			break;
		default:
			break;
		}
	}

}
