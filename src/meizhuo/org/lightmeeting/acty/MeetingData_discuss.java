package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_adapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Discuss;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class MeetingData_discuss extends BaseActivity implements OnRefreshListener, OnScrollListener{
	
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.discuss_lv) ListView discuss_lv;
	private BroadcastReceiver mBroadcastReceiver;
	
	LoadingDialog loadingDialog;
	List<Discuss>data;
	boolean hasMore = true,isloading = false;
	MeetingData_discuss_adapter adapter;
	String meetid;
	String page = "1",limit="";
	
	ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.lv_meetingdata_discuss);
		
		openReceiver();
		initData();
		initLayout();
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		meetid = getIntent().getStringExtra("meetid");
		data = new ArrayList<Discuss>();
		adapter  = new MeetingData_discuss_adapter(this, data);
	}

	@Override
	protected void initLayout() {
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		discuss_lv.setAdapter(adapter);
		discuss_lv.setOnScrollListener(this);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("讨论列表");
		onRefresh();
	}
	

	@Override
	public void onRefresh() {
		page="1";
		DiscussAPI.getdiscusslist(meetid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				// TODO Auto-generated method stub
				List<Discuss>discusslist =  Discuss.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(discusslist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(discusslist.size() <10){
					hasMore = false;
				}else{
					hasMore = true;
				}
				if(discusslist.size() == 0){
					toast("暂无讨论数据!");
					return ;
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				// TODO Auto-generated method stub
				toast("出错了，请检查你的网络设置!");
				return ;
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});

		
	}
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i+=1;
		page = String.valueOf(i);
		DiscussAPI.getdiscusslist(meetid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Discuss>discusslist = Discuss.create_by_jsonarray(obj.toString());
				data.addAll(discusslist);
				adapter.notifyDataSetChanged();
				hasMore = true;
				if(obj.isNull("response")||discusslist.size()<10)
				{
					hasMore = false;
					toast("数据加载完毕!");
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				toast("网络不给力，请检查你的网络设置!");
				return ;
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
	
	private void openReceiver(){
		mBroadcastReceiver = new CreateDiscussBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.Action_Create_discuss_successful);
		filter.addAction(Constants.Action_Update_discuss_successful);
		registerReceiver(mBroadcastReceiver, filter);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.acty_discuss, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		/*创建讨论*/
	/*	case R.id.action_create_discuss:
			Intent intent =  new Intent(MeetingData_discuss.this, MeetingData_discuss_create.class);
			intent.putExtra("meetid", meetid);
			startActivity(intent);
			break;*/
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	
	
	@OnItemClick(R.id.discuss_lv) public void item_click(int position){
		String discussid = data.get(position).getId();
		Intent it =  new Intent(this, MeetingData_discuss_item.class);
		it.putExtra("discussid", discussid);
		it.putExtra("title", data.get(position).getTitle());
		it.putExtra("content", data.get(position).getContent());
		startActivity(it);
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
	
	class CreateDiscussBroadcast extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(action.equals(Constants.Action_Create_discuss_successful)||action.equals(Constants.Action_Update_discuss_successful)){
				onRefresh();
			}
			
		}
		
	}



}
