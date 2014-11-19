package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MeetingAPI {
	
	
	/**
	 * 添加会议 
	 * @param title
	 * @param intro
	 * @param address
	 * @param starttime
	 * @param endtime
	 * @param responseHandler
	 */
	public static void addMeeting(String title,String intro,String address,String starttime,String endtime,
			AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("title", title);
		if(!(intro==null || intro.equals(""))){
			params.add("intro", intro);
		}
		params.add("address", address);
		params.add("starttime", starttime);
		params.add("endtime", endtime);
		RestClient.post("/home/meet/addmeet", params, responseHandler);
	}
	
	/**
	 * 会议更新
	 * @param title
	 * @param intro
	 * @param address
	 * @param starttime
	 * @param endtime
	 * @param responseHandler
	 */
	public static void updateMeeting(String meetid,String title,String intro,String address,String starttime,String endtime,
			AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("id", meetid);
		if(!(title==null || title.equals(""))){
			params.add("title", title);
		}
		if(!(intro==null || intro.equals(""))){
			params.add("intro", intro);
		}
		if(!(address==null || address.equals(""))){
			params.add("address", address);
		}
		if(!(starttime==null || starttime.equals(""))){
			params.add("starttime", starttime);
		}
		if(!(endtime==null || endtime.equals(""))){
			params.add("endtime", endtime);
		}
		RestClient.post("/home/meet/updatemeet", params, responseHandler);
	}
	
	/**
	 * 删除一个会议
	 * @param meetid
	 * @param responseHandler
	 */
	public static void deleteMeeting(String meetid,AsyncHttpResponseHandler responseHandler)
	{
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		RestClient.post("/home/meet/deletemeet", params, responseHandler);
	}
	
	/**
	 * 加入一个会议
	 * @param meetid
	 * @param responseHandler
	 */
	public static void addjoin(String meetid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid",meetid);
		RestClient.post("/home/meet/addjoin", params, responseHandler);
	}
	
	
	/**
	 *  查看会议列表
	 * @param page
	 * @param limit
	 * @param responseHandler
	 */
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
	
	/**
	 * 获取加入的成员列表
	 * @param meetid
	 * @param responseHandler
	 */
	public static void getJoinMember(String meetid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		RestClient.post("/home/meet/getjoinmember", params, responseHandler);
	}
	
	/**
	 * 退出会议
	 * @param meetid
	 * @param responseHandler
	 */
	public static void quiteMeet(String meetid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		RestClient.post("/home/meet/outjoin", params, responseHandler);
	}


}
