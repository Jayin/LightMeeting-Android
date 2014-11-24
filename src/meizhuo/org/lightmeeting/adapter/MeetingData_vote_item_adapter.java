package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Option;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MeetingData_vote_item_adapter extends BaseAdapter{

	private Context mContext;
	private List<Option>mData;
	private OnItemClickListener mOnItemClickListener = null;
	
	public MeetingData_vote_item_adapter(Context context,List<Option>data) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder h;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meetingdata_vote_option, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		
		h.vote_option_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mOnItemClickListener.onItemClick(position);
			}
		});
		h.vote_option.setText(position+1 + ":" + mData.get(position).getVpintro());
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.vote_option) TextView vote_option;
		@InjectView(R.id.vote_option_item) LinearLayout vote_option_item;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		this.mOnItemClickListener=listener;
	}
	
	public interface OnItemClickListener{
		/**
		 * 
		 * 
		 * @param position 当前位置
		 * @param view
		 */
		public void onItemClick(int position);
	}

}
