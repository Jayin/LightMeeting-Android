package meizhuo.org.lightmeeting.acty;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.app.AppInfo;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.User;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ViewFlipper;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 登录注册页面
 * @author Jason
 *
 */
public class Login extends BaseActivity {
	
	@InjectView(R.id.acty_login_flipper) ViewFlipper flipper;
	/**注册*/
	@InjectView(R.id.acty_register_et_username) EditText register_et_username;
	@InjectView(R.id.acty_register_et_nickname) EditText register_et_nickname;
	@InjectView(R.id.acty_register_et_password) EditText register_et_password;
	
	@InjectView(R.id.acty_register_et_company) EditText acty_register_et_company;
	@InjectView(R.id.acty_register_et_position) EditText acty_register_et_position;
	@InjectView(R.id.acty_register_et_phone) EditText acty_register_et_phone;
	
	@InjectView(R.id.radioMale) RadioButton maleRadio;
	@InjectView(R.id.radioFemale) RadioButton femaleRadio;
	@InjectView(R.id.acty_register_et_email) EditText register_et_email;
	@InjectView(R.id.acty_register_btn_regist) Button register_btn_regist;
	@InjectView(R.id.acty_register_btn_cancle) Button register_btn_cancle;
	/**登录**/
	@InjectView(R.id.acty_login_et_username) EditText login_et_username;
	@InjectView(R.id.acty_login_et_password) EditText login_et_password;
	@InjectView(R.id.acty_login_btn_login) Button login_btn_login;
	@InjectView(R.id.acty_login_btn_regist) Button login_btn_regist;
	LoadingDialog dialog;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState,R.layout.acty_login);
	
		initFlipper();
		dialog = new LoadingDialog(this);
	}
	
	//登录
	@OnClick(R.id.acty_login_btn_login) public void login(){
		if(!(AndroidUtils.isNetworkConnected(this)))
		{
			toast("请打开你的网络开关");
			return ;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(login_et_username))||StringUtils.isEmpty(EditTextUtils.getText(login_et_password))){
			toast("不能为空");
			return ;
		}
		UserAPI.login(EditTextUtils.getText(login_et_username), EditTextUtils.getText(login_et_password),new JsonHandler(){
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if (dialog == null)
					dialog = new LoadingDialog(Login.this);
				dialog.setText("正在登录");
				dialog.show();
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				saveLoginInfo();
				if(dialog.isShowing())
				{
					dialog.dismiss();
					dialog = null;
				}
				toast("登录成功");
				//设置推送标签
				App app = (App)getApplication();
				app.addTag(obj.getJSONObject("response").getString("id"), App.TAG_TYPE_PERSON);
				closeActivity();
			}

			@Override
			public void onError(int error_code, Header[] headers, JSONObject obj)
					throws Exception {
				
				if(dialog.isShowing())
				{
					dialog.dismiss();
					dialog = null;
				}
				String msg = obj.getString("msg");
				toast(msg);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				if(dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}
				toast("网络不给力,请检查你的网络设置 ！");
			}
		});

	}
	//去注册
	@OnClick(R.id.acty_login_btn_regist) public void to_regist(){
		flipper.showNext();
	}
	/**
	 @InjectView(R.id.acty_register_et_username) EditText register_et_username;
	@InjectView(R.id.acty_register_et_nickname) EditText register_et_nickname;
	@InjectView(R.id.acty_register_et_password) EditText register_et_password;
	@InjectView(R.id.radioMale) RadioButton maleRadio;
	@InjectView(R.id.radioFemale) RadioButton femaleRadio;
	@InjectView(R.id.acty_register_et_email) EditText register_et_email;
		
	 */
	//点击就进行注册
	@OnClick(R.id.acty_register_btn_regist) public void regist(){
		if(!(AndroidUtils.isNetworkConnected(this))){
			toast("请先打开您的网络开关!");
			return ;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(register_et_username)))
		{
			toast("用户名不能为空");
			return;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(register_et_nickname)))
		{
			toast("昵称不能为空");
			return;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(register_et_password)))
		{
			toast("密码不能为空");
			return;
		}
		if(StringUtils.isEmpty(EditTextUtils.getText(register_et_email)))
		{
			toast("电子邮箱不能为空");
			return;
		}
		if(!StringUtils.isEmail(EditTextUtils.getText(register_et_email))){
			toast("电子邮箱格式不正确");
			return ;
		}
		String username =EditTextUtils.getText(register_et_username); 
		String nickname =EditTextUtils.getText(register_et_nickname); 
		String password =EditTextUtils.getText(register_et_password); 
		String email =EditTextUtils.getText(register_et_email); 
		String sex = "";
		boolean maleIsChecked = maleRadio.isChecked(); //男性被点击
		if(maleIsChecked) sex = "男";
		boolean femaleIsChecked = femaleRadio.isChecked(); //女性被点击
		if(femaleIsChecked) sex = "女";
		String company =EditTextUtils.getText(acty_register_et_company); 
		String position =EditTextUtils.getText(acty_register_et_position); 
		String phone =EditTextUtils.getText(acty_register_et_phone); 

		/**
		 @InjectView(R.id.acty_register_et_company) EditText acty_register_et_company;
	@InjectView(R.id.acty_register_et_position) EditText acty_register_et_position;
	@InjectView(R.id.acty_register_et_phone) EditText acty_register_et_phone;
		 */
		UserAPI.regist(username, nickname, password, sex, email,company,position,phone, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(dialog == null){
					dialog = new LoadingDialog(Login.this);
				}
				dialog.show();
				dialog.setText("正在注册");
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				
				try {
					if(obj.getString("error_code").equals("40000")){
						if(dialog.isShowing())
						{
							dialog.dismiss();
							dialog = null;
						}
						String msg = obj.getString("msg");
						toast(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				try {
					if(obj.getString("code").equals("20000")){
						L.i(obj.toString());
						if(dialog.isShowing())
						{
							dialog.dismiss();
							dialog = null;
						}
						toast("注册成功");
						flipper.showPrevious();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
		
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	//取消注册
	@OnClick(R.id.acty_register_btn_cancle) public void cacle(){
		flipper.showPrevious();
	}
	
	
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}
	
	private void initFlipper(){
		flipper.setInAnimation(this, R.anim.push_up_in);
		flipper.setOutAnimation(this, R.anim.push_up_out);
	}
	
	/**
	 * 保存用户登录信息
	 */
	private void saveLoginInfo(){
		/**
		 @InjectView(R.id.acty_login_et_username) EditText login_et_username;
	@InjectView(R.id.acty_login_et_password) EditText login_et_password;
		 */
		User user = new User();
		AppInfo.setUser(getContext(), user);
		AppInfo.setUsername(getContext(), login_et_username.getText().toString());
		AppInfo.setUserPSW(getContext(), login_et_password.getText().toString());
		openActivity(MainActivity.class);
	}
	

}
