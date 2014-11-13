package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * 会员表
 * @author Jason
 *
 */
@SuppressWarnings("serial")
public class Member implements Serializable{
	
	/**使用json*/
	public static Member create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Member)gson.fromJson(json, Member.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 解析一个json数组
	 * @param jsonarray
	 * @return
	 */
	public static List<Member> create_by_jsonarray(String jsonarray){
		List<Member>list = null;
		JSONObject obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array = obj.getJSONArray("response");
			for(int i = 0; i < array.length();i++){
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}







	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", nickname="
				+ nickname + ", sex=" + sex + ", company=" + company
				+ ", position=" + position + ", phone=" + phone + ", email="
				+ email + ", birth=" + birth + ", lasttime=" + lasttime
				+ ", lastIP=" + lastIP + "]";
	}







	/**自增长id*/
	private String id;
	/**用户名*/
	private String username;
	/**昵称*/
	private String nickname;
	/**性别*/
	private String sex;
	/**公司id*/
	private String company;
	/**职位*/
	private String position;
	/**电话*/
	private String phone;
	/**电子邮箱*/
	private String email;
	/**生日*/
	private String birth;
	/**最后一次登录*/
	private String lasttime;
	/**最后一次登录ip*/
	private String lastIP;
	

}
