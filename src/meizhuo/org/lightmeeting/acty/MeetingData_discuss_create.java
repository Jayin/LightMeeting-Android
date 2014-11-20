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
import meizhuo.org.lightmeeting.utils.L;
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
		if(!AndroidUtils.isNetworkConnected(MeetingData_discuss_create.this)){
			toast("请打开您的网络开关!");
			return;
		}
		title = EditTextUtils.getText(lm_discuss_title).toString();
		content = EditTextUtils.getText(lm_discuss_content).toString();
		if(title == null || title.equals(""))
		{
			toast("讨论标题不能为空!");
			return ;
		}
		if(content == null || content.equals(""))
		{
			toast("讨论内容不能为空!");
			return ;
		}
		DiscussAPI.createDiscuss(meetid, title, content, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(loadingDialog == null){
					loadingDialog = new LoadingDialog(MeetingData_discuss_create.this);
					loadingDialog.setText("正在创建...");
					loadingDialog.show();
				}
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				L.i(obj.toString());
				try {
					if(obj.getString("code").equals("20000")){
						if(loadingDialog.isShowing())
						{
							loadingDialog.dismiss();
							loadingDialog = null;
						}
						toast("创建成功!");
						sendBroadcast(new Intent(Constants.Action_Create_discuss_successful));
						openActivity(MeetingData_discuss.class);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					toast("发生了异常");
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				toast("网络不给力,请检查你的网络设置!");
			}
		});
		
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid = getIntent().getStringExtra("meetid");

	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub

	}

}
