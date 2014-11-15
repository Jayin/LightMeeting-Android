package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.Login;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.adapter.DrawerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerMain extends BaseFragment{
	
	public static final String[] menuName={"会议列表","动态","个人名片","会议资料","功能","会议成员"};
	private MainActivity mainActivity;
	
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
		View userinfo = v.findViewById(R.id.btn_userinfo);
		userinfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(mainActivity, Login.class));
				
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
			case 1://动态
				mainActivity.setMainContent(new Dynamic_fm());
				break;
			case 2://个人名片
				mainActivity.setMainContent(new BusinessCard_fm());
				break;
			case 3://会议资料
				mainActivity.setMainContent(new MeetingData_fm());
				break;
			case 4://功能
				mainActivity.setMainContent(new Meeting_function_fm());
				break;
			case 5://会议成员
				mainActivity.setMainContent(new Member_fm());
				break;
	
			default:
				break;
			}
			
		}
		
	}


}
