package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.EditTextUtils;
import meizhuo.org.lightmeeting.utils.StringUtils;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.os.Bundle;
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
	@InjectView(R.id.radioMale) RadioButton maleRadio;
	@InjectView(R.id.radioFemale) RadioButton femaleRadio;
	@InjectView(R.id.acty_register_et_email) EditText register_et_email;
//	@InjectView(R.id.acty_register_et_company) EditText register_et_company;
//	@InjectView(R.id.acty_register_et_position) EditText register_et_position;
//	@InjectView(R.id.acty_register_et_birth) EditText register_et_birth;
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
		//用户登录
		UserAPI.login(EditTextUtils.getText(login_et_username), EditTextUtils.getText(login_et_password), new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if (dialog == null)
					dialog = new LoadingDialog(Login.this);
				dialog.setText("正在登录");
				dialog.show();
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						if(dialog.isShowing())
						{
							dialog.dismiss();
							dialog = null;
						}
						String message = obj.getString("response");
						toast(message);
						return ;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					toast("出现了异常!");
				}
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
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
		
	}
	//点击就进行注册
	@OnClick(R.id.acty_register_btn_regist) public void regist(){
		
	}
	//取消注册
	@OnClick(R.id.acty_register_btn_cancle) public void cacle(){
		
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

}
