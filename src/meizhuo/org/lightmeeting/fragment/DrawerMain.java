package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.BusinessCard;
import meizhuo.org.lightmeeting.acty.Login;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.L;
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
	
//	public static final String[] menuName={"会议列表","关于","退出"};
	private MainActivity mainActivity;
	LoadingDialog loadingdialog;
	User user;
	@InjectView(R.id.tv_username) TextView tv_username;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mainActivity = (MainActivity)activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 initData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreateView(inflater, container, savedInstanceState,R.layout.fragment_drawermain);
		 return contentView;
	}
	@OnClick(R.id.btn_userinfo) public void to_userinfo(){
		openActivity(BusinessCard.class);
	}
	@OnClick(R.id.lm_to_meetlist) public void to_meetlist(){
		mainActivity.setMainContent(new LMList_fm());
	}
	@OnClick(R.id.lm_to_logoff) public void to_logoff(){
		AlertDialog.Builder logoffBuilder = new AlertDialog.Builder(getActivity());
		logoffBuilder.setTitle("      确定进行注销吗 ?");
		logoffBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UserAPI.logout(new JsonResponseHandler() {
					
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						if(loadingdialog == null)
						{
							loadingdialog = new LoadingDialog(getActivity());
						}
						loadingdialog.setText("正在注销!");
						loadingdialog.show();
					}
					
					@Override
					public void onOK(Header[] headers, JSONObject obj) {
						// TODO Auto-generated method stub
						try {
							if(obj.getString("code").equals("20000"))
							{
				
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
							L.i("拿到了用户的名字" + user.getNickname().toString());
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
