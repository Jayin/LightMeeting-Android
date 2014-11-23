package meizhuo.org.lightmeeting.acty;



import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	BCHandler handler = new BCHandler();
	DialogHandler dialogHandler =new DialogHandler(); 
	User member;
	ActionBar mActionBar ;
	String nickname,birth,sex,company,position,phone,email;
	
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
	
	
	
	class BCHandler extends Handler{
		LoadingDialog dialog;
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.Start:
				if(dialog == null)
				{
					dialog = new LoadingDialog(BusinessCard.this);
				}
				dialog.setText("正在加载个人资料");
				dialog.show();
				break;
			case Constants.Finish:
				if(dialog.isShowing())
				{
					dialog.setText("加载完毕");
					dialog.dismiss();
					dialog = null;
				}
				JSONObject obj1 = (JSONObject)msg.obj;
				try {
					 member = User.create_by_json(obj1.getString("response"));
					member_nickname.setText(member.getNickname());
					member_birth.setText(member.getBirth());
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
				//String nickname,birth,sex,company,position,phone,email;
					nickname = member.getNickname();
					birth = member.getBirth();
					company = member.getCompany();
					position = member.getPosition();
					phone = member.getPhone();
					email = member.getEmail();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	class DialogHandler extends Handler{
		LoadingDialog dialog;
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.Start:
				if(dialog==null){
					dialog = new LoadingDialog(BusinessCard.this);
					dialog.setText("正在修改密码");
					dialog.show();
				}
				break;
			case Constants.Finish:
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						((App)getApplication()).cleanUpInfo();
					}
				}).start();
				if(dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}
				toast("修改成功,请重新登录");
				openActivity(Login.class);
				BusinessCard.this.finish();
				break;
			default:
				break;
			}
		}
		
	}
	
	
	
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
			intent.putExtra("birth", birth);
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
					UserAPI.changePsw(et_change_oldpsw.getText().toString(), et_change_newpsw.getText().toString(), new JsonResponseHandler() {
						
						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							dialogHandler.sendEmptyMessage(Constants.Start);
						}
						
						@Override
						public void onOK(Header[] headers, JSONObject obj) {
							// TODO Auto-generated method stub
							
							try {
								if(obj.getString("code").equals("40000")){
									String message = obj.getString("msg");
									toast(message);
									return ;
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							try {
								if(obj.getString("code").equals("20000"))
								{
									dialogHandler.sendEmptyMessage(Constants.Finish);
								
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
			if(data.getStringExtra("sex").equals("f")){
				member_sex.setText("男");
			}else{
				member_sex.setText("女");
			}
			member_company.setText(data.getStringExtra("company"));
			member_position.setText(data.getStringExtra("position"));
			member_phone.setText(data.getStringExtra("phone"));
			member_email.setText(data.getStringExtra("email"));
			nickname = data.getStringExtra("nickname");
			birth = data.getStringExtra("birth");
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
		handler.sendEmptyMessage(Constants.Start);
		final Message msg = handler.obtainMessage();
		UserAPI.getMemberData(new JsonResponseHandler() {
			
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						msg.obj =obj;
						msg.what = Constants.Finish;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					toast("解析错误");
				}
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				
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
