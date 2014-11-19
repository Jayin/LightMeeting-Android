package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MeetingData_discuss;
import meizhuo.org.lightmeeting.acty.MeetingData_vote;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.InjectView;
import butterknife.OnClick;

public class Meeting_function_fm extends BaseFragment {


	/**讨论*/
	@InjectView(R.id.meeting_function_discuss) LinearLayout meeting_function_discuss;
	/**投票*/
	@InjectView(R.id.meeting_function_vote) LinearLayout meeting_function_vote;
	/**调查*/
	@InjectView(R.id.meeting_function_research) LinearLayout meeting_function_research;
	
	String meetid;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_meeting_function);
	 Bundle idbundle = getArguments();
	 meetid = idbundle.getString("meetid");
	 
	 return contentView;
	}
	
	@OnClick(R.id.meeting_function_discuss) public void to_discuss(){
		Intent it = new Intent(getActivity(), MeetingData_discuss.class);
		it.putExtra("meetid", meetid);
		startActivity(it);
	}
	
	@OnClick(R.id.meeting_function_vote) public void to_vote(){
		Intent it = new Intent(getActivity(), MeetingData_vote.class);
		it.putExtra("meetid", meetid);
		startActivity(it);
		
		
	}
	
	@OnClick(R.id.meeting_function_research) public void to_research(){
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}
	


}
