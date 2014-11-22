package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Research;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MeetingData_research_adapter extends BaseAdapter{
	
	List<Research>mData;
	private Context mContext;
	private OnItemClickListener mOnItemClickListener = null;
	private OnUpdateListener mOnUpdateListener = null;
	private OnHandleListener mOnHandleListener = null;
	private OnEditListener mEditListener = null;
	
	
	public MeetingData_research_adapter(Context context,List<Research>data) {
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
		h.research_intro.setText(mData.get(position).getContent());
		
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.research_title) TextView research_title;
		@InjectView(R.id.research_intro) TextView  research_intro;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
	}



	/**
	 * research_title.setText(mData.get(position).getTitle());
		research_intro.setText(mData.get(position).getContent());
	 lv_research_item
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
	
	public void setonEditListener(OnEditListener listener){
		this.mEditListener = listener;
	}
	
	
	public interface OnEditListener{
		
		public void onEditListener(int position);
	}


}
