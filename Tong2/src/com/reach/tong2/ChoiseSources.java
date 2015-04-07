package com.reach.tong2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import customadapter.ChoiseSourcesAdapter;

public class ChoiseSources extends Activity implements OnItemClickListener {

	private ListView list;
	private String[] target;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choisesources);
		target = getResources().getStringArray(R.array.choisesources_item);
		list = (ListView) this.findViewById(R.id.list_choisesources);
		list.setAdapter(new ChoiseSourcesAdapter(this, target));
		list.setOnItemClickListener(this);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent("com.reach.tong2.BatchSelect");
			intent.putExtra(DataManager.RequestCode.SrcCode.REQUESTCODE_SRC,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			intent.putExtra(DataManager.RequestCode.DstCode.REQUESTCODE_DST,
					DataManager.RequestCode.DstCode.DST_LOCAL);
			intent.putExtra(DataManager.ActionCode.ACTIONCODE,
					DataManager.ActionCode.ADD);
			startActivityForResult(intent,
					DataManager.RequestCode.SrcCode.SRC_EXCEL);
			break;
		case 1:
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		setResult(resultCode);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
