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

	private ArrayList<Person> persons;
	private Context context;
	
	public  MainPageAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.persons = DataManager.getAllPerson(DataManager.LOCAL);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
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
			convertView = View.inflate(context, R.layout.mainpage_list, null);
			vh.name = (TextView) convertView.findViewById(R.id.name);
			vh.phone = (TextView) convertView.findViewById(R.id.phonenumber);
			vh.headphoto = (ImageView) convertView.findViewById(R.id.headphoto);
			convertView.setTag(vh);
		}
		else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.name.setText(persons.get(position).getName());
		for(int i = 0;i<DataManager.PHONETYPE.length;i++){
			if(persons.get(position).getPhone(i) != null){
				vh.phone.setText(DataManager.PHONETYPE[i]+"£º"+persons.get(position).getPhone(i).get(0));
				break;
			}
		}
		Bitmap temp = persons.get(position).getHeadPhoto();
		if(temp != null)
			vh.headphoto.setImageBitmap(temp);
		return convertView;
	}

}
