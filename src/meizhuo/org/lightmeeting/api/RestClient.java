package meizhuo.org.lightmeeting.api;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import android.content.Context;


/**
 * 
 * @author Jason
 *
 */
public class RestClient {
	//测试url
	public static final String BASE_URL = "http://112.74.101.0/LightMeeting";
	private static int HTTP_Timeout = 12*1000;
	public static Context context;
	
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	/**
	 * 初始化:如果需要调用登陆验证记录session的函数前，必须调用这个方法，否则请求失败
	 * @param context Activity or Application context
	 */
	public static void init(Context context) {
		RestClient.context = context;
	}
	
	/**
	 * get method
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		initClient();
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	
	/**
	 * post method
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		initClient();
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	/**
	 * 请求前初始化<br>
	 * 必须在请求之前初始化，不然cookie失效
	 * context不为空时带着cookie去访问
	 */
	private static void initClient(){
		if(context != null)
			client.setCookieStore(new PersistentCookieStore(context));
		client.setTimeout(HTTP_Timeout);
	}
	/**
	 * 获得绝对URL
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl){
		return BASE_URL + relativeUrl;
	}
	public static AsyncHttpClient getClient(){
		initClient();
		return client;
	}
}
