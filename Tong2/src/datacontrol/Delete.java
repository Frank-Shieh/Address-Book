package datacontrol;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

import databasefactory.DeleteContact;
import databasefactory.WriteContact;
import filefactory.UpDataExcel;

public class Delete {

	
	public Delete() {
	}
	
	@SuppressWarnings("unchecked")
	public boolean delete(int model, Object target){
		ArrayList<Person> parent = DataManager.deleteParent(model);
		if (parent == null)
			return false;
		else if (target instanceof ArrayList<?>)
			deleteChild(parent, (ArrayList<Person>) target);
		else if (target instanceof Person)
			deletePerson(parent, (Person) target);
		return true;
	}
	
	private void deletePerson(ArrayList<Person> parent, Person target) {
		Iterator<Person> temp = null;
		temp = parent.iterator();
		if (temp == null) {
			return;
		}
		while (temp.hasNext()) {
			Person temp1 = temp.next();
			if (temp1.equals(target))
				temp.remove();
		}

	}

	private void deleteChild(ArrayList<Person> parent, ArrayList<Person> target) {
		parent.removeAll(target);
	}
	
	public void upData(Context context,int model,Object person){
		switch (model) {
		case DataManager.RequestCode.SrcCode.SRC_EXCEL:
			UpDataExcel temp1 = new UpDataExcel(UpDataExcel.REDUCE, DataManager.persentfilename);
			temp1.UpData();
			break;
		case DataManager.RequestCode.SrcCode.SRC_LOCAL:
			DeleteContact temp2 = new DeleteContact(context, person);
			temp2.delete();
			break;
		default:
			break;
		}
	}
	

}
