package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.KV;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MeetingData_research_item_option_adapter extends BaseAdapter {

	private Context mContext;
	private List<KV>mData;
	
	public MeetingData_research_item_option_adapter(Context context,List<KV>data) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meetdata_research_item_option_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		/*datamap = mData.get(position);
		
		   Set<Map.Entry<String, String>> entrySet=datamap.entrySet();  
	       for(Map.Entry<String, String> entry: entrySet){  
	        h.option_key.setText(entry.getKey());
	        h.option_value.setText(entry.getValue());
	        L.i("hashkey" + entry.getKey() + "hashvalue" + entry.getValue());
	       }  */
		h.option_key.setText(mData.get(position).getKey());
		h.option_value.setText(mData.get(position).getValue());
		return convertView;
	}
	
	 static class ViewHolder{
		 
		@InjectView(R.id.option_key) TextView  option_key;
		@InjectView(R.id.option_value) TextView  option_value;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
		
	}

}
