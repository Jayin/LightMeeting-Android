package meizhuo.org.lightmeeting.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.api.VersionAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.KV;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
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
			if (Constants.Action_checkVersion.equals(action)){
				checkVersion(); //版本控制
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
			Log.i(TAG, "第一次登录");
			sendBroadcast(new Intent(Constants.Action_First_Login));
		}else{
			UserAPI.login(username, password, new JsonResponseHandler() {
				
				@Override
				public void onOK(Header[] headers, JSONObject obj) {
					Log.i(TAG, ""+obj.toString());
					try {
						if(obj.getString("code").equals("20000")){
							Log.i(TAG, "重新登录了");
							sendBroadcast(new Intent(Constants.Action_Login_In_Successful));
							//设置推送标签
							App app = (App)getApplication();
							app.addTag(obj.getJSONObject("response").getString("id"), App.TAG_TYPE_PERSON);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Log.i(TAG, "重新登录失败"+e.getMessage());
						sendBroadcast(new Intent(Constants.Action_Login_failed));
					}
				}
				
				@Override
				public void onFaild(int errorType, int errorCode) {
					// TODO Auto-generated method stub
					sendBroadcast(new Intent(Constants.Action_Login_failed));
					Log.i(TAG, "登录异常");
				}
			});
		}
	}
	
	private void checkVersion(){
		L.i("执行check");
		VersionAPI api = new VersionAPI();
		final List<KV>kvlist = new ArrayList<KV>();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://meizhuo.github.io/LightMeeting/data.json",new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				L.i("执行onSuccess");
				try {
					JSONObject obj = new JSONObject(new String(arg2));
					L.i(obj.toString());
					int versioncode = Integer.parseInt(obj
							.getString("version_code"));
					String versionname = obj.getString("version_name");
					int currentVersion = AndroidUtils
							.getAppVersionCode(getApplicationContext());
					String url = obj.getString("url");
					JSONArray updateInfo = obj.getJSONArray("update_content");
					for(int i=0;i<updateInfo.length();i++)
					{
						for(Iterator<String> keylter = ((JSONObject) updateInfo.get(i)).keys();keylter.hasNext();){
							try {
								String key  = keylter.next();
								L.i("key" + key );
								KV kv = new KV();
								kv.setKey(key);
								kv.setValue(((JSONObject) updateInfo.get(i)).getString(key));
								kvlist.add(kv);
//								List<KV> oldlist = new ArrayList<KV>();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					if (versioncode > currentVersion) {
						Intent intent = new Intent(Constants.Action_Receive_VersionInfo);
						intent.putExtra("version_code", versioncode);
						intent.putExtra("version_name", versionname);
						intent.putExtra("updateInfo", (Serializable)kvlist);
						intent.putExtra("url", url);
						sendBroadcast(intent);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				L.i("执行onFailure");
			}
		});
		
	}

}
