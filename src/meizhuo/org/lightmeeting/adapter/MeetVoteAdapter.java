package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetListAdapter.ViewHolder;
import meizhuo.org.lightmeeting.model.Vote;
import meizhuo.org.lightmeeting.utils.StringUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class MeetVoteAdapter extends BaseAdapter{

	
	List<Vote>mData;
	private Context mContext;

	public MeetVoteAdapter(Context context,List<Vote>data) {
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
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_vote_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else
		{
			h = (ViewHolder)convertView.getTag();
		}
		h.vote_title.setText(mData.get(position).getTitle());
		h.vote_intro.setText(mData.get(position).getIntro());
		if(mData.get(position).getStime().equals("0")){
			h.vote_starttime.setText("暂无开始时间");
		}else{
		h.vote_starttime.setText(StringUtils.timestampToDate(mData.get(position).getStime()));
		}
		if(mData.get(position).getEtime().equals("0")){
			h.vote_end_time.setText("暂无结束时间");
		}{
		h.vote_end_time.setText(StringUtils.timestampToDate(mData.get(position).getEtime()));
		}
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.vote_title) TextView vote_title;
		@InjectView(R.id.vote_intro) TextView  vote_intro;
		@InjectView(R.id.vote_starttime) TextView vote_starttime;
		@InjectView(R.id.vote_end_time) TextView vote_end_time;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
	}
	
}
