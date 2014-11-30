package meizhuo.org.lightmeeting.adapter;

import java.util.HashMap;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.KV;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MeetResearchOptionAdapter extends BaseAdapter {

	private Context mContext;
	private List<KV>mData;
	public static HashMap<Integer, Boolean>isSelected;
	
	public MeetResearchOptionAdapter(Context context,List<KV>data) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mData = data;
//		init();
	}
	
/*	public void init(){
		isSelected = new HashMap<Integer, Boolean>();
		for(int i=0;i<mData.size();i++){
			isSelected.put(i, false);
		}
	}*/
	

	

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
		h.option_key.setText(mData.get(position).getKey() + ":");
		h.option_value.setText(mData.get(position).getValue());
		if(mData.get(position).isIsclick()==false)
		{
			h.option_iv.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
	
	 public class ViewHolder{
		 
		@InjectView(R.id.option_key) public TextView  option_key;
		@InjectView(R.id.option_value) public TextView  option_value;
		@InjectView(R.id.option_iv) public ImageView  option_iv;
		
		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
		
		
	}

}
