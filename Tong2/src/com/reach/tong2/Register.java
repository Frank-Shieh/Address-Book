package com.reach.tong2;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import user.User;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Register extends Activity implements OnClickListener,
		DialogInterface.OnClickListener {

	private ImageView submit;
	private ImageView photo;
	private EditText usrname;
	private EditText pwd;
	private EditText compwd;
	private EditText email;
	private EditText phone;
	private RadioGroup sex;
	private String msex;
	private String username;
	private String password;
	private String temppwd;
	private String mail;
	private BmobFile file;
	private Bitmap headphoto = null;
	private boolean isserveron = false;
	private boolean issex = false;
	private User newuser;
	private String headphotopath;
	private static final int FROMIMAGE = -1;
	private static final int FROMCCAMERA = -2;
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
	}

	private void init() {
		submit = (ImageView) findViewById(R.id.regsubmit);
		submit.setOnClickListener(this);
		photo = (ImageView) findViewById(R.id.headphoto);
		photo.setOnClickListener(this);
		usrname = (EditText) findViewById(R.id.usrname);
		pwd = (EditText) findViewById(R.id.pwd);
		compwd = (EditText) findViewById(R.id.pwdcom);
		email = (EditText) findViewById(R.id.email);
		phone = (EditText) findViewById(R.id.phone);
		sex = (RadioGroup) findViewById(R.id.sex);
		sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.male: {
					msex = "男";
				}
					break;
				case R.id.female: {
					msex = "女";
				}
					break;
				}
				issex = true;
			}
		});
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regsubmit: {
			username = usrname.getText().toString();
			password = pwd.getText().toString();
			temppwd = compwd.getText().toString();
			mail = email.getText().toString();
			if (check())
				regist();
		}
			break;
		case R.id.headphoto: {
			showdialog();
		}
			break;
		}
	}
	
	private void regist() {
		if(headphoto != null)
		{
			savePic(headphoto);
			file.uploadblock(this, new UploadFileListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Log.i("load","success");
					signUp();
				}
				
				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					super.onProgress(arg0);
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Log.i("load", "fail");
				}
			});
		}
		else 
		{
			signUp();
			Log.i("headphoto", "no");
		}
	}

	private void signUp()
	{
		newuser = new User();
		newuser.setUsername(username);
		newuser.setPassword(password);
		newuser.setEmail(mail);
		newuser.setSex(msex);
		newuser.setPhone(phone.getText().toString());
		newuser.setHeadphoto(file);
		newuser.signUp(this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT)
						.show();
                 returnback();
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
//				Toast.makeText(Register.this,
//						Datamanager.getErrorCode((Integer) arg0),
//						Toast.LENGTH_SHORT).show();
				Log.i("registe", "fail"+arg1);
			}
		});
	}
	
	public void returnback(){
		DataManager.user = newuser;
		Intent intent = this.getIntent();
		intent.putExtra("username", username);
		intent.putExtra("pwd", password);
		this.setResult(1, intent);
		this.finish();
	}
	
	private boolean check() {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		if (username.length() == 0 || password.length() == 0
				|| temppwd.length() == 0 || mail.length() == 0 || !issex) {
			Toast.makeText(Register.this, "请完整填写信息", Toast.LENGTH_SHORT).show();
			return false;
		}
		// 接受协议
		if (!isserveron) {
			Toast.makeText(Register.this, "是否接受协议", Toast.LENGTH_SHORT).show();
			return false;
		}
		// 验证用户名有效性
		if (username.length() == 0) {
			return false;
		}
		// 验证密码有效性
		if (password.length() == 0) {
			return false;
		}
		// 验证密码匹配
		if (!password.equals(temppwd)) {
			Toast.makeText(Register.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
			return false;
		}
		// 邮箱有效性验证
		Pattern pattern = Pattern.compile(str);
		Matcher m = pattern.matcher(mail);
		if (!m.matches()) {
			Toast.makeText(Register.this, "邮箱地址无效", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void showdialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("设置头像");
		dialog.setMessage("请选择图片源");
		dialog.setPositiveButton("照片", this);// -1
		dialog.setNegativeButton("相机", this);// -2
		dialog.create().show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		// Intent intent = null;
		// switch (which) {
		// case FROMIMAGE:
		// intent = new Intent(this,Headphotoget.class);
		// intent.putExtra("code", IMAGE_REQUEST_CODE);
		// intent.putExtra("from", FROMIMAGE);
		// startActivityForResult(intent,1);
		// break;
		// case FROMCCAMERA:
		// intent = new Intent(this,Headphotoget.class);
		// intent.putExtra("code", CAMERA_REQUEST_CODE);
		// intent.putExtra("from", FROMCCAMERA);
		// startActivityForResult(intent,2);
		// break;
		// }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		headphoto = DataManager.getHeadphoto();
		photo.setImageBitmap(headphoto);
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void savePic(Bitmap photo) {
		String fileName = "/headphoto_"+username+".png";
		headphotopath = getCacheDir().getParentFile() + "/user_" + username;
		File dirFile = new File(headphotopath);
		dirFile.mkdirs();
		headphotopath = headphotopath + fileName;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(headphotopath));
			photo.compress(Bitmap.CompressFormat.PNG, 100, bos);// (0 - 100)压缩文件
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		DataManager.setHeadphoto(headphotopath);
		file = new BmobFile(new File(headphotopath));
	}

}
