package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Vote implements Serializable{
	
	
	/**
	 * 解析单个投票
	 * @param json
	 * @return
	 */
	public static Vote create_by_json(String json){
		try {
			Gson gson =  new Gson();
			return (Vote)gson.fromJson(json, Vote.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析一个投票列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Vote>create_by_jsonarray(String jsonarray){
		List<Vote>list = new ArrayList<Vote>();
		JSONObject obj =  null;
		JSONArray array = null;
		try {
			obj =  new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i=0;i < array.length();i++)
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
		return "Vote [id=" + id + ", meetid=" + meetid + ", intro=" + intro
				+ ", ctime=" + ctime + ", stime=" + stime + ", etime=" + etime
				+ "]";
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
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	/**投票id*/
	private String id;
	/**会议id*/
	private String meetid;
	/**投票简介 */
	private String intro;
	/**时间1*/
	private String ctime;
	/**时间2*/
	private String stime;
	/**时间3*/
	private String etime;


}
