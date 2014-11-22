package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.LMListAdapter.ViewHolder;
import meizhuo.org.lightmeeting.model.Vote;
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

public class MeetingData_vote_adapter extends BaseAdapter{

	
	List<Vote>mData;
	private Context mContext;
	private OnItemClickListener mOnItemClickListener = null;
	private OnUpdateListener mOnUpdateListener = null;
	private OnHandleListener mOnHandleListener = null;
	private OnEditListener mOnEditListener = null;

	public MeetingData_vote_adapter(Context context,List<Vote>data) {
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
		h.vote_starttime.setText(mData.get(position).getStime());
		h.vote_end_time.setText(mData.get(position).getEtime());
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


	
	/**
	 * lv_vote_item
	 	vote_title.setText(mData.get(position).getTitle());
		vote_intro.setText(mData.get(position).getIntro());
		vote_starttime.setText(mData.get(position).getStime());
		vote_end_time.setText(mData.get(position).getEtime());
	 * @param listener
	 */
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener = listener;
	}
	
	
	public interface OnItemClickListener{
		public void onItemClick(int position);
	}
	
	
	public void setOnUpdateListener(OnUpdateListener listener){
		this.mOnUpdateListener = listener;
	}
	
	
	public interface OnUpdateListener{
		public void onUpdateListener(int position);
	}
	
	public void setOnHandleListener(OnHandleListener listener){
		this.mOnHandleListener = listener;
	}

	public interface OnHandleListener{
		public void onHandlerListener(int position);
	}
	
	
	public void setOnEditListener(OnEditListener listener){
		this.mOnEditListener = listener;
	}
	
	public interface OnEditListener{
		public void onEditListener(int position);
	}
	
}
