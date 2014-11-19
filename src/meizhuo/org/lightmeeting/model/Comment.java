package meizhuo.org.lightmeeting.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Comment implements Serializable{
	
	/**
	 * 解析单个评论
	 * @param json
	 * @return
	 */
	public static Comment create_by_json(String json){
		try {
			Gson gson = new Gson();
			return (Comment)gson.fromJson(json, Comment.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析一段评论列表
	 * @param jsonarray
	 * @return
	 */
	public static List<Comment>create_by_jsonarray(String jsonarray){
		List<Comment>list = new ArrayList<Comment>();
		JSONObject obj = null;
		JSONArray array = null;
		try {
			obj = new JSONObject(jsonarray);
			array  = obj.getJSONArray("response");
			for(int i=0 ; i< array.length();i++){
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
	public String getDiscussid() {
		return discussid;
	}
	public void setDiscussid(String discussid) {
		this.discussid = discussid;
	}
	public String getReply_menber() {
		return reply_menber;
	}
	public void setReply_menber(String reply_menber) {
		this.reply_menber = reply_menber;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public String getCip() {
		return cip;
	}
	public void setCip(String cip) {
		this.cip = cip;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", discussid=" + discussid
				+ ", reply_menber=" + reply_menber + ", author=" + author
				+ ", content=" + content + ", ctime=" + ctime + ", cip=" + cip
				+ "]";
	}
	/**评论id*/
	private String id;
	/**讨论id*/
	private String discussid;
	/**回复数量*/
	private String reply_menber;
	/**讨论发起人作者*/
	private String author;
	/**内容*/
	private String content;
	/**时间*/
	private String ctime;
	/**ip*/
	private String cip;

}
