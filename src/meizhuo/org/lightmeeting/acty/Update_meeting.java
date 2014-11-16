package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class Update_meeting extends BaseActivity {
	
	String meetid,title,intro,address,starttime,endtime;
	@InjectView(R.id.lm_meeting_title) EditText lm_meeting_title;
	@InjectView(R.id.lm_meeting_address) EditText lm_meeting_address;
	@InjectView(R.id.lm_meeting_intro) EditText lm_meeting_intro;
	@InjectView(R.id.lm_meeting_starttime_item) LinearLayout lm_meeting_starttime_item;
	@InjectView(R.id.lm_meeting_endtime_item) LinearLayout lm_meeting_endtime_item;
	@InjectView(R.id.lm_meeting_update) Button lm_meeting_update;
	@InjectView(R.id.lm_meeting_starttime) TextView lm_meeting_starttime;
	@InjectView(R.id.lm_meeting_endtime) TextView lm_meeting_endtime;
	LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lm_list_updatemeeting);
		initData();
		initLayout();
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid = getIntent().getStringExtra("id");
		title = getIntent().getStringExtra("title");
		intro = getIntent().getStringExtra("intro");
		address = getIntent().getStringExtra("address");
		starttime = getIntent().getStringExtra("starttime");
		endtime = getIntent().getStringExtra("endtime");
		

	}

	/**
	 @InjectView(R.id.lm_meeting_title) EditText lm_meeting_title;
	@InjectView(R.id.lm_meeting_address) EditText lm_meeting_address;
	@InjectView(R.id.lm_meeting_intro) EditText lm_meeting_intro;
	@InjectView(R.id.lm_meeting_starttime_item) LinearLayout lm_meeting_starttime_item;
	@InjectView(R.id.lm_meeting_endtime_item) LinearLayout lm_meeting_endtime_item;
	@InjectView(R.id.lm_meeting_update) Button lm_meeting_update;
	@InjectView(R.id.lm_meeting_starttime) TextView lm_meeting_starttime;
	@InjectView(R.id.lm_meeting_endtime) TextView lm_meeting_endtime;
	 */
	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_meeting_title.setText(title);
		lm_meeting_address.setText(address);
		lm_meeting_intro.setText(intro);
		lm_meeting_starttime.setText(starttime);
		lm_meeting_endtime.setText(endtime);
	}
	
	/**
	 	@InjectView(R.id.lm_meeting_starttime_item) LinearLayout lm_meeting_starttime_item;
	@InjectView(R.id.lm_meeting_endtime_item) LinearLayout lm_meeting_endtime_item;
	 */
	/**
	 * 选择会议开始时间
	 */
	@OnClick(R.id.lm_meeting_starttime_item) public void select_starttiem(){
		Intent it = new Intent(this, Lm_meeting_selecttime.class);
		it.putExtra("starttime", starttime);
		sendBroadcast(new Intent(Constants.Action_Update_Start_Time));
		startActivityForResult(it, 100);
	}
	@OnClick(R.id.lm_meeting_endtime_item) public void select_endtime(){
		Intent it = new Intent(this,Lm_meeting_selecttime.class);
		it.putExtra("endtime", endtime);
		sendBroadcast(new Intent(Constants.Action_Update_End_Time));
		startActivityForResult(it, 1000);
		
	}
	
	@OnClick(R.id.lm_meeting_update) public void update_meeting(){
		title = EditTextUtils.getText(lm_meeting_title).toString();
		address = EditTextUtils.getText(lm_meeting_address).toString();
		intro = EditTextUtils.getText(lm_meeting_intro).toString();
		starttime = "2014-11-16 18:30";
		endtime = "2014-11-17 18:30";		
		MeetingAPI.updateMeeting(meetid, title, intro, address, starttime, endtime, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(dialog == null){
					dialog = new LoadingDialog(Update_meeting.this);
					dialog.setText("正在更新!");
					dialog.show();
				}
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						if(dialog.isShowing()){
							dialog.dismiss();
							dialog = null;
						}
						toast("更新成功!");
						Update_meeting.this.finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				if(dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}
				toast("网络不给力,请检查你的网络设置!");
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 100 && resultCode == 101){
			String updateTime = data.getStringExtra("updatetime");
			lm_meeting_starttime.setText(updateTime);
		}
		if(requestCode == 1000 && requestCode == 1001){
			String updateTime = data.getStringExtra("updatetime");
			lm_meeting_endtime.setText(updateTime);
		}
	}
	
	

}
