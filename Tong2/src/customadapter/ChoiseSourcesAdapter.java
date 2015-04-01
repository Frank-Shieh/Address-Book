package customadapter;

import com.reach.tong2.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChoiseSourcesAdapter extends CustomAdapter {

	private String[] target;
	
	public ChoiseSourcesAdapter(Context context,String[] target) {
		super(context);
		this.target = target;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return target.length;
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
		convertView = View.inflate(mContext, R.layout.choisesources_list, null);
		TextView name = (TextView) convertView.findViewById(R.id.name_choisesources_list);
		name.setText(target[position]);
		return convertView;
	}

}
