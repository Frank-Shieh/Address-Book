package customadapter;

import java.util.ArrayList;

import com.reach.tong2.DataManager;
import com.reach.tong2.Person;
import com.reach.tong2.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPageAdapter extends CustomAdapter {

	private ArrayList<Person> mPersons;
	
	public  MainPageAdapter(Context context, ArrayList<Person> person) {
		// TODO Auto-generated constructor stub
		super(context);
		this.mPersons = person;
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
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null)
		{
			vh = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.mainpage_list, null);
			vh.name = (TextView) convertView.findViewById(R.id.name);
			vh.phone = (TextView) convertView.findViewById(R.id.phonenumber);
			vh.headphoto = (ImageView) convertView.findViewById(R.id.headphoto);
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
		return convertView;
	}

}
