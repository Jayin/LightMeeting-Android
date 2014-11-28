package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Option implements Serializable{
	
	/**
	 * 解析单个选项
	 * @param json
	 * @return
	 */
	public static Option create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Option)gson.fromJson(json, Option.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析整个选项列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Option>create_by_jsonarray(String jsonarray){
		List<Option>list = new ArrayList<Option>();
		JSONObject obj = null;
		JSONArray array =  null;
		try {
			obj =  new JSONObject(jsonarray);
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

	public String getVoteid() {
		return voteid;
	}

	public void setVoteid(String voteid) {
		this.voteid = voteid;
	}

	public String getMeetid() {
		return meetid;
	}

	public void setMeetid(String meetid) {
		this.meetid = meetid;
	}

	public String getVpintro() {
		return vpintro;
	}

	public void setVpintro(String vpintro) {
		this.vpintro = vpintro;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}


	@Override
	public String toString() {
		return "Option [id=" + id + ", voteid=" + voteid + ", meetid=" + meetid
				+ ", vpintro=" + vpintro + ", click=" + click + "]";
	}


	/**选项id*/
	private String id;
	/**投票id*/
	private String voteid;
	/**会议id*/
	private String meetid;
	/**投票项*/
	private String vpintro;
	/**是否已经点击*/
	private boolean click;


}
