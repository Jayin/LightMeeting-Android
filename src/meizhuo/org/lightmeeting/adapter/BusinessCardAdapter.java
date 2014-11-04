package meizhuo.org.lightmeeting.adapter;

import java.util.Arrays;
import java.util.List;

import meizhuo.org.lightmeeting.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusinessCardAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<String>mRight_data;
	private List<String>mLeft_data;
	 

	public BusinessCardAdapter(Context context,String[] bus_left,String[] bus_right ) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mLeft_data = Arrays.asList(bus_left);
		mRight_data = Arrays.asList(bus_right);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLeft_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mLeft_data.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_businesscard_item, null);
			h=new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_card_left.setText(mLeft_data.get(position));
		h.tv_card_right.setText(mRight_data.get(position));
		return convertView;
	}
	
	static class ViewHolder{
		
		@InjectView(R.id.lv_business_left) TextView tv_card_left;
		@InjectView(R.id.lv_business_right) TextView tv_card_right;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}
	
	

}
