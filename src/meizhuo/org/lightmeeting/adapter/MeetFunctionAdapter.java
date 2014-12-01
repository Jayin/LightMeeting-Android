package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Doc;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MeetFunctionAdapter extends BaseAdapter{
	
	List<Doc>mData;
	Context mContext;
	
	public MeetFunctionAdapter(Context context,List<Doc>data) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meetdata, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
			
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.meetdata_title.setText(mData.get(position).getTitle());
		
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.meetdata_title) TextView meetdata_title;
		
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}

}
