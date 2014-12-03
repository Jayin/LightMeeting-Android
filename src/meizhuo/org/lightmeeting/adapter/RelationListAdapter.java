package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Relation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RelationListAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<Relation>mData;
	
	
	public RelationListAdapter(Context context,List<Relation>list) {
		mContext = context;
		mData = list;
		
	}
	

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder h;
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_relation, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_relation_nickname.setText(mData.get(position).getNickname());
		if(mData.get(position).getSex().equals("m"))
		{
			h.tv_relation_nickname.setText("男");
		}else{
			h.tv_relation_nickname.setText("女");
		}
		h.tv_relation_company.setText(mData.get(position).getCompany());
		h.tv_relation_position.setText(mData.get(position).getPosition());
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.tv_relation_nickname) TextView tv_relation_nickname;
		@InjectView(R.id.tv_relation_sex) TextView tv_relation_sex;
		@InjectView(R.id.tv_relation_company) TextView tv_relation_company;
		@InjectView(R.id.tv_relation_position) TextView tv_relation_position;
		
		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}

}
