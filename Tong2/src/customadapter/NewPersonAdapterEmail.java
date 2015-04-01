package customadapter;

import java.util.List;
import java.util.Map;

import com.reach.tong2.DataManager;
import com.reach.tong2.R;

import customadapter.NewPersonAdapterPhone.FocusChange;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewPersonAdapterEmail extends CustomAdapter {

	private List<Map<Integer, String>> mData;
	private List<Map<Integer, Integer>> mType;
	private int mLayout;
	private ArrayAdapter<String> arrayAdapter;
	private Integer index = -1;
	private int mFamily = 0;
	private int mCount;
	private String FAMILY;

	public NewPersonAdapterEmail(Context context, int layout,
			List<Map<Integer, String>> data, List<Map<Integer, Integer>> type,
			String[] arr, int count) {
		super(context);
		mLayout = layout;
		mData = data;
		mType = type;
		mCount = count;
		arrayAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, arr);
		if (arr.equals(DataManager.PHONETYPE))
			mFamily = 0;
		else if (arr.equals(DataManager.EMAILTYPE))
			mFamily = 1;
		else if (arr.equals(DataManager.ADDRESSTYPE))
			mFamily = 2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.get(mFamily).size();
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
		public EditText mData;
		public Spinner mSpinner;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = View.inflate(mContext, mLayout, null);
			vh.mData = (EditText) convertView
					.findViewById(R.id.newperson_email_entertext);
			vh.mData.setTag(position);
//			vh.mData.setOnFocusChangeListener(new FocusChange());
			vh.mData.addTextChangedListener(new EditTextListener(vh.mData));
			vh.mSpinner = (Spinner) convertView
					.findViewById(R.id.newperson_email_spinner);
			vh.mSpinner.setAdapter(arrayAdapter);
			vh.mSpinner.setTag(position);
			vh.mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Spinner temp = (Spinner) parent;
					int itemposition = (Integer) temp.getTag();
					mType.get(mFamily).put(itemposition, position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			convertView.setTag(vh);
		} else
			vh = (ViewHolder) convertView.getTag();
		vh.mData.setTag(position);
		String temp = mData.get(position).get(position);
		if (temp != null && !"".equals(temp)) {
			vh.mData.setText(temp);
		}
		vh.mData.clearFocus();
		if (index != -1 && index == position) {
			vh.mData.requestFocus();
		}
		vh.mSpinner.setTag(position);
		if (mType.get(mFamily).get(position) != null)
			vh.mSpinner.setSelection(mType.get(mFamily).get(position));
		return convertView;
	}

	private class EditTextListener implements TextWatcher {

		private EditText mTarget;
		private String temp;
		private boolean change;

		public EditTextListener(EditText target) {
			mTarget = target;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			System.out.println(s.toString());
			temp = s.toString();
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s != null && !"".equals(s.toString())) {
				int position = (Integer) mTarget.getTag();
				if (mData.get(mFamily).get(position) == null
						|| mData.get(mFamily).get(position) == ""
						|| !temp.equals(s.toString()))
					mData.get(mFamily).put(position, s.toString());
				// System.out.println(mFamily+" "+mData.get(mFamily).get(position));
				System.out.println(mFamily + " have " + s.toString());
			} else if (s != null && !"".equals(s.toString())) {
				int position = (Integer) mTarget.getTag();
				// mData.get(mFamily).put(position, s.toString());
				System.out.println(mFamily + " "
						+ mData.get(mFamily).get(position));
				System.out.println(mFamily + " no have");
				System.out.println("temp: " + temp + " s: " + s.toString());
			}
		}
	}
	
	public class FocusChange implements OnFocusChangeListener{

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			EditText temp = (EditText) v;
			int position = (Integer) temp.getTag();
			if(!hasFocus)
				mData.get(mFamily).put(position, temp.toString());
		}
		
	}
	

}
