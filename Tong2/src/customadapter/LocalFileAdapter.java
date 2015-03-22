package customadapter;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.reach.tong2.R;

@SuppressLint("SimpleDateFormat")
public class LocalFileAdapter extends CustomAdapter {

	private File[] mTarget;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar date = Calendar.getInstance();
	public LocalFileAdapter(Context context,File[] target) {
		super(context);
		this.mTarget = target;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTarget.length;
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
		public TextView mFileName;
		public TextView mTime;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.localfile_list, null);
			vh = new ViewHolder();
			vh.mFileName = (TextView) convertView.findViewById(R.id.filename_localfile_list);
			vh.mTime = (TextView) convertView.findViewById(R.id.time_localfile_list);
			convertView.setTag(vh);
		}else
			vh = (ViewHolder) convertView.getTag();
		if(vh == null)
			Log.i("isisisis", "null");
		vh.mFileName.setText(mTarget[position].getName());
		date.setTimeInMillis(mTarget[position].lastModified());
		vh.mTime.setText(df.format(date.getTime()));
		return convertView;
	}


}
