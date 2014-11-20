package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 
 * @author Jason
 *
 */
public class VoteAPI {
	
	/**
	 * 获取投票列表
	 * @param meetid 会议id
	 * @param responseHandler
	 */
	public static void getVoteList(String meetid,String page,String limit,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		params.add("page", page);
		params.add("limit", limit);
		RestClient.post("/home/vote/lists", params, responseHandler);
	}
	
	/**
	 * 创建一个投票
	 * @param meetid
	 * @param responseHandler
	 */
	public static void createVote(String meetid,String title,String intro,String stime,String etime,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		params.add("title", title);
		params.add("intro", intro);
		params.add("stime", stime);
		params.add("etime", etime);
		RestClient.post("/home/vote/create", params, responseHandler);
	}
	
	public static void addOption(String voteid,String meetid,String vpintro,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("voteid", voteid);
		params.add("meetid", meetid);
		params.add("vpintro", vpintro);
		RestClient.post("/home/vote/addOption", params, responseHandler);
	}
	
	/**
	 * 更新一个选项
	 * @param vpotionsid
	 * @param voteid
	 * @param meetid
	 * @param vpintro 选项介绍
	 * @param responseHandler
	 */
	public static void updateOption(String vpotionsid,String voteid,String meetid,String vpintro,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("vpotionsid",vpotionsid );
		params.add("voteid", voteid);
		params.add("meetid", meetid);
		params.add("vpintro",vpintro );
		RestClient.post("/home/vote/updateOption", params, responseHandler);
	}
	
	/**
	 * 删除一个选项
	 * @param vpotionsid
	 * @param responseHandler
	 */
	public static void deleteOption(String vpotionsid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("vpotionsid", vpotionsid);
		RestClient.post("/home/vote/deleteOption", params, responseHandler);
	}
	
	/**
	 * 获取选项列表
	 * @param voteid
	 * @param responseHandler
	 */
	public static void getOptionList(String voteid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("voteid",voteid);
		
		RestClient.post("/home/vote/listOption", params, responseHandler);
	}
	
	/**
	 * 更新投票项
	 * @param id
	 * @param title
	 * @param intro
	 * @param stime
	 * @param etime
	 * @param responseHandler
	 */
	public static void updateVote(String id,String title,String intro,String stime,String etime,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("id",id);
		params.add("title",title);
		params.add("intro",intro);
		params.add("stime",stime);
		params.add("etime",etime);
		RestClient.post("/home/vote/update", params, responseHandler);
	}	
	
	/**
	 * 删除投票项
	 * @param voteid
	 * @param responseHandler
	 */
	public static void deleteVoteOption(String voteid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("voteid",voteid);
		RestClient.post("/home/vote/delete", params, responseHandler);
	}
	
	/**
	 * 用户投票
	 * @param vpotionsid
	 * @param voteid
	 * @param responseHandler
	 */
	public static void MemberVote(String optionsid,String voteid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("optionsid",optionsid);
		params.add("voteid",voteid);
		RestClient.post("/home/vote/vote", params, responseHandler);
	}	
	
	/**
	 * 查看一个投票项
	 * @param voteid
	 * @param responseHandler
	 */
	public static void getOneVote(String voteid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("voteid",voteid);
		RestClient.post("/home/vote/info", params, responseHandler);
	}
	
	
	
	
	
	


}
