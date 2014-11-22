package meizhuo.org.lightmeeting.api;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class VersionAPI {

	public VersionAPI() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 最新版本信息
	 * @param responseHandler
	 */
	public void getLatestInfo(AsyncHttpResponseHandler responseHandler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.post("http://meizhuo.github.io/LightMeeting/data.json", null, responseHandler);
	}

}
