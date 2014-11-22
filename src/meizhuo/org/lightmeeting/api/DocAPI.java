package meizhuo.org.lightmeeting.api;

import meizhuo.org.lightmeeting.utils.StringUtils;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DocAPI {

	
	/**
	 * 获取文档列表
	 * @param meetid
	 * @param page
	 * @param limit
	 * @param responseHandler
	 */
	public static void getDocList(String meetid,String page,String limit,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("meetid", meetid);
		if(!StringUtils.isEmpty(page))
		params.add("meetid", meetid);
		if(!StringUtils.isEmpty(limit))
		params.add("meetid", meetid);
		RestClient.post("/home/document/listmeetdoc", params, responseHandler);
	}

}
