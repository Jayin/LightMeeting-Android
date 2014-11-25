package meizhuo.org.lightmeeting.acty;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.EditTextUtils;

/**
 * 评论
 * @author Jason
 *
 */
public class MeetingData_discuss_item_tocomment extends BaseActivity{

	String discussid,reply_member = "";
	
	@InjectView(R.id.et_comment) EditText et_comment;
	@InjectView(R.id.to_comment) LinearLayout to_comment;
	ActionBar mActionBar;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item_tocomment);
		
		initData();
		initLayout();
	}
	
	
	
	
	@OnClick(R.id.to_comment) public void comment(){
		String comment_content = EditTextUtils.getText(et_comment);
		if(comment_content.equals("")||comment_content == null)
		{
			toast("评论内容不能为空!");
			return ;
			
		}
		DiscussAPI.createComment(discussid, comment_content, reply_member, new JsonHandler(){
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				toast("评论成功!");
				sendBroadcast(new Intent(Constants.Action_Comment_Successful));
				MeetingData_discuss_item_tocomment.this.finish();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				toast("网络不给力，评论失败!请重新评论!");
				return ;
			}
			
			
		});
	
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		discussid  = getIntent().getStringExtra("discussid");
		
	}


	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		et_comment.setFocusable(true);
		et_comment.setFocusableInTouchMode(true);
		et_comment.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputManager =  
		                 (InputMethodManager)et_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		             inputManager.showSoftInput(et_comment, 0);  
				
			}
		}, 500);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("请写下您的评论");
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	
	

}
