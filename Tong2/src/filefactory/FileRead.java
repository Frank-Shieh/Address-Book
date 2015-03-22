package filefactory;

import java.util.ArrayList;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

public abstract class FileRead extends FileControl {

	public FileRead(){
		DataManager.mListExcel = new ArrayList<Person>();
	}
	
	
	public abstract void readFile();
	
	
}
