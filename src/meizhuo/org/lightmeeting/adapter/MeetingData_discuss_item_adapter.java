package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/***
 * 评论
 * @author Jason
 *
 */
public class MeetingData_discuss_item_adapter extends BaseAdapter{
	
	private Context mContext;
	private List<Comment>mData;
	public MeetingData_discuss_item_adapter(Context context,List<Comment>data) {
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
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meetingdata_discuss_comment, null);
			h=new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.discuss_comment.setText(mData.get(position).getContent());
		
		return convertView;
	}
	
		class ViewHolder{
			@InjectView(R.id.discuss_comment) TextView discuss_comment;
		
		public ViewHolder(View v) {
			// TODO Auto-generated constructor stub
			ButterKnife.inject(this, v);
		}
		
	}


}
