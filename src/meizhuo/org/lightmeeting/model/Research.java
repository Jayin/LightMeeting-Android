package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Research implements Serializable{
	
	/**
	 * 解析单个调查
	 * @param json
	 * @return
	 */
	public static  Research create_by_json(String json){
		try {
			Gson gson =  new Gson();
			return (Research)gson.fromJson(json, Research.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析一个调查列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Research>create_by_jsonarray(String jsonarray){
		List<Research>list = new ArrayList<Research>();
		JSONObject obj = null;
		JSONArray  array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i=0 ; i<array.length();i++)
			{
				list.add(create_by_json(array.getJSONObject(i).toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	
	@Override
	public String toString() {
		return "Research [id=" + id + ", meetid=" + meetid + ", memberid="
				+ memberid + ", title=" + title + ", content=" + content
				+ ", ctime=" + ctime + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMeetid() {
		return meetid;
	}
	public void setMeetid(String meetid) {
		this.meetid = meetid;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	/**调查id*/
	private String id;
	/**会议id*/
	private String meetid;
	/**成员id*/
	private String memberid;
	/**调查标题*/
	private String title;
	/**调查内容*/
	private String content;
	/**调查时间*/
	private String ctime;
	


}
