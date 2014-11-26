package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.InjectView;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Member;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MdMemberBusinessCard extends BaseActivity{

	ActionBar mActionBar;
	String memberid,mNickname;
	
	@InjectView(R.id.mt_member_nickname) TextView mt_member_nickname;
	@InjectView(R.id.mt_member_birth) TextView mt_member_birth;
	@InjectView(R.id.mt_member_sex) TextView mt_member_sex;
	@InjectView(R.id.mt_member_company) TextView mt_member_company;
	@InjectView(R.id.mt_member_position) TextView mt_member_position;
	@InjectView(R.id.mt_member_phone) TextView mt_member_phone;
	@InjectView(R.id.mt_member_email) TextView mt_member_email;
	
	String nickname,birth,sex,company,position,phone,email;
	LoadingDialog loadingDialog;
	Member member;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.mt_member_businesscard);
		loadingDialog = new LoadingDialog(this);
		 initData();
//		 initLayout();
		 
	}
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		memberid = getIntent().getStringExtra("memberid");
		mNickname = getIntent().getStringExtra("nickname");
		MeetingAPI.getOneMember(memberid, new JsonHandler(){
				@Override
				public void onStart() {
					loadingDialog.setText("正在加载个人名片...");
					loadingDialog.show();
				}
				@Override
				public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
					
					if(loadingDialog.isShowing())
					{
						loadingDialog.setText("加载完成");
						loadingDialog.dismiss();
					}
					member = Member.create_by_json(obj.getString("response"));
					mt_member_nickname.setText(member.getNickname());
					mt_member_birth.setText(StringUtils.timestampToDate(member.getBirth()));
					if(member.getSex().equals("m"))
					{
						mt_member_sex.setText("男");
					}else
					{
						mt_member_sex.setText("女");
					}
					mt_member_company.setText(member.getCompany());
					mt_member_position.setText(member.getPosition());
					mt_member_phone.setText(member.getPhone());
					mt_member_email.setText(member.getEmail());
					
					mActionBar = getActionBar();
					mActionBar.setDisplayHomeAsUpEnabled(true);
					mActionBar.setTitle(mNickname + "名片");
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
					if(loadingDialog.isShowing())
					{
						loadingDialog.dismiss();
					}
					toast("网络不给力吗,请检查你的网络设置!");
				}
				
		});
	}

	/**
	 @InjectView(R.id.mt_member_nickname) TextView mt_member_nickname;
	@InjectView(R.id.mt_member_birth) TextView mt_member_birth;
	@InjectView(R.id.mt_member_sex) TextView mt_member_sex;
	@InjectView(R.id.mt_member_company) TextView mt_member_company;
	@InjectView(R.id.mt_member_position) TextView mt_member_position;
	@InjectView(R.id.mt_member_phone) TextView mt_member_phone;
	@InjectView(R.id.mt_member_email) TextView mt_member_email;
	 */
	@Override
	protected void initLayout() {

		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(mNickname + "名片");
	
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			
		}
		return super.onOptionsItemSelected(item);
	}
		

}
