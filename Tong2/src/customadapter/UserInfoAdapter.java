package customadapter;

import com.reach.tong2.DataManager;
import com.reach.tong2.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoAdapter extends CustomAdapter {

	private boolean mKey;
	private String[] mItem;
	private String[] mValue;
	private int mLayout;

	public UserInfoAdapter(Context context, int layout, String[] item,
			String[] value) {
		super(context);
		// TODO Auto-generated constructor stub
		switch (layout) {
		case R.layout.userinfo_list1:
			mKey = true;
			break;
		case R.layout.userinfo_list2:
			mKey = false;
			break;
		}
		this.mItem = item;
		this.mValue = value;
		this.mLayout = layout;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mKey)
			return 1;
		else
			return mItem.length;
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
		public TextView mTitle;
		public TextView mValue;
		public ImageView mHeadPhoto;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, mLayout, null);
			vh = new ViewHolder();
			if (mKey) {
				vh.mHeadPhoto = (ImageView) convertView
						.findViewById(R.id.headphoto_userinfo_list);
				vh.mTitle = (TextView) convertView.findViewById(R.id.title_userinfo_list);
			} else {
				vh.mTitle = (TextView) convertView
						.findViewById(R.id.title_userinfo_list);
				vh.mValue = (TextView) convertView
						.findViewById(R.id.context_userinfo_list);
			}
			convertView.setTag(vh);
		} else
			vh = (ViewHolder) convertView.getTag();

		if (mKey) {
			if (DataManager.getHeadphoto() != null)
				vh.mHeadPhoto.setImageBitmap(DataManager.getHeadphoto());
			vh.mTitle.setText(mItem[position]);
		} else {
			vh.mTitle.setText(mItem[position]);
			vh.mValue.setText(mValue[position]);
		}
		return convertView;
	}

}
