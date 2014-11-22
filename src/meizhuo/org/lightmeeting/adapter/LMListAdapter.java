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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class LMListAdapter extends BaseAdapter  {
	
	
	List<Meeting>mData;
	private Context mContext;
	private OnItemClickListener mOnItemClickListener = null;
	private OnUpdateListener mOnUpdateListener = null;
	private OnHandleListener mOnHandleListener = null;
	private OnEditListener mEditListener = null;

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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder h;
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_lmlist_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else
		{
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_meeting_title.setText(mData.get(position).getTitle());
		h.tv_meeting_address.setText(mData.get(position).getAddress());
		h.tv_meeting_intro.setText(mData.get(position).getIntro());
		h.tv_start_time.setText(mData.get(position).getStarttime());
		h.tv_end_time.setText(mData.get(position).getEndtime());
		
		return convertView;
	}
	
	static class ViewHolder {
		@InjectView(R.id.tv_meeting_title) TextView tv_meeting_title;
		@InjectView(R.id.tv_meeting_address) TextView  tv_meeting_address;
		@InjectView(R.id.tv_meeting_intro) TextView tv_meeting_intro;
		@InjectView(R.id.tv_start_time) TextView tv_start_time;
		@InjectView(R.id.tv_end_time) TextView tv_end_time;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
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
	
	
	public void setOnUpdateListener(OnUpdateListener listener){
		this.mOnUpdateListener = listener;
	}
	
	
	public interface OnUpdateListener{
		public void onUpdateListener(int position);
	}

}
