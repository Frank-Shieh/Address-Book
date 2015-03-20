package filefactory;

public class FileFactory {

	public FileFactory(){
	}
	
	
	public static FileRead readFile(String fileName){
		
		switch (FileTypeCode.getFileTypeCode(fileName)) {
		
		case FileTypeCode.FILETYPECODE_DOC_:
			
			break;
			
		case FileTypeCode.FILETYPECODE_XLS_:
			return new ReadExcel(fileName);
			
		case FileTypeCode.FILETYPECODE_TXT:
			break;

		default:
			break;
		}
		
		
		
		
		return null;
		
	}
	
}



