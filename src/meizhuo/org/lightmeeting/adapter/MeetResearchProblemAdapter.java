package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Problem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeetResearchProblemAdapter extends BaseAdapter{
	
	
	private Context mContext;
	private List<Problem>mData;
	
	
	public MeetResearchProblemAdapter(Context context,List<Problem>data) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meetdata_research_problem, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else
		{
			h = (ViewHolder)convertView.getTag();
		}
		h.research_problem.setText(mData.get(position).getTitle());
		return convertView;
	}
	
	static class ViewHolder{
		@InjectView(R.id.research_problem) TextView research_problem;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
	}
	
	

}
