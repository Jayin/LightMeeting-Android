package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.EditTextUtils;

public class MeetingData_discuss_item_tocomment extends BaseActivity{

	String discussid,reply_member = "";
	
	@InjectView(R.id.et_comment) EditText et_comment;
	@InjectView(R.id.to_comment) LinearLayout to_comment;
	ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item_tocomment);
		
		initData();
		initLayout();
	}
	
	@OnClick(R.id.to_comment) public void comment(){
		String comment_content = EditTextUtils.getText(et_comment);
		DiscussAPI.createComment(discussid, comment_content, reply_member, new JsonResponseHandler() {
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						toast("评论成功!");
						MeetingData_discuss_item_tocomment.this.finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
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
