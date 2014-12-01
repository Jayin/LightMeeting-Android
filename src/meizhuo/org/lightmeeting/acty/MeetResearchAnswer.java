package meizhuo.org.lightmeeting.acty;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.utils.EditTextUtils;

/**
 * 回答问题
 * @author Jason
 *
 */
public class MeetResearchAnswer extends BaseActivity{

	
	
	@InjectView(R.id.et_comment) EditText et_answer;
	ActionBar mActionBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item_tocomment);
		
		initData();
		initLayout();
	}
	
	@OnClick(R.id.to_comment) public void answer(){
		String answer = EditTextUtils.getText(et_answer);
		if(answer.isEmpty()){
			toast("答案不能为空!");
			return ;
		}
		Intent backintent = new Intent();
		backintent.putExtra("answer", answer);
		setResult(321, backintent);
		this.finish();
	}
	

	@Override
	protected void initData() {
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("请填写您的答案");
	}

	@Override
	protected void initLayout() {
		et_answer.setFocusable(true);
		et_answer.setFocusableInTouchMode(true);
		et_answer.requestFocus();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager inputManager =  
		                 (InputMethodManager)et_answer.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
		             inputManager.showSoftInput(et_answer, 0);  
				
			}
		}, 500);
		
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
