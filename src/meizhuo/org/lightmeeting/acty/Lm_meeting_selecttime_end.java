package meizhuo.org.lightmeeting.acty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;

public class Lm_meeting_selecttime_end extends BaseActivity{

	@InjectView(R.id.lm_meeting_time) TextView lm_meeting_time;
	@InjectView(R.id.lm_meeting_update_time) TextView lm_meeting_update_time;
	String endtime;
	String date,time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lm_meeting_selecttime);
		initData();
		initLayout();
		
	}
	@OnClick(R.id.lm_meeting_select_date) public void select_date(){
		//用来获取日期和时间
		Calendar calendar = Calendar.getInstance();
		Dialog datedialog = null;
		DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year,
					int month, int dayOfMonth) {
				date = "" + year + "-" + (month+1) + "-" + dayOfMonth;
				lm_meeting_update_time.setText(date);
			}
		};
		datedialog = new DatePickerDialog(this, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datedialog.show();

	}
	
	@OnClick(R.id.lm_meeting_select_time) public void select_time(){
		Calendar calendar = Calendar.getInstance();
		Dialog timedialog = null;
		SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");       
		Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
		final String    DateStr    =    formatter.format(curDate);   
		TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker timerPicker, int hourOfDay,
					int minute) {
				if(date!= null){
					time = date + "   " + hourOfDay + ":" + minute;
					lm_meeting_update_time.setText(time);
				}else if(date == null){
					time = DateStr + "   " + hourOfDay + ":" + minute;
					lm_meeting_update_time.setText(time);
				}
				
			}
		};
		timedialog = new TimePickerDialog(this, timeListener,
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), false); // 是否为二十四制
		timedialog.show();
		
		
	}
	@OnClick(R.id.lm_meeting_confirm) public void confirm_update(){
		Intent intent = new Intent(this, Update_meeting.class);
		intent.putExtra("updatetime", lm_meeting_update_time.getText().toString());
			this.setResult(1001, intent);
			this.setResult(31, intent);
			this.setResult(203, intent);
		Lm_meeting_selecttime_end.this.finish();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		endtime = getIntent().getStringExtra("endtime");
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		lm_meeting_time.setText(endtime);
		
	}

}
