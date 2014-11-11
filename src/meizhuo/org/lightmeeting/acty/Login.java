package meizhuo.org.lightmeeting.acty;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ViewFlipper;
import butterknife.InjectView;

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
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.acty_login);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}

}
