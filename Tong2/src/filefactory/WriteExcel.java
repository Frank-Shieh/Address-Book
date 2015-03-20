package filefactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.graphics.Bitmap;
import android.util.Log;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

public class WriteExcel extends FileWrite {

	private ArrayList<Person> mPersons;
	private HSSFRow mRow;
	private HSSFCell mCell;
	private HSSFWorkbook mWorkBook;
	private HSSFSheet mSheet;
	private int mIndex = 0;
	private HSSFCellStyle mStyle;

	public WriteExcel() {
		// TODO Auto-generated constructor stub
		mPersons = DataManager.getAllPerson(DataManager.LOCAL);
	}

	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		mWorkBook = new HSSFWorkbook();
		mSheet = mWorkBook.createSheet("sheet1");
		createHeader();
		putData();
		writeExcel();
	}

	private void createHeader() {
		mRow = mSheet.createRow(0);
		mStyle = mWorkBook.createCellStyle();
		mStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		mCell = mRow.createCell(mIndex++);
		mCell.setCellValue("姓名");
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
		mCell.setCellValue("头像");
		mCell.setCellStyle(mStyle);
		mIndex = 0;
	}

	private void putData() {
		ByteArrayOutputStream baos = null;
		mStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		for (int i = 1; i <= mPersons.size(); i++, mIndex = 0) {
			mRow = mSheet.createRow(i);
			Person person = mPersons.get(i - 1);
			mCell = mRow.createCell(mIndex++);
			mCell.setCellStyle(mStyle);
			mCell.setCellValue(person.getName());
			for (int j = 0; j < DataManager.PHONETYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getPhone(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				Log.i("target size", String.valueOf(target.size()));
				if (target.size() == 1) {
					mCell.setCellValue(formatPhone(target.get(0)));
				} else {
				}
			}
			for (int j = 0; j < DataManager.EMAILTYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getEmail(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				if (target.size() == 1) {
					mCell.setCellValue(target.get(0));
				} else {
				}
			}
			for (int j = 0; j < DataManager.ADDRESSTYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getPostAddress(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				if (target.size() == 1) {
					mCell.setCellValue(target.get(0));
				} else {
				}
			}
			/**
			 * 写入头像
			 */
			baos = new ByteArrayOutputStream();
			if (person.getHeadPhoto() != null) {
				person.getHeadPhoto().compress(Bitmap.CompressFormat.PNG, 100,
						baos);
				HSSFPatriarch patriarch = mSheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255,
						(short) mIndex, i, (short) mIndex, i);
				anchor.setAnchorType(HSSFClientAnchor.MOVE_AND_RESIZE);
				patriarch.createPicture(anchor, mWorkBook.addPicture(
						baos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
			}
		}
	}

	private void writeExcel() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(DataManager.excelStorePath+"/test.xls");
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

	private String formatPhone(String target) {

		StringBuffer temp = new StringBuffer(target);
		for (int i = 0; i < temp.length(); i++) {
			if (temp.charAt(i) < '0' || temp.charAt(i) > '9')
				temp.deleteCharAt(i);
		}
		return temp.toString();
	}

}
