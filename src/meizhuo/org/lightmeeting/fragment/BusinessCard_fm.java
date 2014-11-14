package meizhuo.org.lightmeeting.fragment;



import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class BusinessCard_fm extends BaseFragment  {
	
	@InjectView(R.id.lm_member_nickname) TextView member_nickname;
	@InjectView(R.id.lm_member_birth) TextView member_birth;
	@InjectView(R.id.lm_member_sex) TextView member_sex;
	@InjectView(R.id.lm_member_company) TextView member_company;
	@InjectView(R.id.lm_member_position) TextView member_position;
	@InjectView(R.id.lm_member_phone) TextView member_phone;
	@InjectView(R.id.lm_member_email) TextView member_email;
	
	
	BCHandler handler = new BCHandler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_buinesscard);
	 initData();
	 initLayout();
	 
	 return contentView;
	}
	
	
	
	private void initData(){
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
	
private void initLayout(){
		
	}
	
	class BCHandler extends Handler{
		LoadingDialog dialog;
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case Constants.Start:
				if(dialog == null)
				{
					dialog = new LoadingDialog(getActivity());
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
					User member = User.create_by_json(obj1.getString("response"));
					member_nickname.setText(member.getNickname());
					member_birth.setText(member.getBirth());
					if(member.getSex().equals("m")){
						member_sex.setText("男");
					}else{
						member_sex.setText("女");
					}
					L.i(member.toString());
					member_company.setText(member.getCompany());
					member_position.setText(member.getPosition());
					member_phone.setText(member.getPhone());
					member_email.setText(member.getEmail());
				
					
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
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fm_user, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_refreshdata:
			toast("1");
			break;
		case R.id.action_changePsw:
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View dialogView = inflater.inflate(R.layout.dialog_change_psw, null);
			final EditText et_change_oldpsw = (EditText)dialogView.findViewById(R.id.et_change_oldpsw);
			final EditText et_change_newpsw = (EditText)dialogView.findViewById(R.id.et_change_newpsw);
			final EditText et_confirm_newpsw = (EditText)dialogView.findViewById(R.id.et_change_confirmnewpsw);
			AlertDialog.Builder  builder =  new AlertDialog.Builder(getActivity());
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
						toast("旧密码不能为空!");
						return ;
					}
					if(!((et_confirm_newpsw.getText().toString()).equals(et_change_newpsw.getText().toString()))){
						toast("两次新密码输入不一致,修改失败!");
						return ;
					}
					UserAPI.changePsw(et_change_oldpsw.getText().toString(), et_change_newpsw.getText().toString(), new JsonResponseHandler() {
						
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
									toast("修改成功");
									
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
		case R.id.action_logoff:
			toast("1");
			break;
		case R.id.action_logout:
			toast("1");
			break;
		default:
			break;
		}
		return true;
	}
	
	
	






}
