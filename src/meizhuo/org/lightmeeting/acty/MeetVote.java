package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_adapter;
import meizhuo.org.lightmeeting.api.VoteAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Vote;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class MeetVote extends BaseActivity implements OnRefreshListener, OnScrollListener{

	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.vote_lv) ListView vote_lv;
	String meetid;
	List<Vote>data;
	MeetingData_vote_adapter adapter;
	boolean hasMore = true,isloading =false;
	String page = "1",limit="";
	ActionBar mActionBar;
	
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
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		vote_lv.setAdapter(adapter);
		vote_lv.setOnScrollListener(this);
	
	
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("投票列表");
		
		onRefresh();
		
	}
	

	@Override
	public void onRefresh() {
		page="1";
		VoteAPI.getVoteList(meetid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Vote>votelist =  Vote.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(votelist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(votelist.size() <10){
					hasMore = false;
				}else{
					hasMore = true;
				}
				if(votelist.size() == 0){
					toast("暂无投票数据!");
					return ;
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				swipeRefreshLayout.setRefreshing(false);
				toast("出错了，请检查你的网络设置!");
				return ;
			}
			@Override
			public void onFinish() {
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});
	}
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i+=1;
		page = String.valueOf(i);
		VoteAPI.getVoteList(meetid, page, limit, new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Vote>votelist = Vote.create_by_jsonarray(obj.toString());
				data.addAll(votelist);
				adapter.notifyDataSetChanged();
				hasMore = true;
				if(obj.isNull("response")||votelist.size()<10)
				{
					hasMore = false;
					toast("数据加载完毕!");
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				swipeRefreshLayout.setRefreshing(false);
				toast("网络不给力，请检查你的网络设置!");
				return ;
			}
			
			@Override
			public void onFinish() {
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
			
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.meetvote, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case  android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if(swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount  !=0 && hasMore){
			
			isloading = true;
			onLoadMore();
		}
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	@OnItemClick(R.id.vote_lv) public void to_vote(int position){
		String voteid = data.get(position).getId();
		Intent it =  new Intent(this, MeetVoteOption.class);
		it.putExtra("voteid", voteid);
		it.putExtra("title", data.get(position).getTitle());
		startActivity(it);
	}


}
