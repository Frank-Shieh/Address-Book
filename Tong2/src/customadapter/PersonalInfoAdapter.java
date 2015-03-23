package customadapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;
import com.reach.tong2.R;

public class PersonalInfoAdapter extends CustomAdapter {

	private ArrayList<String> mData;
	private ArrayList<String> mType;
	private Person mPerson;
	private String mTemp;

	public PersonalInfoAdapter(Context context,Person person) {
		// TODO Auto-generated constructor stub
		super(context);
		this.mPerson = person;
		this.mContext = context;
	}

	private void transform() {
		int index = 0;
		DataManager.TargetPerson.mData = new ArrayList<String>();
		DataManager.TargetPerson.mType = new ArrayList<String>();
		for (int i = 0; i < DataManager.PHONETYPE.length; i++) {
			if(mPerson.getPhone(i) == null)
				continue;
			for(int j = 0;j<mPerson.getPhone(i).size();j++,index++){
				DataManager.TargetPerson.mData.add(index, mPerson.getPhone(i).get(j));
				DataManager.TargetPerson.mType.add(index, DataManager.PHONETYPE[i]);
			}
		}
		for(int i = 0; i<DataManager.EMAILTYPE.length;i++){
			if(mPerson.getEmail(i) == null)
				continue;
			for(int j = 0;j<mPerson.getEmail(i).size();j++,index++){
				DataManager.TargetPerson.mData.add(index, mPerson.getEmail(i).get(j));
				DataManager.TargetPerson.mType.add(index, DataManager.EMAILTYPE[i]);
			}
		}
		for(int i = 0; i<DataManager.ADDRESSTYPE.length;i++){
			if(mPerson.getPostAddress(i) == null)
				continue;
			for(int j = 0;j<mPerson.getPostAddress(i).size();j++,index++){
				DataManager.TargetPerson.mData.add(index, mPerson.getPostAddress(i).get(j));
				DataManager.TargetPerson.mType.add(index, DataManager.ADDRESSTYPE[i]);
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		transform();
		mData = DataManager.TargetPerson.mData;
		mType = DataManager.TargetPerson.mType;
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
		public TextView data;
		public TextView type;
		public ImageView icon;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.info_list, null);
			vh.data = (TextView) convertView.findViewById(R.id.data_list_info);
			vh.type = (TextView) convertView.findViewById(R.id.type_list_info);
			vh.icon = (ImageView) convertView.findViewById(R.id.icon_list_info);
			convertView.setTag(vh);
		} else
			vh = (ViewHolder) convertView.getTag();
		vh.data.setText(mData.get(position));
		mTemp = mType.get(position);
		vh.type.setText(mTemp);
		if (mTemp.matches(".*ºÅÂë")) {
			vh.icon.setVisibility(View.VISIBLE);
		} else
			vh.icon.setVisibility(View.INVISIBLE);
		return convertView;
	}

}
