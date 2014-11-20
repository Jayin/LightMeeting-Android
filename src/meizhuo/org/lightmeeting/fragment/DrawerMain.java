package meizhuo.org.lightmeeting.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.BusinessCard;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.adapter.DrawerAdapter;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.L;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class DrawerMain extends BaseFragment{
	
	public static final String[] menuName={"会议列表","关于","退出"};
	private MainActivity mainActivity;
	TextView tv_username;
	
	User user;
	
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
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		View v = inflater.inflate(R.layout.fragment_drawermain, container, false);
		ListView lv = (ListView)v.findViewById(R.id.left_drawer);
		 tv_username = (TextView)v.findViewById(R.id.tv_username);
		initData();
		View userinfo = v.findViewById(R.id.btn_userinfo);
		
		userinfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openActivity(BusinessCard.class);
			}
		});
		lv.setAdapter(new DrawerAdapter(mainActivity, menuName));
		lv.setOnItemClickListener(new DrawerItemClickListener());
		return v;
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//public static final String[] menuName={"会议列表","动态","个人名片","会议资料","功能","会议成员"};
			switch (position) {
			case 0://会议列表
				mainActivity.setMainContent(new LMList_fm());
				break;
		/*	case 1://动态   （暂时不显示吧）
				mainActivity.setMainContent(new Dynamic_fm());
				break;*/
			case 1://设置
				mainActivity.setMainContent(new About());
//				mainActivity.setMainContent(new Settings());
				break;
			case 2://关于
				getActivity().finish();
				break; 
			default:
				break;
			}
			
		}
		
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
