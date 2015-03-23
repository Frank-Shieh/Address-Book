package filefactory;

import java.io.File;

import android.util.Log;

import com.reach.tong2.DataManager;

public class LocalFiles {
	private   int mFileCount;
	private  File[] mFileName;
	
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
