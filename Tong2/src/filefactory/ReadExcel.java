package filefactory;

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

import datacontrol.Add;
import datatypetransation.Transation;

public class ReadExcel extends FileRead {

	private String mfileName;
	private HSSFWorkbook mworkBook;
	private int msheetCount;
	private HSSFCell mcell;
	private String[] mtitle;
	private Person mperson;
	private int mHeadphotoposition = 0;
	private int mEmailPosition;
	private int mAddressPosition;
	private Add mAdd = new Add();
	private List<HSSFPictureData> mList;
	private DecimalFormat mformat = new DecimalFormat("0"); // 转换从excel得到的数值类型的数据格式

	public ReadExcel(String fileName) {
		// TODO Auto-generated constructor stub
		super();
		this.mfileName = fileName;
	}

	@Override
	public void readFile() {
		// TODO Auto-generated method stub
		ReadFile temp = new ReadFile(mfileName);
		mworkBook = temp.readFile(mworkBook);
		msheetCount = mworkBook.getNumberOfSheets();
		getDataFromSheet();
	}

	/**
	 * 用循环历遍excel表 第一层循环历遍sheet工作表 第二层循环历遍每一个表的行 第三层循环历遍每行的每一个单元格
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
				mAdd.add(DataManager.RequestCode.SrcCode.SRC_EXCEL, mperson);
			}
		}
	}

	/**
	 * 获得表头，根据表头插入数据
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
		mEmailPosition = DataManager.PHONETYPE.length + 1;
		mAddressPosition = mEmailPosition + DataManager.EMAILTYPE.length;
	}

	/**
	 * 每个单元有6种类型的数据 1、doubel型 2、string 3、boolean 4、空格 5、错误 6、公式
	 * 
	 * @param cellIndex
	 */
	private void getCellData(short cellIndex) {
		ArrayList<String> target = new ArrayList<String>();
		Transation temp = new Transation(mtitle[cellIndex]);
		switch (mcell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // double型的数字
			target.add(mformat.format(mcell.getNumericCellValue()));
			temp = new Transation(mtitle[cellIndex]);
			mperson.addData(temp.getFamily(), temp.getType(), target);
			break;
		case HSSFCell.CELL_TYPE_STRING: // 字符串
			dealWithStrings(mcell.getStringCellValue(), target);
			if (cellIndex == mAddressPosition + DataManager.ADDRESSTYPE.length)
				mperson.mHasheadphoto = mcell.getStringCellValue();
			else if (cellIndex == 0)
				mperson.addName(mcell.getStringCellValue());
			else
				mperson.addData(temp.getFamily(), temp.getType(), target);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔值
			mcell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK: // 空格
			Log.i("kongbai", "yes");
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 错误
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			break;
		default:
			break;
		}
	}

	/**
	 * 获得头像
	 */
	private void getHeadPhoto(int index) {
		if (index <= 0 || mList.size() <= 0
				|| mperson.mHasheadphoto.equals("no"))
			return;
		HSSFPictureData pdata = mList.get(mHeadphotoposition++);
		byte[] data = pdata.getData();
		if (data.length != 0) {
			mperson.addHeadPhoto(BitmapFactory.decodeByteArray(data, 0,
					data.length));
		}
	}

	private void dealWithStrings(String target, ArrayList<String> context) {

		String[] temp = null;
		temp = target.split(";");
		for (int i = 0; i < temp.length; i++) {
			context.add(temp[i]);
		}

	}

}
