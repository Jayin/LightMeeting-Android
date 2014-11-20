package meizhuo.org.lightmeeting.acty;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.os.Bundle;
import android.widget.EditText;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.VoteAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MeetingData_vote_update extends BaseActivity {
	ActionBar mActionBar;
	
	@InjectView(R.id.lm_vote_title_update) EditText lm_vote_title_update;
	@InjectView(R.id.lm_vote_content_update) EditText lm_vote_content_update;
	String voteid,title,intro;
	
	LoadingDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.meetdata_vote_update);
		
		initData();
		initLayout();
	}
	
	@OnClick(R.id.lm_vote_update) public void update_vote(){
		if(!AndroidUtils.isNetworkConnected(MeetingData_vote_update.this)){
			toast("请打开您的网络开关!");
			return;
		}
		title = EditTextUtils.getText(lm_vote_title_update).toString();
		intro = EditTextUtils.getText(lm_vote_content_update).toString();
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		voteid = getIntent().getStringExtra("voteid");
		title = getIntent().getStringExtra("title");
		intro = getIntent().getStringExtra("intro");
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_vote_title_update.setText(title);
		lm_vote_content_update.setText(intro);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

}
