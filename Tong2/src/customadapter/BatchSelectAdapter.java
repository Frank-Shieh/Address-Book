package customadapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;
import com.reach.tong2.R;


public class BatchSelectAdapter extends CustomAdapter {

	private ArrayList<Person> mPersons;
	
	public BatchSelectAdapter(Context context,ArrayList<Person> persons) {
		super(context);
		this.mPersons = persons;
		for(int i = 0;i<mPersons.size();i++){
			mPersons.get(i).mIsFull = false;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPersons.size();
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
	
	private class ViewHolder{
		public ImageView headphoto;
		public TextView name;
		public TextView phone;
		public CheckBox checkbox;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null)
		{
			vh = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.batchselect_list, null);
			vh.name = (TextView) convertView.findViewById(R.id.name);
			vh.phone = (TextView) convertView.findViewById(R.id.phonenumber);
			vh.headphoto = (ImageView) convertView.findViewById(R.id.headphoto);
			vh.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox_bacthdelete_list);
			vh.checkbox.setTag(position);
			vh.checkbox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CheckBox temp = (CheckBox) v;
					mPersons.get((Integer) temp.getTag()).mIsFull = temp.isChecked();
				}
			});
			convertView.setTag(vh);
		}
		else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.name.setText(mPersons.get(position).getName());
		vh.phone.setText("");
		for(int i = 0;i<DataManager.PHONETYPE.length;i++){
			if(mPersons.get(position).getPhone(i) != null){
				vh.phone.setText(DataManager.PHONETYPE[i]+"£º"+mPersons.get(position).getPhone(i).get(0));
				break;
			}
		}
		Bitmap temp = mPersons.get(position).getHeadPhoto();
		if(temp != null)
			vh.headphoto.setImageBitmap(temp);
		else
			vh.headphoto.setImageResource(R.drawable.defheadphoto);
		vh.checkbox.setTag(position);
		vh.checkbox.setChecked(mPersons.get(position).mIsFull);
		return convertView;
	}

}
