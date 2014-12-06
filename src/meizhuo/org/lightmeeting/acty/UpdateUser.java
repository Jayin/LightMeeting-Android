package meizhuo.org.lightmeeting.acty;

import java.util.Calendar;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.UserAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class UpdateUser extends BaseActivity {
	
	
	@InjectView(R.id.lm_usercard_nickname) EditText lm_usercard_nickname;
	@InjectView(R.id.lm_usercard_birth) TextView lm_usercard_birth;
	@InjectView(R.id.lm_usercard_birth_item) LinearLayout lm_usercard_birth_item;
	@InjectView(R.id.lm_usercard_sex) TextView lm_usercard_sex;
	@InjectView(R.id.lm_usercard_company) EditText lm_usercard_company;
	@InjectView(R.id.lm_usercard_position) EditText lm_usercard_position;
	@InjectView(R.id.lm_usercard_contactphone) EditText lm_usercard_contactphone;
	@InjectView(R.id.lm_usercard_contactemail) EditText lm_usercard_contactemail;
	@InjectView(R.id.lm_usercard_changesex) LinearLayout lm_usercard_changesex;
	LoadingDialog dialog;
	ActionBar mActionBar;
	String nickname,birth,sex,company,position,phone,email;
	String birthday;
	String chooseSex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.acty_usercard_edit);
		initData();
		initLayout();
		registerForContextMenu(lm_usercard_changesex);
	}
	@Override
	protected void initData() {
		nickname = getIntent().getStringExtra("nickname");
		birth = getIntent().getStringExtra("birth");
		sex = getIntent().getStringExtra("sex");
		company = getIntent().getStringExtra("company");
		position = getIntent().getStringExtra("position");
		phone = getIntent().getStringExtra("phone");
		email = getIntent().getStringExtra("email");
	}
	
	/**改变性别*/
	@OnClick(R.id.lm_usercard_changesex) public void changesex(){
		lm_usercard_changesex.showContextMenu();
	}
	
	@Override
	protected void initLayout() {
		lm_usercard_nickname.setText(nickname);
		lm_usercard_birth.setText(birth);
			lm_usercard_sex.setText(sex);
		lm_usercard_company.setText(company);
		lm_usercard_position.setText(position);
		lm_usercard_contactphone.setText(phone);
		lm_usercard_contactemail.setText(email);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

	}
	 
	/**
	 * 选择生日
	 */
	@OnClick(R.id.lm_usercard_birth_item) public void chooseBirth(){
		Calendar calendar = Calendar.getInstance();
		Dialog datedialog = null;
		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year,
					int month, int dayOfMonth) {
				birthday = year + "-" + (month+1) + "-" + dayOfMonth;
				lm_usercard_birth.setText(birthday);
			}
		};
		datedialog = new DatePickerDialog(this, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datedialog.show();
	}
	
	@OnClick(R.id.lm_usercard_save) public void save_userinfo(){
		nickname = lm_usercard_nickname.getText().toString();
		if(birthday!= null)
		{
			birth = birthday;
		}
		if(chooseSex != null)
		{
			sex = chooseSex;
		}
		company = lm_usercard_company.getText().toString();
		position = lm_usercard_position.getText().toString();
		phone = lm_usercard_contactphone.getText().toString();
		email = lm_usercard_contactemail.getText().toString();
		if(sex.equals("女")){
			sex ="f";
		}else{
			sex = "m";
		}
		UserAPI.update(nickname, sex, phone, email, company, position, birth, new JsonHandler(){
			@Override
			public void onStart() {
				if(dialog==null)
				{
					dialog = new LoadingDialog(getContext());
					dialog.setText("正在更新资料!");
				}
				dialog.show();
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				if(dialog.isShowing())
				{
					dialog.dismiss();
					dialog = null;
				}
				toast("更新成功");
				Intent it = new Intent(UpdateUser.this, BusinessCard.class);
				String nickname1 = nickname;
				String birth1 = birth;
				String sex1 = sex;
				String company1 = company;
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
				UpdateUser.this.setResult(205, it);
				UpdateUser.this.finish();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				if(dialog.isShowing())
				{
					dialog.dismiss();
					dialog = null;
				}
				return ;
			}
			
		});
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(0, 1, 0, "男");
		menu.add(0, 2, 0, "女");

		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo itemInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		switch (item.getItemId()) {
		case 1:
			lm_usercard_sex.setText("男");
			chooseSex ="男";
			break;
		case 2:
			lm_usercard_sex.setText("女");
			chooseSex = "女";
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
