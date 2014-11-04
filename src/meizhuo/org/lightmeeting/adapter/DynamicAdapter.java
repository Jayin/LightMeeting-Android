package meizhuo.org.lightmeeting.adapter;

import java.util.Arrays;
import java.util.List;

import meizhuo.org.lightmeeting.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DynamicAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<String>namelist;
	private List<String>contentlist;
	
	public DynamicAdapter(Context context,String[] names,String[] contents) {
		// TODO Auto-generated constructor stub
		mContext = context;
		namelist = Arrays.asList(names);
		contentlist = Arrays.asList(contents);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return namelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return namelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder h;
		if(convertView == null){
			convertView  = LayoutInflater.from(mContext).inflate(R.layout.lv_dynamic_item, null); 
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.dynamic_user_name.setText(namelist.get(position));
		h.dynamic_user_content.setText(contentlist.get(position));
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.dynamic_user_name)  TextView dynamic_user_name;
		@InjectView(R.id.dynamic_user_content) TextView dynamic_user_content;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}

}
