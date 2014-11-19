package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter.OnEditListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter.OnHandleListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter.OnItemClickListener;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter.OnUpdateListener;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.model.Vote;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;

public class MeetingData_vote extends BaseActivity implements OnRefreshListener, OnScrollListener{

	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.vote_lv) ListView vote_lv;
	String meetid;
	List<Vote>data;
	MeetingData_vote_adapter adapter;
	boolean hasMore = true,isloading =false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lv_meetingdata_vote);
		
		initData();
		initLayout();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid = getIntent().getStringExtra("meetid");
		data = new ArrayList<Vote>();
		adapter = new MeetingData_vote_adapter(this, data);
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		vote_lv.setAdapter(adapter);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(int position) {
				// TODO Auto-generated method stub
				data.remove(position);
				adapter.notifyDataSetChanged();
				onRefresh();
			}
		});
		adapter.setOnUpdateListener(new OnUpdateListener() {
			
			@Override
			public void onUpdateListener(int position) {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setEnabled(false);
			}
		});
		adapter.setOnHandleListener(new OnHandleListener() {
			
			@Override
			public void onHandlerListener(int position) {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
		});
		adapter.setOnEditListener(new OnEditListener() {
			
			@Override
			public void onEditListener(int position) {
				// TODO Auto-generated method stub
				Intent it =  new Intent(MeetingData_vote.this, MeetingData_vote_update.class);
				it.putExtra("voteid", data.get(position).getId());
				it.putExtra("title", data.get(position).getTitle());
				it.putExtra("intro", data.get(position).getIntro());
			}
		});
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
