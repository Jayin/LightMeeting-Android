package meizhuo.org.lightmeeting.acty;



import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Member;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BusinessCard extends BaseActivity  {
	
	@InjectView(R.id.lm_member_nickname) TextView member_nickname;
	@InjectView(R.id.lm_member_birth) TextView member_birth;
	@InjectView(R.id.lm_member_sex) TextView member_sex;
	@InjectView(R.id.lm_member_company) TextView member_company;
	@InjectView(R.id.lm_member_position) TextView member_position;
	@InjectView(R.id.lm_member_phone) TextView member_phone;
	@InjectView(R.id.lm_member_email) TextView member_email;
	
	 LoadingDialog loadingdialog;
	Member member;
	ActionBar mActionBar ;
	String nickname,birth,sex,company,position,phone,email;
	boolean isTime=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.fm_buinesscard);
		loadingdialog = new LoadingDialog(this);
		 initData();
		 initLayout();
		 
	}
	
/*	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,);
	
	 return contentView;
	}*/
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.fm_user, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_refreshdata:
			Intent intent = new Intent(this, Update_userdata.class);
			intent.putExtra("nickname", nickname);
			if(isTime)
			{
				L.i("字符串" + birth);
				Long tempbirth = StringUtils.dateToTimestamp2(birth);
				L.i(" lOng" + tempbirth);
				birth = String.valueOf(tempbirth);
			}
			intent.putExtra("birth",StringUtils.timestampToDate2(birth) );
			intent.putExtra("sex", sex);
			intent.putExtra("company", company);
			intent.putExtra("position", position);
			intent.putExtra("phone", phone);
			intent.putExtra("email", email);
			startActivityForResult(intent, 204);
			
			break;
		case R.id.action_changePsw:
			LayoutInflater inflater = LayoutInflater.from(BusinessCard.this);
			View dialogView = inflater.inflate(R.layout.dialog_change_psw, null);
			final EditText et_change_oldpsw = (EditText)dialogView.findViewById(R.id.et_change_oldpsw);
			final EditText et_change_newpsw = (EditText)dialogView.findViewById(R.id.et_change_newpsw);
			final EditText et_confirm_newpsw = (EditText)dialogView.findViewById(R.id.et_change_confirmnewpsw);
			AlertDialog.Builder  builder =  new AlertDialog.Builder(BusinessCard.this);
			builder.setTitle("修改密码");
			builder.setView(dialogView);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if((et_change_oldpsw.getText().toString()) == null || (et_change_oldpsw.getText().toString()).equals(""))
					{
						toast("旧密码不能为空!");
						return ;
					}
					if((et_change_newpsw.getText().toString()) == null || (et_change_newpsw.getText().toString()).equals(""))
					{
						toast("新密码不能为空!");
						return ;
					}
					if((et_change_oldpsw.getText().toString()) == null || (et_change_oldpsw.getText().toString()).equals(""))
					{
						toast("确认新密码不能为空!");
						return ;
					}
					if(!((et_confirm_newpsw.getText().toString()).equals(et_change_newpsw.getText().toString()))){
						toast("两次新密码输入不一致,修改失败!");
						return ;
					}
					UserAPI.changePsw(et_change_oldpsw.getText().toString(), et_change_newpsw.getText().toString(), new JsonHandler(){
						@Override
						public void onStart() {
							if(loadingdialog==null){
								loadingdialog = new LoadingDialog(BusinessCard.this);
								loadingdialog.setText("正在修改密码");
							}
							loadingdialog.show();
						}
						
						@Override
						public void onOK(int statusCode, Header[] headers,
								JSONObject obj) throws Exception {
							new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									((App)getApplication()).cleanUpInfo();
								}
							}).start();
							if(loadingdialog.isShowing()){
								loadingdialog.dismiss();
								loadingdialog = null;
							}
							toast("修改成功,请重新登录");
							sendBroadcast(new Intent(Constants.Action_ChangePSW_Successful));
							openActivity(Login.class);
							BusinessCard.this.finish();
							
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] data, Throwable arg3) {
							if(loadingdialog.isShowing()){
								loadingdialog.dismiss();
								loadingdialog = null;
							}
							toast("网络不给力，请检查你的网络设置!");
						}
					});
				}
			});
			builder.setNegativeButton("取消", null);
			 AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 204 && resultCode == 205){
			member_nickname.setText(data.getStringExtra("nickname"));
			member_birth.setText(data.getStringExtra("birth"));
			if(data.getStringExtra("sex").equals("m")){
				sex = "男";
				member_sex.setText(sex);
			}else{
				sex = "女";
				member_sex.setText(sex);
			}
			member_company.setText(data.getStringExtra("company"));
			member_position.setText(data.getStringExtra("position"));
			member_phone.setText(data.getStringExtra("phone"));
			member_email.setText(data.getStringExtra("email"));
			nickname = data.getStringExtra("nickname");
			birth = data.getStringExtra("birth");
			isTime=true;
			sex = data.getStringExtra("sex");
			company = data.getStringExtra("company");
			position = data.getStringExtra("position");
			phone = data.getStringExtra("phone");
			email = data.getStringExtra("email");
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if(!AndroidUtils.isNetworkConnected(BusinessCard.this))
		{
			toast("请先连接您的网络 !");
			return ;
		}
		UserAPI.getOneMember(new JsonHandler(){
			@Override
			public void onStart() {
				if(loadingdialog==null){
					loadingdialog = new LoadingDialog(BusinessCard.this);
					loadingdialog.setText("正在加载个人信息");
				}
				loadingdialog.show();
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				if(loadingdialog.isShowing())
				{
					loadingdialog.setText("加载完毕");
					loadingdialog.dismiss();
					loadingdialog = null;
				}
				member = Member.create_by_json(obj.getString("response"));
				member_nickname.setText(member.getNickname());
				member_birth.setText(StringUtils.timestampToDate2(member.getBirth()));
				if(member.getSex().equals("m")){
					member_sex.setText("男");
					sex="男";
				}else{
					member_sex.setText("女");
					sex = "女";
				}
				L.i(member.toString());
				member_company.setText(member.getCompany());
				member_position.setText(member.getPosition());
				member_phone.setText(member.getPhone());
				member_email.setText(member.getEmail());
				nickname = member.getNickname();
				birth = member.getBirth();
				company = member.getCompany();
				position = member.getPosition();
				phone = member.getPhone();
				email = member.getEmail();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, data, arg3);
			}
		});
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("个人名片");
		
	}
	
	






}
