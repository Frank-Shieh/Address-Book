package filefactory;

import java.io.File;

import android.util.Log;

import com.reach.tong2.DataManager;

public class LocalFiles {
	private   static int mFileCount;
	private  static File[] mFileName;
	
	public LocalFiles(){
		File temp = new File(DataManager.excelStorePath);
		mFileName = temp.listFiles();
		mFileCount = mFileName.length;
		Log.i("localfile", "has read");
	}
	
	public int getFilesCount(){
		return mFileCount;
	}
	
	public File[] getFilesName(){
		return mFileName;
	}
}
