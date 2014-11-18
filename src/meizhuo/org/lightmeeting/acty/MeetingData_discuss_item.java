package meizhuo.org.lightmeeting.acty;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;

public class MeetingData_discuss_item extends BaseActivity {
	
	
	String withComments = "1";
	
	@InjectView(R.id.comment_lv)ListView   comment_lv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item);
		
		initData();
		initLayout();
	}
	

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		String discussid = getIntent().getStringExtra("discussid");
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}

}
