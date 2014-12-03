package meizhuo.org.lightmeeting.adapter;

import java.util.List;


import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Relation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RelationListAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<Relation>mData;
	private OnItemClickListener mOnItemClickListener = null;
	
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
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder h;
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_relation, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_relation_nickname.setText(mData.get(position).getNickname());
		if(mData.get(position).getCompany() == null || mData.get(position).getCompany().equals("")){
			h.tv_relation_company.setText("暂无公司信息");
		}else{
			h.tv_relation_company.setText(mData.get(position).getCompany());
		}
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.tv_relation_nickname) TextView tv_relation_nickname;
		@InjectView(R.id.tv_relation_company) TextView tv_relation_company;
		
		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}
	
	public interface OnItemClickListener {
		/**
		 * 
		 * @param position
		 *            当前位置
		 * @param view
		 *            点击的那个view
		 */
		public void onItemClick(int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}
	

}
