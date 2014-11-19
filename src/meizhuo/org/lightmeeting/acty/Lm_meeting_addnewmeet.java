package meizhuo.org.lightmeeting.acty;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class Lm_meeting_addnewmeet extends BaseActivity {
	
	@InjectView(R.id.lm_meeting_title) EditText lm_meeting_title;
	@InjectView(R.id.lm_meeting_address) EditText lm_meeting_address;
	@InjectView(R.id.lm_meeting_intro) EditText lm_meeting_intro;
	@InjectView(R.id.lm_meeting_starttime_item) LinearLayout lm_meeting_starttime_item;
	@InjectView(R.id.lm_meeting_endtime_item) LinearLayout lm_meeting_endtime_item;
	@InjectView(R.id.lm_meeting_add) Button lm_meeting_add;
	@InjectView(R.id.lm_meeting_starttime) TextView lm_meeting_starttime;
	@InjectView(R.id.lm_meeting_endtime) TextView lm_meeting_endtime;
	
	String str;
	String title,address,intro,starttime,endtime;
	LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lmlist_addnewmeeting);
		initData();
		initLayout();
	}
	
	@OnClick(R.id.lm_meeting_add) public void add_one_meeting(){
		if(StringUtils.isEmpty(EditTextUtils.getText(lm_meeting_title)))
		{
			toast("会议标题不能为空!");
			return ;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(lm_meeting_address)))
		{
			toast("会议地址不能为空!");
			return ;
		}
		title = EditTextUtils.getText(lm_meeting_title).toString();
		address = EditTextUtils.getText(lm_meeting_address).toString();
		intro = EditTextUtils.getText(lm_meeting_intro).toString();
		
		
		MeetingAPI.addMeeting(title, intro, address, starttime, endtime, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(dialog == null){
					dialog  = new LoadingDialog(Lm_meeting_addnewmeet.this);
					dialog.setText("正在添加会议...");
					dialog.show();
				}
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				L.i(obj.toString());
				try {
					if(obj.getString("code").equals("20000")){
						if(dialog.isShowing()){
							dialog.dismiss();
							dialog = null;
						}
						openActivity(MainActivity.class);
						Lm_meeting_addnewmeet.this.finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					L.i(e.getMessage());
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
				toast("网络不给力,会议添加失败!");
				return ;
			}
		});
		
	}
	
	@OnClick(R.id.lm_meeting_starttime) public void to_selectstarttime(){
		Intent it = new Intent(this, Lm_meeting_selecttime.class);
		it.putExtra("starttime", starttime);
		startActivityForResult(it, 20);
		
	}
	@OnClick(R.id.lm_meeting_endtime) public void to_selectendtime(){
		Intent it = new Intent(this,Lm_meeting_selecttime_end.class);
		it.putExtra("endtime", endtime);
		startActivityForResult(it, 30);
		
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss  ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		str = formatter.format(curDate);
		starttime = str;
		endtime = str;

	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_meeting_starttime.setText(starttime);
		lm_meeting_endtime.setText(endtime);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode ==20 && resultCode == 21){
			 starttime = data.getStringExtra("updatetime");
			lm_meeting_starttime.setText(starttime);
		}
		if(requestCode == 30 && resultCode == 31){
			endtime = data.getStringExtra("updatetime");
			lm_meeting_endtime.setText(endtime);
			
		}
	}

}
