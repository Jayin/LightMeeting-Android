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

	
	
	/**http请求状态*/
	public final static int Start = 0;
	public final static int Logining = 1;
	public final static int Downloading = 2;
	public final static int Finish = 3;
	public final static int Fail = 4;
	
}
