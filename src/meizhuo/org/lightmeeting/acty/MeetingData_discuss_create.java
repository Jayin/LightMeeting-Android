package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MeetingData_discuss_create extends BaseActivity {
	
	ActionBar mActionBar;
	@InjectView(R.id.lm_discuss_title_create) EditText lm_discuss_title;
	@InjectView(R.id.lm_discuss_content_create) EditText lm_discuss_content;
	
	String meetid,title,content;
	
	LoadingDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.meetdata_discuss_create);
		initData();
		initLayout();
	}
	
	@OnClick(R.id.lm_discuss_create) public void create_discuss(){
		if(AndroidUtils.isNetworkConnected(MeetingData_discuss_create.this)){
			toast("请打开您的网络开关!");
			return;
		}
		title = EditTextUtils.getText(lm_discuss_title).toString();
		content = EditTextUtils.getText(lm_discuss_content).toString();
		DiscussAPI.createDiscuss(meetid, title, content, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(loadingDialog == null){
					loadingDialog.setText("正在创建...");
					loadingDialog.show();
				}
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						toast("创建成功!");
						sendBroadcast(new Intent(Constants.Action_Create_discuss_successful));
						openActivity(MeetingData_discuss.class);
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
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
