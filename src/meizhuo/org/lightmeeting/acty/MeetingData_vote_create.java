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
import meizhuo.org.lightmeeting.api.VoteAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MeetingData_vote_create extends BaseActivity{

	
	@InjectView(R.id.lm_vote_title) EditText lm_vote_title;
	@InjectView(R.id.lm_vote_content) EditText lm_vote_content;
	@InjectView(R.id.lm_vote_starttime_item) LinearLayout lm_vote_starttime_item;
	@InjectView(R.id.lm_vote_endtime_item) LinearLayout lm_vote_endtime_item;
	@InjectView(R.id.lm_vote_add) Button lm_vote_add;
	@InjectView(R.id.lm_vote_starttime) TextView lm_vote_starttime;
	@InjectView(R.id.lm_vote_endtime) TextView lm_vote_endtime;
	
	String str,meetid;
	String title,intro,starttime,endtime;
	LoadingDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.meetdata_vote_create);
		initData();
		initLayout();
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid = getIntent().getStringExtra("meetid");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss  ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		str = formatter.format(curDate);
		starttime = str;
		endtime = str;
		
	}
	@OnClick(R.id.lm_vote_add) public void add_one_vote(){
		if(StringUtils.isEmpty(EditTextUtils.getText(lm_vote_title)))
		{
			toast("投票标题不能为空!");
			return ;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(lm_vote_content)))
		{
			toast("投票内容不能为空!");
			return ;
		}
		title = EditTextUtils.getText(lm_vote_title).toString();
		intro = EditTextUtils.getText(lm_vote_content).toString();
		
		
		VoteAPI.createVote(meetid, title, intro, starttime, endtime, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(dialog == null){
					dialog  = new LoadingDialog(MeetingData_vote_create.this);
					dialog.setText("正在创建投票...");
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
						toast("创建成功!");
						MeetingData_vote_create.this.finish();
						
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
	
	@OnClick(R.id.lm_vote_starttime_item) public void to_selectstarttime(){
		Intent it = new Intent(this, Lm_meeting_selecttime.class);
		it.putExtra("starttime", starttime);
		startActivityForResult(it, 200);
		
	}
	@OnClick(R.id.lm_vote_endtime_item) public void to_selectendtime(){
		Intent it = new Intent(this,Lm_meeting_selecttime_end.class);
		it.putExtra("endtime", endtime);
		startActivityForResult(it, 202);
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_vote_starttime.setText(starttime);
		lm_vote_endtime.setText(endtime);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode ==200 && resultCode == 201){
			 starttime = data.getStringExtra("updatetime");
			 lm_vote_starttime.setText(starttime);
		}
		if(requestCode == 202 && resultCode == 203){
			endtime = data.getStringExtra("updatetime");
			lm_vote_endtime.setText(endtime);
			
		}
	}

}
