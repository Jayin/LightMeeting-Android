package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class MeetingAPI {
	
	public MeetingAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public static void getMeetingList(AsyncHttpResponseHandler responseHandler)
	{
		RestClient.post("/home/meet/getjoinmeet", null, responseHandler);
	}


}
