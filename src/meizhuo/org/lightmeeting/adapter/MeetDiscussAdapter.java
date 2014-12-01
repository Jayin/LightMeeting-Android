package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Discuss;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MeetDiscussAdapter extends BaseAdapter{
	
	List<Discuss>mData;
	private Context mContext;

	public MeetDiscussAdapter(Context context,List<Discuss>data) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_discuss_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else
		{
			h = (ViewHolder)convertView.getTag();
		}
		h.discuss_title.setText(mData.get(position).getTitle());
		h.discuss_content.setText(mData.get(position).getContent());
		return convertView;
	}

	
	static class ViewHolder {
		@InjectView(R.id.discuss_title) TextView discuss_title;
		@InjectView(R.id.discuss_content) TextView  discuss_content;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
	}


	
	
	

}
