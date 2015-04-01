package filefactory;

public class CreateExcel {
	private int mModel;
	private String mName;
	
	public CreateExcel(int model, String name){
		this.mModel = model;
		this.mName = name;
	}
	
	public void create(){
		FileWrite target = new WriteExcel(mModel, mName);
		target.writeFile();
	}
}
