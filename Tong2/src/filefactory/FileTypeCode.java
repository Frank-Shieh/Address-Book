package filefactory;


public class FileTypeCode {
	
	public static final int FILETYPECODE_DOC_ = 1;
	public static final int FILETYPECODE_XLS_ = 2;
	public static final int FILETYPECODE_TXT = 3;
	
	public FileTypeCode(){
		
	}
	
	public static int getFileTypeCode(String fileName){
		String suffix = getFileSuffix(fileName);
		if(suffix.equals("doc") || suffix.equals("docx"))
			return 1;
		if(suffix.equals("xls") || suffix.equals("xlsx"))
			return 2;
		if(suffix.equals("txt"))
			return 3;
		return 0;
	}
	
	private static String getFileSuffix(String fileName)
	{
		String temp = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length()).toLowerCase();
		return temp.substring(temp.lastIndexOf(".")+1, temp.length());
	}
	
}
