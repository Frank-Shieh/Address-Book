package filefactory;

import java.io.ByteArrayOutputStream;
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
	private int mRowIndex;
	private String mFileName = "test.xls";

	public WriteExcel(int model) {
		// TODO Auto-generated constructor stub
		mPersons = DataManager.getAllPerson(model);
		ReadFile temp = new ReadFile();
		mWorkBook = temp.readFile(mWorkBook);
	}

	public WriteExcel(int model, String name) {
		mPersons = DataManager.getAllPerson(model);
		mFileName = name;
		ReadFile temp = null;
		temp = new ReadFile();
		mWorkBook = temp.readFile(mWorkBook);
	}

	public WriteExcel(int model, String name, boolean append) {
		mPersons = DataManager.getAllPerson(model);
		ReadFile temp = null;
		if (append)
			temp = new ReadFile(name);
		else
			temp = new ReadFile();
		mWorkBook = temp.readFile(mWorkBook);
	}

	@Override
	public void writeFile() {
		// TODO Auto-generated method stub
		mSheet = mWorkBook.getSheet("sheet1");
		mRowIndex = mSheet.getLastRowNum() + 1;
		mStyle = mWorkBook.createCellStyle();
		putData();
		writeExcel();
	}

	private void putData() {
		ByteArrayOutputStream baos = null;
		mStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		
		for (int i = 0; i < mPersons.size(); i++, mIndex = 0, mRowIndex++) {
			mRow = mSheet.createRow(mRowIndex);
			Person person = mPersons.get(i);
			mCell = mRow.createCell(mIndex++);
			mCell.setCellStyle(mStyle);
			mCell.setCellValue(person.getName());
			for (int j = 0; j < DataManager.PHONETYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getPhone(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				StringBuffer temp = new StringBuffer();
				for (int k = 0; k < target.size(); k++) {
					temp.append(formatPhone(target.get(k)) + ";");
				}
				if (temp.length() != 0)
					mCell.setCellValue(temp.toString());
			}
			for (int j = 0; j < DataManager.EMAILTYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getEmail(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				StringBuffer temp = new StringBuffer();
				for (int k = 0; k < target.size(); k++) {
					temp.append(target.get(k) + ";");
				}
				if (temp.length() != 0)
					mCell.setCellValue(temp.toString());
			}
			for (int j = 0; j < DataManager.ADDRESSTYPE.length; j++, mIndex++) {
				ArrayList<String> target = person.getPostAddress(j);
				if (target == null)
					continue;
				mCell = mRow.createCell(mIndex);
				mCell.setCellStyle(mStyle);
				StringBuffer temp = new StringBuffer();
				for (int k = 0; k < target.size(); k++) {
					temp.append(target.get(k) + ";");
				}
				if (temp.length() != 0)
					mCell.setCellValue(temp.toString());
			}
			/**
			 * Ð´ÈëÍ·Ïñ
			 */
			mCell = mRow.createCell(mIndex);
			baos = new ByteArrayOutputStream();
			if (person.getHeadPhoto() != null) {
				person.getHeadPhoto().compress(Bitmap.CompressFormat.PNG, 100,
						baos);
				HSSFPatriarch patriarch = mSheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 512, 255,
						(short) mIndex, mRowIndex, (short) mIndex, mRowIndex);
				anchor.setAnchorType(HSSFClientAnchor.MOVE_AND_RESIZE);
				patriarch.createPicture(anchor, mWorkBook.addPicture(
						baos.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				mCell.setCellValue("yes");
			} else
				mCell.setCellValue("no");

		}
	}

	private void writeExcel() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(DataManager.excelStorePath + "/"
					+ mFileName);
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
			if (temp.charAt(i) < '0' || temp.charAt(i) > '9'
					|| temp.charAt(i) == ' ')
				temp.deleteCharAt(i);
		}
		return temp.toString();
	}

}
