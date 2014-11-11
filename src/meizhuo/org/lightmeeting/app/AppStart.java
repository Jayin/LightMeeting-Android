package meizhuo.org.lightmeeting.app;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.Login;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.DataPool;
import meizhuo.org.lightmeeting.utils.L;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class AppStart extends BaseActivity{
	private long starttime;
	private long waittime = 1500;
	private BroadcastReceiver mReceiver = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.acty_start);
		initReceiver();
//		init();
		starttime = System.currentTimeMillis();
		
	}
	
	private void initReceiver(){
		mReceiver = new AppStartReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.Action_Login_In_Successful);
		filter.addAction(Constants.Action_Login_failed);
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mReceiver != null)
			unregisterReceiver(mReceiver);
	}
	
	//初始化工作
//	private void init() {
//		Intent service = new Intent(getContext(), CoreService.class);
//		service.setAction(Constants.Action_To_Login);
//		startService(service);
//	}
	
	private boolean checkLoginInfo() {
		DataPool dp = new DataPool(DataPool.SP_Name_User, this);
		if(dp.contains(DataPool.SP_Key_User))
			return true;
		else
			return false;
	}
	
	
	
	class AppStartReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(action.equals(Constants.Action_Login_In_Successful)){
				L.i("AppStartReceiver-->Login Successfully");
			}else{
				L.i("AppStartReceiver-->Login faild");
			}
			long lasttime = System.currentTimeMillis() - starttime;
			if(waittime > lasttime) {
				try {
					Thread.sleep(waittime-lasttime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(checkLoginInfo()){
				openActivity(MainActivity.class);
			}else{
				openActivity(Login.class);
			}
			closeActivity();
		}
	}



	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}


}
