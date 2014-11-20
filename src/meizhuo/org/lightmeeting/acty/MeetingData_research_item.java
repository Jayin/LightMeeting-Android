package meizhuo.org.lightmeeting.acty;

import android.os.Bundle;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_research_item_adapter;
import meizhuo.org.lightmeeting.app.BaseActivity;

public class MeetingData_research_item extends BaseActivity {

	String researchid;
	MeetingData_research_item_adapter adapter;
	
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
		researchid = getIntent().getStringExtra("research_id");

	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub

	}

}
