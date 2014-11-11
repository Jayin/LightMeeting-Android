package meizhuo.org.lightmeeting.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserAPI {

	public UserAPI() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 注册
	 * @param username
	 * @param nickname
	 * @param password
	 * @param sex
	 * @param email
	 * @param responseHandler
	 */
	public static void regist(String username,String nickname,String password,
			String sex,String email,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("username", username);
		params.add("nickname", nickname);
		params.add("password", password);
		if(sex.equals("男")){
			params.add("sex", "m");
		}else if(sex.equals("女")){
			params.add("sex", "f");
		}
		params.add("email", email);
		RestClient.post("/home/member/addmember", params, responseHandler);
	}
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param responseHandler
	 */
	public static void login(String username,String password,AsyncHttpResponseHandler responseHandler){
		RequestParams params =  new RequestParams();
		params.add("username", username);
		params.add("password", password);
		RestClient.post("/home/member/login", params, responseHandler);
	}
	/**
	 * 更新会员资料
	 * @param nickname
	 * @param sex
	 * @param email
	 * @param company
	 * @param position
	 * @param birth
	 */
	public static void update(String nickname,String sex,String email,String company,String position,
			String birth,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		if(!(nickname.equals("")||nickname == null))
			params.add("nickname", nickname);
		if(!(sex.equals("")||sex == null)){
			if(sex.equals("男")){
				params.add("sex", "m");
			}else{
				params.add("sex", "f");
			}
		}
		if(!(email.equals("") || email == null))
			params.add("email", email);
		RestClient.post("/home/member/updatemember", params, responseHandler);
	}
	
	/**
	 * 修改密码
	 * @param password
	 * @param newpassword
	 * @param responseHandler
	 */
	public static void changePsw(String password,String newpassword,AsyncHttpResponseHandler responseHandler){
		RequestParams params = new RequestParams();
		params.add("password", password);
		params.add("newpassword", newpassword);
		RestClient.post("/home/member/login", params, responseHandler);
	}
	
	/**
	 * 退出登录
	 * @param responseHandler
	 */
	public static void logout(AsyncHttpResponseHandler responseHandler){
		RequestParams params  = new RequestParams();
		RestClient.post("/home/member/logout", params, responseHandler);
	}
}
