package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DialerFilter;
import android.widget.EditText;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MeetingData_discuss_update extends BaseActivity {

	ActionBar mActionBar;
	
	@InjectView(R.id.lm_discuss_title_update) EditText lm_discuss_title;
	@InjectView(R.id.lm_discuss_content_update) EditText lm_discuss_content;
	String discussid,title,content;
	
	LoadingDialog loadingDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.meetdata_discuss_update);
		
		initData();
		initLayout();
	}
	
	
	@OnClick(R.id.lm_discuss_update) public void update_discuss(){
		if(!AndroidUtils.isNetworkConnected(MeetingData_discuss_update.this)){
			toast("请打开您的网络开关!");
			return;
		}
		title = EditTextUtils.getText(lm_discuss_title).toString();
		content = EditTextUtils.getText(lm_discuss_content).toString();
		DiscussAPI.updateDiscuss(discussid, title, content, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(loadingDialog == null){
					loadingDialog = new LoadingDialog(MeetingData_discuss_update.this);
					loadingDialog.setText("正在更新讨论...");
					loadingDialog.show();
				}
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				L.i(obj.toString());
				try {
					if(obj.getString("code").equals("20000")){
						toast("更新讨论成功!");
						sendBroadcast(new Intent(Constants.Action_Update_discuss_successful));
						openActivity(MeetingData_discuss.class);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				toast("网络不给力,请检查你的网络设置!");
				
			}
		});
		
		
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		discussid = getIntent().getStringExtra("discussid");
		title = getIntent().getStringExtra("title");
		content = getIntent().getStringExtra("content");

	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_discuss_title.setText(title);
		lm_discuss_content.setText(content);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

	}

}
