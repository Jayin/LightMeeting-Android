package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnEditListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnHandleListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnItemClickListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter.OnUpdateListener;
import meizhuo.org.lightmeeting.model.Research;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

public class MeetingData_research_adapter extends BaseSwipeAdapter{
	
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
	public void fillValues(final int position, View convertView) {
		// TODO Auto-generated method stub
		TextView research_title = (TextView)convertView.findViewById(R.id.discuss_title);
		TextView research_intro = (TextView)convertView.findViewById(R.id.research_intro);
		ImageView deletebtn = (ImageView)convertView.findViewById(R.id.delete);
		deletebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnItemClickListener!= null){
					mOnItemClickListener.onItemClick(position);
				}
				
			}
		});
		research_title.setText(mData.get(position).getTitle());
		research_intro.setText(mData.get(position).getContent());
		
	}

	@Override
	public View generateView(final int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(mContext).inflate(R.layout.lv_research_item, null);
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
					if(mOnHandleListener!= null){
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
		return R.id.swipe4;
	}
	
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
