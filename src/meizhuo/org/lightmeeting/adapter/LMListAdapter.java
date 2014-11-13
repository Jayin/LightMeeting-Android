package meizhuo.org.lightmeeting.adapter;


import java.util.Arrays;
import java.util.List;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Meeting;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LMListAdapter extends BaseAdapter {
	
	
	List<Meeting>mData;
	private Context mContext;

	public LMListAdapter(Context context, List<Meeting> data) {
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
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder) convertView.getTag();
		}
		h.tv_meeting_title.setText(mData.get(position).getTitle());
		h.tv_meeting_address.setText(mData.get(position).getAddress());
		h.tv_start_time.setText(mData.get(position).getStarttime());
		h.tv_end_time.setText(mData.get(position).getEndtime());
		h.tv_meeting_intro.setText(mData.get(position).getIntro());
		return convertView;
	}
	
	static class ViewHolder {
		@InjectView(R.id.tv_meeting_title) TextView tv_meeting_title;
		@InjectView(R.id.tv_meeting_address) TextView tv_meeting_address;
		@InjectView(R.id.tv_start_time) TextView tv_start_time;
		@InjectView(R.id.tv_end_time) TextView tv_end_time;
		@InjectView(R.id.tv_meeting_intro) TextView tv_meeting_intro;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}

}
