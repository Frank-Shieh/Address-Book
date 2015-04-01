package filefactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.util.Log;

import com.reach.tong2.DataManager;

public class FileTamplate {
	
	private HSSFWorkbook mWorkBook;
	private HSSFSheet mSheet;
	private HSSFRow mRow;
	private HSSFCell mCell;
	private HSSFCellStyle mStyle;
	private int mIndex = 0;


	public FileTamplate(){
		
	}
	
	public static FileTamplate create(){
		FileTamplate temp = new FileTamplate();
		temp.createTamplate();
		return temp;
	}
	
	private  void createTamplate(){
		mWorkBook = new HSSFWorkbook();
		mSheet = mWorkBook.createSheet("sheet1");
		createHeader();
		writeExcel();
		Log.i("tamplate", "create2");
	}
	
	private void createHeader() {
		mRow = mSheet.createRow(0);
		mStyle = mWorkBook.createCellStyle();
		mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		mCell = mRow.createCell(mIndex++);
		mCell.setCellValue("ÐÕÃû");
		mCell.setCellStyle(mStyle);
		for (int i = 0; i < DataManager.PHONETYPE.length; mIndex++, i++) {
			mCell = mRow.createCell(mIndex);
			mCell.setCellValue(DataManager.PHONETYPE[i]);
			mCell.setCellStyle(mStyle);
		}
		for (int i = 0; i < DataManager.EMAILTYPE.length; mIndex++, i++) {
			mCell = mRow.createCell(mIndex);
			mCell.setCellValue(DataManager.EMAILTYPE[i]);
			mCell.setCellStyle(mStyle);
		}
		for (int i = 0; i < DataManager.ADDRESSTYPE.length; i++, mIndex++) {
			mCell = mRow.createCell(mIndex);
			mCell.setCellValue(DataManager.ADDRESSTYPE[i]);
			mCell.setCellStyle(mStyle);
		}
		mCell = mRow.createCell(mIndex);
		mCell.setCellValue("Í·Ïñ");
		mCell.setCellStyle(mStyle);
		mIndex = 0;
	}

	private void writeExcel() {
		FileOutputStream fos = null;
		try {
			Log.i("tamplate", "create1  "+DataManager.targetFile);
			File temp = new File(DataManager.targetFile);
			fos = new FileOutputStream(temp);
			
			mWorkBook.write(fos);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
