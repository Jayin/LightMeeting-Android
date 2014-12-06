package meizhuo.org.lightmeeting.adapter;

import java.util.ArrayList;
import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.model.Member;
import meizhuo.org.lightmeeting.utils.StringUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MemberAdapter extends BaseAdapter  {
	
	private Context mContext;
	private List<Member>mData;
	private List<Integer>pics = new ArrayList<Integer>();

	public MemberAdapter(Context context,List<Member>data,List<Integer>picss) {
		mContext = context;
		mData = data;
		pics = picss;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder h;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_member_item, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.tv_username.setText(mData.get(position).getNickname());
		if(mData.get(position).getCheckin().equals("1"))
		{
			h.tv_signup.setText("已签到");
			h.tv_signup.setTextColor(mContext.getResources().getColor(R.color.lm_lucency_blue));
		}else
		{
			h.tv_signup.setText("未签到");
			h.tv_signup.setTextColor(mContext.getResources().getColor(R.color.lm_sign_grey));
		}
			h.tv_usercompany.setText(mData.get(position).getCompany());
			h.tv_userposition.setText(mData.get(position).getPosition());
			h.user_img.setImage(pics.get(position));
		return convertView;
	}
	
	
	static class ViewHolder{
		@InjectView(R.id.tv_username) TextView tv_username;
		@InjectView(R.id.tv_signup) TextView tv_signup;
		@InjectView(R.id.tv_usercompany) TextView tv_usercompany;
		@InjectView(R.id.tv_userposition) TextView tv_userposition;
		@InjectView(R.id.user_img) BootstrapCircleThumbnail user_img;
		
		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}
	

}
