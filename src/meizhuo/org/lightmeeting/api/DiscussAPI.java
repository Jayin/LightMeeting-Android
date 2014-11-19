package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DiscussAPI {

	/**
	 * 获取讨论列表
	 * @param meetid
	 * @param responseHandler
	 */
	public static void getdiscusslist(String meetid,String page,String limit,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		if(!(page == null || page.equals("")))
			params.add("page", page);
		if(!(limit == null || limit.equals("")))
			params.add("page", limit);
		RestClient.post("/home/discuss/lists", params, responseHandler);
	}
	
	/**
	 * 创建一个讨论
	 * @param meetid
	 * @param title
	 * @param content
	 * @param responseHandler
	 */
	public static void createDiscuss(String meetid,String title,String content,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((meetid == null) || (meetid.equals("")))){
			params.add("meetid", meetid);
		}
		if(!((title == null) || (title.equals("")))){
			params.add("title", title);
		}
		if(!((content == null) || (content.equals("")))){
			params.add("content", content);
		}
		RestClient.post("/home/discuss/create", params, responseHandler);
	}
	
	/**
	 * 更新一个讨论信息
	 * @param discussid
	 * @param title
	 * @param content
	 * @param responseHandler
	 */
	public static void updateDiscuss(String discussid,String title,String content,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((discussid == null) || (discussid.equals("")))){
			params.add("discussid", discussid);
		}
		params.add("title", title);
		params.add("content", content);
		RestClient.post("/home/discuss/update", params, responseHandler);
	}
	
	/**
	 * 删除讨论
	 * @param discussid
	 * @param responseHandler
	 */
	public static void deleteDiscuss(String discussid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((discussid == null) || (discussid.equals("")))){
			params.add("discussid", discussid);
		}
		RestClient.post("/home/discuss/delete", params, responseHandler);
	}
	
	/***
	 * 获得一个讨论的信息 
	 * @param discussid
	 * @param withComments 0 ：不需要 1：需要
	 * @param responseHandler
	 */
	public static void getOneDiscuss(String discussid,String withComments,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((discussid == null) || (discussid.equals("")))){
			params.add("discussid", discussid);
		}
		params.add("withComments", withComments);
		RestClient.post("/home/discuss/info", params, responseHandler);
	}
	
	//
	//  分界线 讨论->评论
	//----------------------------------------------------------------------
	//
	/**
	 * 获取一个评论列表
	 * @param discussid
	 * @param responseHandler
	 */
	public static void getCommentlist(String discussid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((discussid == null) || (discussid.equals("")))){
			params.add("discussid", discussid);
		}
		RestClient.post("/home/discuss/listComment", params, responseHandler);
	}
	
	/**
	 * 创建一个评论
	 * @param discussid
	 * @param content
	 * @param reply_member
	 * @param responseHandler
	 */
	public static void createComment(String discussid,String content,String reply_member,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((discussid == null) || (discussid.equals("")))){
			params.add("discussid", discussid);
		}
		if(!((content == null) || (content.equals("")))){
			params.add("content", content);
		}
		params.add("reply_member", reply_member);
		RestClient.post("/home/discuss/createComment", params, responseHandler);
	}
	
	public static void deleteComment(String commentid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!((commentid == null) || (commentid.equals("")))){
			params.add("commentid", commentid);
		}
		RestClient.post("/home/discuss/deleteComment", params, responseHandler);
	}
	

}
