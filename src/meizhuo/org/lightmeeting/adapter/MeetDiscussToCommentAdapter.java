package meizhuo.org.lightmeeting.adapter;

import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MdMemberBusinessCard;
import meizhuo.org.lightmeeting.model.Comment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

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

	@Override public View getView(final int position, View convertView,
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
		h.avatar.setOnClickListener(new OnClickListener() {
			
			@Override public void onClick(View v) {
				Intent it = new Intent(mContext, MdMemberBusinessCard.class);
				it.putExtra("memberid", mData.get(position).getAuthor());
				it.putExtra("nickname", mData.get(position).getNickname());
				mContext.startActivity(it);
			}
		});

		return convertView;
	}

	class ViewHolder {
		@InjectView(R.id.discuss_comment) TextView discuss_comment;
		@InjectView(R.id.comment_author) TextView comment_author;
		@InjectView(R.id.chat_person_headershot) BootstrapCircleThumbnail avatar;

		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}
}
