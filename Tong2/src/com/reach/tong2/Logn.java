package com.reach.tong2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import user.User;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Logn extends Activity implements OnClickListener {

	private ImageView headphoto;
	private Button submit;
	private TextView Fpwd;
	private TextView register;
	private EditText username;
	private EditText pwd;
	private Handler handler;
	private String name;
	private String password;
	private Intent intent;
	private InputMethodManager imm;
	private boolean issubmit = true;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logn);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Intent intent = new Intent(Logn.this, Main.class);
				startActivity(intent);
				finish();
			}
		};
		init();
	}

	private void init() {
		headphoto = (ImageView) findViewById(R.id.headphoto);
		username = (EditText) findViewById(R.id.username);
		pwd = (EditText) findViewById(R.id.password);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
		Fpwd = (TextView) findViewById(R.id.Fpwd);
		Fpwd.setOnClickListener(this);
		register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(this);
		if (DataManager.user != null) {
			Bitmap bitmap = DataManager.getHeadphoto();
			if (bitmap != null)
				headphoto.setImageBitmap(bitmap);
			username.setText(DataManager.user.getUsername());
		}
		imm = (InputMethodManager) getSystemService(Logn.INPUT_METHOD_SERVICE);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.register: {
			intent = new Intent(Logn.this, Register.class);
			startActivityForResult(intent, 1);
		}
			break;

		case R.id.Fpwd: {

		}
			break;

		case R.id.submit: {
			if (imm != null) {
				imm.hideSoftInputFromWindow(getWindow().getDecorView()
						.getWindowToken(), 0);
			}
			if (issubmit)
				login();
		}
			break;

		default:
			break;
		}

	}
	
	private void login() {
		name = username.getText().toString();
		password = pwd.getText().toString();
		issubmit = false;
		User user = new User();
		user.setUsername(name);
		user.setPassword(password);
		user.login(this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(Logn.this, "µÇÂ¼³É¹¦", Toast.LENGTH_SHORT).show();
				Download temp = new Download();
				temp.start();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
//				Toast.makeText(Logn.this, DataManager.getErrorCode(arg0),
//						Toast.LENGTH_SHORT).show();
				issubmit = true;
				Log.i("logn", "fail code : " + String.valueOf(arg0));
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (data != null) {
			username.setText(data.getStringExtra("username"));
			pwd.setText(data.getStringExtra("pwd"));
			login();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@SuppressLint("SdCardPath")
	private class Download extends Thread {

		private String path = getCacheDir().getParentFile()+"/user_";

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			DataManager.user = BmobUser.getCurrentUser(Logn.this, User.class);
			String path = this.path + name;
			String url = null;
			if (DataManager.user.getHeadphoto() != null) {
				url = DataManager.user.getHeadphoto().getFileUrl(Logn.this);
			}
			if (url != null)
				downloadFile(url, path);

			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}

		public boolean downloadFile(String url, String path) {

			URL url1 = null;

			File target = new File(path);
			path += "/headphoto_"+name+".png";
			// this.path = path;
			if (target.exists()) {
				DataManager.setHeadphoto(path);
				return true;
			}
			target.mkdirs();
			target = new File(path);
			Log.i("path", path);
			FileOutputStream fos = null;
			InputStream ip = null;
			byte[] bs = new byte[4096];
			int i = -1;
			try {
				url1 = new URL(url);
				Log.i("url", "open success" + String.valueOf((url1 == null)));
				fos = new FileOutputStream(target);
				Log.i("fos", "open success" + String.valueOf((fos == null)));
				ip = url1.openStream();
				Log.i("fis", "open success");
				while ((i = ip.read(bs)) != -1) {
					fos.write(bs, 0, i);
				}
				DataManager.setHeadphoto(path);
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
				if (ip != null)
					try {
						ip.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return false;

		}
	}

}
