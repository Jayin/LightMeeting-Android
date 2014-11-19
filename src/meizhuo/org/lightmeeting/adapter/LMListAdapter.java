package meizhuo.org.lightmeeting.adapter;


import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnEditListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnHandleListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnUpdateListener;
import meizhuo.org.lightmeeting.model.Meeting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
	private OnUpdateListener mOnUpdateListener = null;
	private OnHandleListener mOnHandleListener = null;
	private OnEditListener mEditListener = null;
	private OnSweepListener mOnSweepListener = null;

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
		ImageView deletebtn = (ImageView)convertView.findViewById(R.id.delete);
		ImageView sweep_code = (ImageView)convertView.findViewById(R.id.sweep_code);
		ImageView update = (ImageView)convertView.findViewById(R.id.update);
		
		deletebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnItemClickListener!= null){
					mOnItemClickListener.onItemClick(position);
				}
				
			}
		});
		sweep_code.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnSweepListener != null){
					mOnSweepListener.onSweepListener(position);
				}
				
			}
		});
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mEditListener != null){
					mEditListener.onEditListener(position);
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
	public View generateView(final int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
		SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
		swipeLayout.addSwipeListener(new SimpleSwipeListener(){
				@Override
				public void onOpen(SwipeLayout layout) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onUpdate(SwipeLayout layout, int leftOffset,
					int topOffset) {
				// TODO Auto-generated method stub
					if(mOnUpdateListener != null){
						mOnUpdateListener.onUpdateListener(position);
					}
				super.onUpdate(layout, leftOffset, topOffset);
				}
				
				@Override
				public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
				// TODO Auto-generated method stub
					if(mOnHandleListener != null){
						mOnHandleListener.onHandlerListener(position);
					}
				super.onHandRelease(layout, xvel, yvel);
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
	
	
	
	public void setOnHandleListener(OnHandleListener listener){
		this.mOnHandleListener = listener;
	}
	
	public interface OnHandleListener{
		public void onHandlerListener(int position);
	}
	
	public void setonEditListener(OnEditListener listener){
		this.mEditListener = listener;
	}
	
	
	public interface OnEditListener{
		
		public void onEditListener(int position);
	}
	
	public void setOnSweepListener(OnSweepListener listener){
		this.mOnSweepListener = listener;
	}
	
	
	public interface OnSweepListener{
		public void onSweepListener(int position);
	}
	
	public void setOnUpdateListener(OnUpdateListener listener){
		this.mOnUpdateListener = listener;
	}
	
	
	public interface OnUpdateListener{
		public void onUpdateListener(int position);
	}

}
