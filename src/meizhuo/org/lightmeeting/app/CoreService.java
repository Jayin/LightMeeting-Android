package meizhuo.org.lightmeeting.app;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 后台服务
 * @author Jason
 *
 */
public class CoreService extends Service{
	private static final String TAG = "CoreService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//任务分发
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent != null){
			String action  = intent.getAction();
			Log.i(TAG, action);
			if(Constants.Action_To_Login.equals(action)){
				Log.i(TAG, "执行了");
				to_login();
			}
		}
		return Service.START_STICKY;
	}
	
	//退出app之后重新启动登录。避免每次登录
	public void to_login(){
		String username = AppInfo.getUsername(getApplicationContext());
		String password = AppInfo.getUserPSW(getApplicationContext());
		if(username == null || username.equals("")||password == null || password.equals(""))
		{
			Log.i(TAG, "这里跑了");
			sendBroadcast(new Intent(Constants.Action_First_Login));
		}else{
			UserAPI.login(username, password, new JsonResponseHandler() {
				
				@Override
				public void onOK(Header[] headers, JSONObject obj) {
					// TODO Auto-generated method stub
					Log.i(TAG, ""+obj.toString());
					try {
						if(obj.getString("code").equals("20000")){
							Log.i(TAG, "重新登录了");
							sendBroadcast(new Intent(Constants.Action_Login_In_Successful));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.i(TAG, "重新登录异常了"+e.getMessage());
					}
				}
				
				@Override
				public void onFaild(int errorType, int errorCode) {
					// TODO Auto-generated method stub
					sendBroadcast(new Intent(Constants.Action_Login_failed));
					Log.i(TAG, "重新失败");
				}
			});
		}
	}

}
