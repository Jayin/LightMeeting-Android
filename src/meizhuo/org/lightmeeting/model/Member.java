package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Member implements Serializable{
	
	/**
	 * 解析单个会议成员
	 * @param json
	 * @return
	 */
	public static Member create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Member)gson.fromJson(json, Member.class);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/**
	 * 解析会议成员列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Member>create_by_jsonarray(String jsonarray){
		List<Member>list = new ArrayList<Member>();
		JSONObject obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i=0 ; i<array.length();i++)
			{
				list.add(create_by_json(array.getJSONObject(i).toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			list = null;
		}
		return list;
	}
	
	
	
	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", nickname="
				+ nickname + ", sex=" + sex + ", company=" + company
				+ ", position=" + position + ", stime=" + stime + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	/**成员id*/
	private String id;
	/**用户名字*/
	private String username;
	/**用户昵称*/
	private String nickname;
	/**性别*/
	private String sex;
	/**公司*/
	private String company;
	/**位置*/
	private String position;
	/**时间*/
	private String stime;


}
