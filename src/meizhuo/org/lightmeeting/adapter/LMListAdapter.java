package meizhuo.org.lightmeeting.adapter;


import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Meeting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class LMListAdapter extends BaseSwipeAdapter  {
	
	
	List<Meeting>mData;
	private Context mContext;
	private OnItemClickListener mOnItemClickListener = null;
	private OnUpdateBtnClickListener mOnUpdateBtnClickListener = null;

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
	public void fillValues(final int position, View convertView) {
		// TODO Auto-generated method stub
		TextView tv_meeting_title = (TextView)convertView.findViewById(R.id.tv_meeting_title);
		TextView tv_meeting_address = (TextView)convertView.findViewById(R.id.tv_meeting_address);
		TextView tv_start_time = (TextView)convertView.findViewById(R.id.tv_start_time);
		TextView tv_end_time = (TextView)convertView.findViewById(R.id.tv_end_time);
		TextView tv_meeting_intro = (TextView)convertView.findViewById(R.id.tv_meeting_intro);
		Button deletebtn = (Button)convertView.findViewById(R.id.delete);
		LinearLayout update_meeting = (LinearLayout)convertView.findViewById(R.id.lm_icon_update_meeting);
		update_meeting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnUpdateBtnClickListener!= null){
					mOnUpdateBtnClickListener.onUpdateClick(position);
				}
				
			}
		});
		
		deletebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnItemClickListener!= null){
					mOnItemClickListener.onItemClick(position);
				}
				
			}
		});
	
		tv_meeting_title.setText(mData.get(position).getTitle() + position);
		tv_meeting_address.setText(mData.get(position).getAddress());
		tv_start_time.setText(mData.get(position).getStarttime());
		tv_end_time.setText(mData.get(position).getEndtime());
		tv_meeting_intro.setText(mData.get(position).getIntro());
		
		
	}
	


	@Override
	public View generateView(int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
		SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
		swipeLayout.addSwipeListener(new SimpleSwipeListener(){
				@Override
				public void onOpen(SwipeLayout layout) {
					// TODO Auto-generated method stub
				}
		});
		swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
			
			@Override
			public void onDoubleClick(SwipeLayout layout, boolean surface) {
				// TODO Auto-generated method stub
			}
		});
		return v;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		// TODO Auto-generated method stub
		return R.id.swipe;
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener = listener;
	}
	
	
	public interface OnItemClickListener{
		public void onItemClick(int position);
	}
	
	public void setOnUpdateBtnClickListener(OnUpdateBtnClickListener listener){
		this.mOnUpdateBtnClickListener = listener;
	}
	
	public interface OnUpdateBtnClickListener{
		public void onUpdateClick(int position);
	}
	
	
	

}
