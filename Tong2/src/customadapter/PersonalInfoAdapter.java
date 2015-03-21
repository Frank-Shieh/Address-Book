package customadapter;

import java.util.ArrayList;
import com.reach.tong2.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PersonalInfoAdapter extends BaseAdapter{

	private ArrayList<String> mTarget;
	private Context mContext;
	
	
	public  PersonalInfoAdapter(ArrayList<String> target, Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mTarget = target;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0;i<mTarget.size();i++){
			if(mTarget.get(i) == null)
				continue;
			sum += mTarget.get(i).length();
		}
		return sum;
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
		public TextView nameFlag;
		public TextView name;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			int sum = 0;
			ViewHolder vh = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.info_list, null);
			vh.name = (TextView) convertView.findViewById(R.id.name_info_list);
			vh.nameFlag = (TextView) convertView.findViewById(R.id.name_flag_info_list);
			for(int i = 0;i<mTarget.size();i++){
				if(mTarget.get(i) == null)
					continue;
				sum += mTarget.get(i).length();
				if(position > sum)
			}
		}
		
		
		
		
		return null;
	}

}
