package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_research_adapter;
import meizhuo.org.lightmeeting.api.ResearchAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Research;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class MeetResearch extends BaseActivity implements OnRefreshListener, OnScrollListener{
	
	
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.research_lv) ListView  research_lv;
	
	String meetid;
	List<Research>data;
	boolean hasMore = true,isloading = false;
	MeetingData_research_adapter adapter;
	ActionBar mActionBar;
	String page = "1",limit="";
	LoadingDialog loadingDialog;
	
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
		adapter = new MeetingData_research_adapter(this, data);
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		research_lv.setAdapter(adapter);
		research_lv.setOnScrollListener(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("调查列表");
		onRefresh();
	}
	
	@OnItemClick(R.id.research_lv) public void research_item(int position){
		Intent intent = new Intent(this, MeetResearchList.class);
		intent.putExtra("research_id", data.get(position).getId());
		intent.putExtra("research_title", data.get(position).getTitle());
		startActivity(intent);
	}
	
	
	@Override
	public void onRefresh() {
		page="1";
		ResearchAPI.getResearchList(meetid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Research>researchlist =  Research.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(researchlist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(researchlist.size() <10){
					hasMore = false;
				}else{
					hasMore = true;
				}
				if(researchlist.size() == 0){
					toast("暂无调查数据!");
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
/*	ResearchAPI.getResearchList(meetid,page,limit, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000"))
					{
						List<Research>researchlist =  Research.create_by_jsonarray(obj.toString());
						data.clear();
						data.addAll(researchlist);
						adapter.notifyDataSetChanged();
						page = "1";
						if(researchlist.size() <10){
							hasMore = false;
						}else{
							hasMore = true;
						}
						if(researchlist.size() == 0){
							toast("暂无调查数据!");
							return ;
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				toast("出错了，请检查你的网络设置!");
				
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});*/
	}
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i+=1;
		page = String.valueOf(i);
		ResearchAPI.getResearchList(meetid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Research>researchlist = Research.create_by_jsonarray(obj.toString());
				data.addAll(researchlist);
				adapter.notifyDataSetChanged();
				hasMore = true;
				if(obj.isNull("response")||researchlist.size()<10)
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
/*		ResearchAPI.getResearchList(meetid,page,limit, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				L.i("onloadmore"+ obj.toString());
				try {
					if(obj.getString("code").equals("20000")){
						List<Research>researchlist = Research.create_by_jsonarray(obj.toString());
						data.addAll(researchlist);
						adapter.notifyDataSetChanged();
						hasMore = true;
						if(obj.isNull("response")||researchlist.size()<10)
						{
							hasMore = false;
							toast("数据加载完毕!");
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				toast("网络不给力，请检查你的网络设置!");
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
				
			}
		});*/
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
