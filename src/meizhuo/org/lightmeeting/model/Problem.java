package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Problem implements Serializable{
	
	/**
	 * 解析单个问题
	 * @param json
	 * @return
	 */
	public static Problem create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Problem)gson.fromJson(json, Problem.class);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/**
	 * 解析问题列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Problem>create_by_jsonarray(String jsonarray){
		List<Problem>list  = new ArrayList<Problem>();
		JSONObject  obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i = 0 ; i<array.length();i++)
			{
				list.add(create_by_json(array.getJSONObject(i).toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public String toString() {
		return "Problem [id=" + id + ", researchid=" + researchid + ", title="
				+ title + ", options=" + options + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResearchid() {
		return researchid;
	}
	public void setResearchid(String researchid) {
		this.researchid = researchid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
	private String id;
	private String researchid;
	private String title;
	private String options;
	


}
