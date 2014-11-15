package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MeetingAPI {
	
	public MeetingAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public static void getMeetingList(String page,String limit,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();
		if(!(page==null || page.equals(""))){
			params.add("page", page);
		}
		if(!(limit==null || limit.equals(""))){
			params.add("limit", limit);
		}
		RestClient.post("/home/meet/getjoinmeet", params, responseHandler);
	}


}
