package meizhuo.org.lightmeeting.acty;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class Update_userdata extends BaseActivity {
	
	
	@InjectView(R.id.lm_usercard_nickname) EditText lm_usercard_nickname;
	@InjectView(R.id.lm_usercard_birth) EditText lm_usercard_birth;
	@InjectView(R.id.lm_usercard_sex) TextView lm_usercard_sex;
	@InjectView(R.id.lm_usercard_company) EditText lm_usercard_company;
	@InjectView(R.id.lm_usercard_position) EditText lm_usercard_position;
	@InjectView(R.id.lm_usercard_contactphone) EditText lm_usercard_contactphone;
	@InjectView(R.id.lm_usercard_contactemail) EditText lm_usercard_contactemail;
	LoadingDialog dialog;
	ActionBar mActionBar;
	String nickname,birth,sex,company,position,phone,email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.acty_usercard_edit);
		initData();
		initLayout();
	}

	/**
	 	intent.putExtra("nickname", member.getNickname());
			intent.putExtra("birth", member.getBirth());
			intent.putExtra("sex", member.getSex());
			intent.putExtra("company", member.getCompany());
			intent.putExtra("position", member.getPosition());
			intent.putExtra("phone", member.getPhone());
			intent.putExtra("email", member.getEmail());
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		nickname = getIntent().getStringExtra("nickname");
		birth = getIntent().getStringExtra("birth");
		sex = getIntent().getStringExtra("sex");
		company = getIntent().getStringExtra("company");
		position = getIntent().getStringExtra("position");
		phone = getIntent().getStringExtra("phone");
		email = getIntent().getStringExtra("email");
	}
	/**
	 @InjectView(R.id.lm_usercard_nickname) EditText lm_usercard_nickname;
	@InjectView(R.id.lm_usercard_birth) EditText lm_usercard_birth;
	@InjectView(R.id.lm_usercard_sex) TextView lm_usercard_sex;
	@InjectView(R.id.lm_usercard_company) EditText lm_usercard_company;
	@InjectView(R.id.lm_usercard_position) EditText lm_usercard_position;
	@InjectView(R.id.lm_usercard_contactphone) EditText lm_usercard_contactphone;
	@InjectView(R.id.lm_usercard_contactemail) EditText lm_usercard_contactemail;
	 */
	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_usercard_nickname.setText(nickname);
		lm_usercard_birth.setText(birth);
		if(sex.equals("f")){
			lm_usercard_sex.setText("女");
		}else{
			lm_usercard_sex.setText("男");
		}
		lm_usercard_company.setText(company);
		lm_usercard_position.setText(position);
		lm_usercard_contactphone.setText(phone);
		lm_usercard_contactemail.setText(email);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

	}
	
	@OnClick(R.id.lm_usercard_save) public void save_userinfo(){
		nickname = lm_usercard_nickname.getText().toString();
		birth = lm_usercard_birth.getText().toString();
		sex = lm_usercard_sex.getText().toString();
		company = lm_usercard_company.getText().toString();
		position = lm_usercard_position.getText().toString();
		phone = lm_usercard_contactphone.getText().toString();
		email = lm_usercard_contactemail.getText().toString();
		if(sex.equals("男")){
			sex ="m";
		}else{
			sex = "f";
		}
		UserAPI.update(nickname, sex, email, company, position, birth, new JsonResponseHandler() {
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						toast("更新成功");
						Intent it = new Intent(Update_userdata.this, BusinessCard.class);
						String nickname1 = nickname;
						String birth1 = birth;
						String sex1 = sex;
						String company1 = birth;
						String position1 = position;
						String phone1 = phone;
						String email1 = email;
						
						it.putExtra("nickname", nickname1);
						it.putExtra("birth",birth1);
						it.putExtra("sex", sex1);
						it.putExtra("company",company1);
						it.putExtra("position", position1);
						it.putExtra("phone", phone1);
						it.putExtra("email", email1);
						Update_userdata.this.setResult(205, it);
						Update_userdata.this.finish();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}

}
