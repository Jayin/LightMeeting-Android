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

public class MemberAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<String>username_list;
	private List<String>userintro_list;

	public MemberAdapter(Context context, String[] usernames,String[] userintros) {
		// TODO Auto-generated constructor stub
		mContext = context;
		userintro_list = Arrays.asList(userintros);
		username_list = Arrays.asList(usernames);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return username_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return username_list.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_member_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h  =(ViewHolder)convertView.getTag();
		}
		h.tv_username.setText(username_list.get(position));
		h.tv_userintro.setText(userintro_list.get(position));
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.tv_username) TextView tv_username;
		@InjectView(R.id.tv_userintro) TextView tv_userintro;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}

}
