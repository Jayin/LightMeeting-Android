package meizhuo.org.lightmeeting.adapter;


import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Meeting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class LMListAdapter extends BaseSwipeAdapter  {
	
	
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
	public void fillValues(int position, View convertView) {
		// TODO Auto-generated method stub
/*		ViewHolder h;
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder) convertView.getTag();
		}
		L.i(mData.toString());*/
		TextView tv_meeting_title = (TextView)convertView.findViewById(R.id.tv_meeting_title);
		TextView tv_meeting_address = (TextView)convertView.findViewById(R.id.tv_meeting_address);
		TextView tv_start_time = (TextView)convertView.findViewById(R.id.tv_start_time);
		TextView tv_end_time = (TextView)convertView.findViewById(R.id.tv_end_time);
		TextView tv_meeting_intro = (TextView)convertView.findViewById(R.id.tv_meeting_intro);
	
		tv_meeting_title.setText(mData.get(position).getTitle());
		tv_meeting_address.setText(mData.get(position).getAddress());
		tv_start_time.setText(mData.get(position).getStarttime());
		tv_end_time.setText(mData.get(position).getEndtime());
		tv_meeting_intro.setText(mData.get(position).getIntro());
		
	}
/*	static class ViewHolder {
		@InjectView(R.id.tv_meeting_title) TextView tv_meeting_title;
		@InjectView(R.id.tv_meeting_address) TextView tv_meeting_address;
		@InjectView(R.id.tv_start_time) TextView tv_start_time;
		@InjectView(R.id.tv_end_time) TextView tv_end_time;
		@InjectView(R.id.tv_meeting_intro) TextView tv_meeting_intro;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}*/
	/*	@Override
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
	}*/
	


	@Override
	public View generateView(int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
		SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
		swipeLayout.setDragEdge(SwipeLayout.DragEdge.Left);
		swipeLayout.addSwipeListener(new SimpleSwipeListener(){
				@Override
				public void onOpen(SwipeLayout layout) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "drawer", Toast.LENGTH_SHORT).show();
				}
		});
		swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
			
			@Override
			public void onDoubleClick(SwipeLayout layout, boolean surface) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		// TODO Auto-generated method stub
		return R.id.swipe;
	}

}
