package filefactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.reach.tong2.DataManager;


public class ReadFile {
	
	private String mFileName;
	
	public ReadFile(){
		this.mFileName = DataManager.targetFile;
	}
	
	public ReadFile(String filename){
		this.mFileName = DataManager.excelStorePath+"/"+filename;
	}

	public HSSFWorkbook readFile(HSSFWorkbook workBook) {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(mFileName));
			workBook = new HSSFWorkbook(fileInputStream);
			return workBook;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
