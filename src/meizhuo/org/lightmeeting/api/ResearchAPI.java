package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 调查功能的API
 * @author Jason
 *
 */
public class ResearchAPI {
	
	
	
	/**
	 * 获得会议的调查列表
	 * @param meetid
	 * @param responseHandler
	 */
	public static void getResearchList(String meetid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("meetid", meetid);
		RestClient.post("/home/research/lists", params, responseHandler);
	}
	
	/**
	 * 创建调查表
	 * @param meetid
	 * @param title
	 * @param intro
	 * @param stime
	 * @param etime
	 * @param responseHandler
	 */
	public static void createOneSearch(String meetid,String title,String intro,String stime,String etime,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("meetid", meetid);
		params.add("title", title);
		params.add("intro",intro );
		params.add("stime", stime);
		params.add("etime",etime );
		RestClient.post("/home/research/create", params, responseHandler);
	}
	
	/***
	 * 添加一问题
	 * @param researchid
	 * @param title
	 * @param options
	 * @param responseHandler
	 */
	public static void createOneQuestion(String researchid,String title,String options,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("researchid", researchid);
		params.add("title", title);
		params.add("options",options );
		RestClient.post("/home/research/createQuestion", params, responseHandler);
	}
	
	/**
	 * 更新一问题
	 * @param questionid
	 * @param title
	 * @param options
	 * @param responseHandler
	 */
	public static void updateOneQuestion(String questionid,String title,String options,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("questionid",questionid );
		params.add("title", title);
		params.add("options", options);
		RestClient.post("/home/research/updateQuestion", params, responseHandler);
	}
	
	
	/**
	 * 删除一问题
	 * @param questionid
	 * @param responseHandler
	 */
	public static void deleteOneQuestion(String questionid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("questionid", questionid);
		RestClient.post("/home/research/deleteQuestion", params, responseHandler);
	}
	
	/**
	 * 获得question列表
	 * @param researchid
	 * @param responseHandler
	 */
	public static void getQuestionList(String researchid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("researchid",researchid );
		RestClient.get("/home/research/listQuestion", params, responseHandler);
	}
	
	/**
	 * 更新调查表
	 * @param id
	 * @param title
	 * @param intro
	 * @param stime
	 * @param etime
	 * @param responseHandler
	 */
	public static void updateOneResearch(String id,String title,String intro,String stime,String etime,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("id", id);
		params.add("title",title );
		params.add("intro", intro);
		params.add("stime",stime );
		params.add("etime",etime );
		RestClient.post("/home/research/update", params, responseHandler);
	}
	
	/**
	 * 删除一个调查表
	 * @param voteid
	 * @param responseHandler
	 */
	public static void deleteOneResearch(String voteid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("voteid", voteid);
		RestClient.post("/home/research/delete", params, responseHandler);
	}
	
	/**
	 * 回答问题
	 * @param questionid
	 * @param responseHandler
	 */
	public static void answer(String questionid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("questionid", questionid);
		RestClient.post("/home/research/answer", params, responseHandler);
	}
	
	public static void getOneResearch(String researchid,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("researchid", researchid);
		RestClient.get("/home/research/info", params, responseHandler);
	}
	

}
