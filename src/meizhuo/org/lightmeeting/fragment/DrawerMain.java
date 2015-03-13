package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.BusinessCard;
import meizhuo.org.lightmeeting.acty.Login;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.acty.RelationList;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class DrawerMain extends BaseFragment  {
	 
	private MainActivity mainActivity;
	LoadingDialog loadingdialog;
	User user;
	@InjectView(R.id.tv_username) TextView tv_username;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mainActivity = (MainActivity)activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 initData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState,R.layout.fragment_drawermain);
		 return contentView;
	}
	@OnClick(R.id.btn_userinfo) public void to_userinfo(){
		openActivity(BusinessCard.class);
	}
	@OnClick(R.id.lm_to_relation) public void to_relation(){
		openActivity(RelationList.class);
	}
	
	@OnClick(R.id.lm_to_meetlist) public void to_meetlist(){
		mainActivity.setMainContent(new MeetlistFm());
	}
	@OnClick(R.id.lm_to_logoff) public void to_logoff(){
		AlertDialog.Builder logoffBuilder = new AlertDialog.Builder(getActivity());
		logoffBuilder.setTitle("      确定进行注销吗 ?");
		logoffBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UserAPI.logout(new JsonHandler(){
					@Override
					public void onStart() {
						if(loadingdialog == null)
						{
							loadingdialog = new LoadingDialog(getActivity());
						}
						loadingdialog.setText("正在注销!");
						loadingdialog.show();
					}
					
					@Override
					public void onOK(int statusCode, Header[] headers,
							JSONObject obj) throws Exception {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								App app = (App)getActivity().getApplication();
								app.cleanUpInfo();
							}
						}).start();
						
						if(loadingdialog.isShowing())
						{
							loadingdialog.dismiss();
							loadingdialog = null;
						}
						toast("注销成功");
						openActivity(Login.class);
						getActivity().finish();
					}
					
					@Override
					public void onError(int error_code, Header[] headers,
							JSONObject obj) throws Exception {
						// TODO Auto-generated method stub
						super.onError(error_code, headers, obj);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] data, Throwable arg3) {
						toast("网络不给力,注销失败!");
						return ;
					}
				});
	
			}
		});
		logoffBuilder.setNegativeButton("暂不注销", null);
		AlertDialog logoffDialog = logoffBuilder.create();
		logoffDialog.show();
		
	}
	@OnClick(R.id.lm_to_about) public void to_about(){
		mainActivity.setMainContent(new About());
	}
	@OnClick(R.id.lm_to_logout) public void to_logout(){
		getActivity().finish();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if(AndroidUtils.isNetworkConnected(getActivity())){
			UserAPI.getMemberData(new JsonResponseHandler() {
				
				@Override
				public void onOK(Header[] headers, JSONObject obj) {
					// TODO Auto-generated method stub
					try {
						
						if(obj.getString("code").equals("20000")){
							
							user = User.create_by_json(obj.getString("response"));
							tv_username.setText(user.getNickname().toString());
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
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}


}
