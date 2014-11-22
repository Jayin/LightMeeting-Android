package meizhuo.org.lightmeeting.utils;

/**
 * 常量跟注册的Action都放这里
 * @author Jason
 *
 */
public class Constants {
	
	/**Action*/
	/**去登陆*/
	public static final String Action_To_Login = "To_Login";
	/**用户登录成功*/
	public static final String Action_Login_In_Successful = "Login_In_Successful";
	/**用户登录失败*/
	public static final String Action_Login_failed = "Login_failed";
	/**还没登录过*/
	public static final String Action_First_Login = "First_Login";
	/**更新会议开始时间*/
	public static final String Action_Update_Start_Time = "Update_Start_Time";
	/**更新会议结束时间*/
	public static final String Action_Update_End_Time = "Update_End_Time";
	/**创建讨论成功*/
	public static final String Action_Create_discuss_successful ="Create_discuss_successful";
	/**更新讨论成功*/
	public static final String Action_Update_discuss_successful ="Update_discuss_successful";
	
	/**接受版本信息*/
	public static final String Action_Receive_VersionInfo ="Action_Receive_VersionInfo";
	/**检查版本*/
	public static final String Action_checkVersion ="checkVersion";
	
	

	
	
	/**http请求状态*/
	public final static int Start = 0;
	public final static int Logining = 1;
	public final static int Downloading = 2;
	public final static int Finish = 3;
	public final static int Fail = 4;
	
}
