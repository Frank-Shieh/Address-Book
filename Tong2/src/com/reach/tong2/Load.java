package com.reach.tong2;

import java.util.Timer;
import java.util.TimerTask;

import user.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import databasefactory.ReadContact;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Load extends Activity {

	private boolean isFrist;
	private boolean hasuser;
	private String target;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		SharedPreferences setting = getSharedPreferences("configuration", 0);
		DataManager.user = BmobUser.getCurrentUser(this, User.class);
		hasuser = (DataManager.user != null);
		isFrist = (!hasuser && setting.getBoolean("isFrist", true));
		Bmob.initialize(this, "e2e1d4107c3a430f8adf3d843aacd70e");
		Timer timer = new Timer();
		TimerTask tt = null;
		if (isFrist) {
			SharedPreferences.Editor editor = setting.edit();
			editor.putBoolean("isFrist", false);
			editor.commit();
			DataManager.frist();
			target = "com.reach.tong2.Logn";
		} else if (hasuser) {
			target = "com.reach.tong2.Main";
			DataManager.frist();
		} else {
			target = "com.reach.tong2.Logn";
			DataManager.frist();
		}
		tt = new TimerTask() {
			@Override
			public void run() {
				ReadContact temp = new ReadContact(Load.this);
				temp.getContact();
				Intent intent = new Intent(target);
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(tt, 2000);
	}

}
