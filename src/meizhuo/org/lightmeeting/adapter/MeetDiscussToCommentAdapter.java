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
 * 
 * @author Jason
 * 
 */
public class MeetDiscussToCommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<Comment> mData;

	public MeetDiscussToCommentAdapter(Context context, List<Comment> data) {
		mContext = context;
		mData = data;

	}

	@Override public int getCount() {
		return mData.size();
	}

	@Override public Object getItem(int position) {
		return mData.get(position);
	}

	@Override public long getItemId(int position) {
		return position;
	}

	@Override public View getView(int position, View convertView,
			ViewGroup parent) {
		ViewHolder h;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_meetingdata_discuss_comment, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		} else {
			h = (ViewHolder) convertView.getTag();
		}
		h.discuss_comment.setText(mData.get(position).getContent());
		h.comment_author.setText(mData.get(position).getNickname());

		return convertView;
	}

	class ViewHolder {
		@InjectView(R.id.discuss_comment) TextView discuss_comment;
		@InjectView(R.id.comment_author) TextView comment_author;

		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}
}
