package meizhuo.org.lightmeeting.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Relation {
	
	/**
	 * 构建单个人脉对象
	 * @param json
	 * @return
	 */
	public static Relation create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Relation)gson.fromJson(json, Relation.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Relation>create_by_jsonarray(String jsonarray){
		List<Relation>list = new ArrayList<Relation>();
		JSONObject obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i=0 ; i<array.length();i++)
			{
				list .add(create_by_json(array.getJSONObject(i).toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	
	
	
	public String getRelationid() {
		return relationid;
	}
	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Relation [relationid=" + relationid + ", id=" + id
				+ ", nickname=" + nickname + ", sex=" + sex + ", company="
				+ company + ", position=" + position + "]";
	}
	
	/**人脉di*/
	private String relationid;
	/**..*/
	private String id;
	/**昵称*/
	private String nickname;
	/**性别*/
	private String sex;
	/**公司*/
	private String company;
	/**职位*/
	private String position;


}
