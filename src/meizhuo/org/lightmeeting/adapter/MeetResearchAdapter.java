package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Research;
import meizhuo.org.lightmeeting.utils.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MeetResearchAdapter extends BaseAdapter{
	
	List<Research>mData;
	private Context mContext;
	
	
	public MeetResearchAdapter(Context context,List<Research>data) {
		// TODO Auto-generated constructor stub
		mData = data;
		mContext = context;
		
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_research_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else
		{
			h = (ViewHolder)convertView.getTag();
		}
		h.research_title.setText(mData.get(position).getTitle());
		h.research_intro.setText(mData.get(position).getIntro());
		if(mData.get(position).getStime().equals("0")){
			h.research_starttime.setText("暂无开始时间");	
		}else{
		h.research_starttime.setText(StringUtils.timestampToDate(mData.get(position).getStime()));
		}
		if(mData.get(position).getEtime().equals("0")){
			h.research_endtime.setText("暂无结束时间");
		}else{
		h.research_endtime.setText(StringUtils.timestampToDate(mData.get(position).getEtime()));
		}
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.research_title) TextView research_title;
		@InjectView(R.id.research_intro) TextView  research_intro;
		@InjectView(R.id.research_starttime) TextView  research_starttime;
		@InjectView(R.id.research_endtime) TextView  research_endtime;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
	}
}
