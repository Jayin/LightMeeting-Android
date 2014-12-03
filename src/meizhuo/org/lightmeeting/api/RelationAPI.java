package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RelationAPI {
	
	
	
	/**
	 * 删除人脉
	 * @param relationid
	 * @param responseHandler
	 */
	public static void deleteRelation(String relationid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("relationid", relationid);
		RestClient.post("/home/relation/delete", params, responseHandler);
	}
	
	/**
	 * 获取人脉列表
	 * @param responseHandler
	 */
	public static void getRelationList(AsyncHttpResponseHandler responseHandler){
		RestClient.post("/home/relation/lists", null, responseHandler);
	}

	
}
