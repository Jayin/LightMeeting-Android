package meizhuo.org.lightmeeting.model;

public class Doc {
	
	
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
	@Override
	public String toString() {
		return "Doc [id=" + id + ", meetid=" + meetid + ", memberid="
				+ memberid + ", title=" + title + ", content=" + content
				+ ", ctime=" + ctime + "]";
	}
	/**文档id*/
	private String id;
	/**会议id*/
	private String meetid;
	/**成员id*/
	private String memberid;
	/**文档标题*/
	private String title;
	/**文档内容*/
	private String content;
	/**文档内容*/
	private String ctime;

}
