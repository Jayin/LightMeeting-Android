package meizhuo.org.lightmeeting.app;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.Login;
import meizhuo.org.lightmeeting.acty.MainActivity;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.DataPool;
import meizhuo.org.lightmeeting.utils.L;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

public class AppStart extends BaseActivity {


	private long starttime;
	private long waittime = 1500;
	private BroadcastReceiver mReceiver = null;

	@Override protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState, R.layout.acty_start);
		/*
		 * new Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * if(!AppStart.this.isFinishing()){ openActivity(MeetingData.class);
		 * closeActivity(); } } }, 1500);
		 */

		initReceiver();
		init();
		starttime = System.currentTimeMillis();

	}

	private void initReceiver() {
		mReceiver = new AppStartReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.Action_Login_In_Successful);
		filter.addAction(Constants.Action_Login_failed);
		filter.addAction(Constants.Action_First_Login);
		registerReceiver(mReceiver, filter);
	}

	@Override protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}

	}

	// 初始化工作
	protected void init() {
		Intent service = new Intent(getContext(), CoreService.class);
		service.setAction(Constants.Action_To_Login);
		startService(service);
	}

	private boolean checkLoginInfo() {
		DataPool dp = new DataPool(DataPool.SP_Name_User, this);
		if (dp.contains(DataPool.SP_Key_User))
			return true;
		else
			return false;
	}

	class AppStartReceiver extends BroadcastReceiver {
		@Override public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			final String action = intent.getAction();

			long lasttime = System.currentTimeMillis() - starttime;
			if (waittime > lasttime) {
				try {
					Thread.sleep(waittime - lasttime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			new Handler().postDelayed(new Runnable() {

				@Override public void run() {
					if (action.equals(Constants.Action_First_Login)) {
						L.i("第一次登录");
						openActivity(Login.class);
					}
					if (action.equals(Constants.Action_Login_In_Successful)) {
						openActivity(MainActivity.class);
					}/* else {
						openActivity(Login.class);
					}*/
					if (action.equals(Constants.Action_Login_failed)){
						//登录失败的话  一样进入主界面
						openActivity(MainActivity.class);
					}
					closeActivity();

				}
			}, waittime - lasttime);

		}
	}

	@Override protected void initData() {

	}

	@Override protected void initLayout() {

	}

}
