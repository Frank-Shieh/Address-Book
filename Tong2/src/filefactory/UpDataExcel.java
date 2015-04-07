package filefactory;

import com.reach.tong2.DataManager;

public class UpDataExcel {

	public static final int INCREASE = 1;
	public static final int CHANGE = 2;
	public static final int REDUCE = 3;
	private int mModel;
	private String mFileName;
	
	public UpDataExcel(int model,String name){
		this.mModel = model;
		this.mFileName = name;
		UpData();
	}
	
	public void UpData(){
		WriteExcel temp = null;
		switch (mModel) {
		case INCREASE:
			temp = new WriteExcel(DataManager.RequestCode.SrcCode.SRC_TEMP, mFileName, true);
			temp.writeFile();
			break;
		case REDUCE:
		case CHANGE:
			temp = new WriteExcel(DataManager.RequestCode.SrcCode.SRC_EXCEL, mFileName);
			temp.writeFile();
			break;
		default:
			break;
		}
	}
	
	
	
}
