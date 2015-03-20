package filefactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;

public class ReadExcel extends FileRead {

	private String mfileName;
	private HSSFWorkbook mworkBook;
	private int msheetCount;
	private HSSFCell mcell;
	private String[] mtitle;
	private Person mperson;
	private int mPhonePosition;
	private int mEmailPosition;
	private int mAddressPosition;
	private List<HSSFPictureData> mList;
	private DecimalFormat mformat = new DecimalFormat("0"); // ת����excel�õ�����ֵ���͵����ݸ�ʽ

	public ReadExcel(String fileName) {
		// TODO Auto-generated constructor stub
		this.mfileName = fileName;
	}

	@Override
	public void readFile() {
		// TODO Auto-generated method stub
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(mfileName));
			mworkBook = new HSSFWorkbook(fileInputStream);
			msheetCount = mworkBook.getNumberOfSheets();
			getDataFromSheet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Log.i("IOException:", "IO close exception");
				}
			}
		}
	}

	/**
	 * ��ѭ������excel�� ��һ��ѭ������sheet������ �ڶ���ѭ������ÿһ������� ������ѭ������ÿ�е�ÿһ����Ԫ��
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void getDataFromSheet() {
		mList = mworkBook.getAllPictures();
		for (int sheetCount = 0; sheetCount < msheetCount; sheetCount++) {
			HSSFSheet sheet = mworkBook.getSheetAt(sheetCount);
			getTitleFromSheet(sheet);
			for (int rowCount = 1; rowCount <= sheet.getLastRowNum(); rowCount++) {
				HSSFRow row = sheet.getRow(rowCount);
				if (row == null)
					continue;
				mperson = new Person();
				for (short cellCount = 0; cellCount < row.getLastCellNum(); cellCount++) {
					mcell = row.getCell(cellCount);
					if (mcell == null)
						continue;
					getCellData(cellCount);
				}
				getHeadPhoto(rowCount);
				DataManager.addPerson(DataManager.EXCEL, mperson);

			}
		}
	}

	/**
	 * ��ñ�ͷ�����ݱ�ͷ��������
	 * 
	 * @param sheet
	 */

	private void getTitleFromSheet(HSSFSheet sheet) {
		HSSFRow row = sheet.getRow(0);
		if (row == null)
			return;
		short cellNumber = row.getLastCellNum();
		mtitle = new String[cellNumber];
		for (int i = 0; i < cellNumber; i++) {
			HSSFCell cell = row.getCell(i);
			mtitle[i] = cell.getStringCellValue();
		}
		mPhonePosition = 1;
		mEmailPosition = DataManager.PHONETYPE.length + 1;
		mAddressPosition = mEmailPosition + DataManager.EMAILTYPE.length;
	}

	/**
	 * ÿ����Ԫ��6�����͵����� 1��doubel�� 2��string 3��boolean 4���ո� 5������ 6����ʽ
	 * 
	 * @param cellIndex
	 */
	private void getCellData(short cellIndex) {
		ArrayList<String> target = new ArrayList<String>();
		switch (mcell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // double�͵�����
			target.add(mformat.format(mcell.getNumericCellValue()));
			// if(cellIndex>=mAddressPosition)
//			Log.i("phone2", mformat.format(mcell.getNumericCellValue()));
			mperson.addPhone(target,
					DataManager.phonePosition.get(mtitle[cellIndex]));
			break;
		case HSSFCell.CELL_TYPE_STRING: // �ַ���
			target.add(mcell.getStringCellValue());
			if (cellIndex >= mAddressPosition)
				mperson.addAddress(target,
						DataManager.addressPosition.get(mtitle[cellIndex]));
			else if (cellIndex >= mEmailPosition)
				mperson.addEmail(target,
						DataManager.emailPosition.get(mtitle[cellIndex]));
			else if(cellIndex == 0)
				mperson.addName(mcell.getStringCellValue());
			else 
				mperson.addPhone(target,
						DataManager.phonePosition.get(mtitle[cellIndex]));
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // ����ֵ
			mcell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK: // �ո�
			Log.i("kongbai", "yes");
			break;
		case HSSFCell.CELL_TYPE_ERROR: // ����
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // ��ʽ
			break;
		default:
			break;
		}
	}

	/**
	 * ���ͷ��
	 */
	private void getHeadPhoto(int index) {
		HSSFPictureData pdata = mList.get(index - 1);
		byte[] data = pdata.getData();
		if (data.length != 0) {
			mperson.addHeadPhoto(BitmapFactory.decodeByteArray(data, 0,
					data.length));
		}
	}

}
