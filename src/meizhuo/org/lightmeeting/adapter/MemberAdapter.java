package meizhuo.org.lightmeeting.adapter;

import java.util.Arrays;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Member;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MemberAdapter extends BaseAdapter  {
	
	private Context mContext;
	private List<Member>mData;
	

	public MemberAdapter(Context context,List<Member>data) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mData = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
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
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_username.setText(mData.get(position).getNickname());
		
		h.tv_userintro.setText(mData.get(position).getCompany() + mData.get(position).getPosition());
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
