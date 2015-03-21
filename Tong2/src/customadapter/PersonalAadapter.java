package customadapter;

import com.reach.tong2.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PersonalAadapter extends BaseAdapter{

	private String[] mData;
	private int mLayout;
	private Context mContext;

	public PersonalAadapter(Context context,String[] data,int layout) {
		this.mData = data;
		this.mLayout = layout;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length;
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
	

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(mContext, mLayout, null);
		TextView text = (TextView) convertView.findViewById(R.id.textname);
		text.setText(mData[position]);
		return convertView;
	}
	
	

}
