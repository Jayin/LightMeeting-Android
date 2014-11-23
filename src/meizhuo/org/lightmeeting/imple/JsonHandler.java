package meizhuo.org.lightmeeting.imple;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * AsyncHttpResponseHandler的封装
 * 
 * 	 网络连接异常
 *	json构建对象失败,一般是后台接口错误,
 *	返回不是状态码为20000的错误
 *	正确返回json并解析成功，但有error（服务端返回错误信息给客户端） 
 * 
 *  @author Jayin Ton
 *
 */
public class JsonHandler extends AsyncHttpResponseHandler {
	private static final String TAG = "debug";
	

	public static final int Error_Response = 4; 
	/**
	 * 执行该方法的情况
	 * 1. 连接异常,网络的中断等
	 * 2. statusCode >= 300
	 */
	@Override public void onFailure(int statusCode, Header[] headers, byte[] data,
			Throwable arg3) {
			
	}
	/**
	 * 执行该方法的情况:
	 * 200<= statusCode <= 300
	 */
	@Override public void onSuccess(int statusCode, Header[] headers, byte[] data) {
		 String json = new String(data);
		 JSONObject obj = null;
		 Log.d(TAG,"请求成功,返回的json：");
		 try {
			obj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		    try {
				obj = new JSONObject("{\"error_code\":40000,\"response\":\"远程接口有误\"}");
				Log.d(TAG,obj.toString());
				try {
					onError(statusCode, headers, obj);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		    return;
		}
		Log.d(TAG,obj.toString());
		try {
			int error_code = obj.getInt("error_code");
			
			try {
				//有错误
				onError(error_code, headers, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JSONException e) {
			//没有错误
			try {
				onOK(statusCode, headers, obj);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	//正确返回回调函数
	public void onOK(int statusCode, Header[] headers ,JSONObject obj) throws Exception{
		
	}
	/**
	 * 出现error_code,错误返回回调函数
	 * @param error_code 错误返回码
	 * @param headers
	 * @param obj
	 * @throws Exception
	 */
	public void onError(int error_code, Header[] headers ,JSONObject obj) throws Exception{
		
	}
}
