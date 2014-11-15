package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 会议列表
 * @author Jason
 *
 */
@SuppressWarnings("serial")
public class Meeting implements Serializable{
	
	/**
	 * 解析单个会议
	 * @param json
	 * @return
	 */
	public static Meeting create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Meeting)gson.fromJson(json, Meeting.class);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/**
	 * 解析会议列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Meeting>create_by_jsonarray(String jsonarray){
		List<Meeting>list = new ArrayList<Meeting>();
		JSONObject obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i=0 ; i< array.length();i++)
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
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreatemember() {
		return createmember;
	}
	public void setCreatemember(String createmember) {
		this.createmember = createmember;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", title=" + title + ", intro=" + intro
				+ ", address=" + address + ", createmember=" + createmember
				+ ", weight=" + weight + ", starttime=" + starttime
				+ ", endtime=" + endtime + "]";
	}

	/**会议id*/
	private String id;
	/**会议主题*/
	private String title;
	/**会议简介*/
	private String intro;
	/**会议*/
	private String address;
	/**会议主持人*/
	private String createmember;
	/**权重*/
	private String weight;
	/**开始时间*/
	private String starttime;
	/**结束时间*/
	private String endtime;
	

}
