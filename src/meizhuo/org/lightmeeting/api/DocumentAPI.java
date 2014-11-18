package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DocumentAPI {

/*	public static void (String meetid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("", arg1);
		RestClient.post(url, params, responseHandler);
		
	}*/
	
	
	/**
	 * 创建一份文档
	 * @param meetid
	 * @param title
	 * @param content
	 * @param responseHandler
	 */
	public static void createDoc(String meetid,String title,String content,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		params.add("title", title);
		params.add("content",content );
		RestClient.post("/home/document/createdoc", params, responseHandler);

		
	}
	
	/**
	 * 获取文档列表
	 * @param id
	 * @param page
	 * @param limit
	 * @param responseHandler
	 */
	public static void getDocList(String id,String page,String limit,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("id", id);
		if(!(limit == null || limit.equals("")))
		params.add("limit", limit);
		if(!(page == null || page.equals("")))
		params.add("page", page);
		RestClient.post("/home/document/listmeetdoc", params, responseHandler);
		
	}
	
	/**
	 * 更新文档 
	 * @param docid
	 * @param title
	 * @param content
	 * @param responseHandler
	 */
	public static void updateDoc(String docid,String title,String content,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("id", docid);
		params.add("title", title);
		params.add("content", content);
		RestClient.post("/home/document/updatedoc", params, responseHandler);
		
	}
	
	/**
	 * 删除文档
	 * @param docid
	 * @param responseHandler
	 */
	public static void deleteDoc(String docid,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("id", docid);
		RestClient.post("/home/document/deletedoc", params, responseHandler);
		
	}
	
	/**
	 * 查看文档
	 * @param meetid
	 * @param responseHandler
	 */
	public static void getOneDoc(AsyncHttpResponseHandler responseHandler){
		RestClient.get("/home/document/listdoc/7.html", null, responseHandler);
		
	}
	

	
	

	
}
