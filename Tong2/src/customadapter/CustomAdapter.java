package customadapter;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class CustomAdapter extends BaseAdapter{

	protected Context mContext;
	
	public CustomAdapter(Context context){
		this.mContext = context;
	}
	
	
}
