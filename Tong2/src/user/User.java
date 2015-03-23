package user;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mSex;
	private String mPhone;
	private BmobFile mHeadphoto;
	
	//���úͻ�ȡ�绰����
	public void setPhone(String phone){
		this.mPhone = phone;
	}
	public String getPhone(){
		return mPhone;
	}
	
	//���úͻ�ȡ�Ա�
	public void setSex(String sex){
		this.mSex = sex;
	}
	public String getSex(){
		return mSex;
	}
	public BmobFile getHeadphoto() {
		return mHeadphoto;
	}
	public void setHeadphoto(BmobFile headphoto) {
		this.mHeadphoto = headphoto;
	}

	
}
