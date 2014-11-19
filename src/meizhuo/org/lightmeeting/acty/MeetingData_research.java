package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.model.Research;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Adapter;
import android.widget.ListView;
import butterknife.InjectView;

public class MeetingData_research extends BaseActivity{
	
	
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.research_lv) ListView  research_lv;
	
	String meetid;
	List<Research>data;
	boolean hasMore = true,isloading = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lv_meetingdata_research);
		
		initData();
		initLayout();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid  = getIntent().getStringExtra("meetid");
		data = new ArrayList<Research>();
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}

}
