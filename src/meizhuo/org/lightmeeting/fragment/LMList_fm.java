package meizhuo.org.lightmeeting.fragment;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.Lm_meeting_addnewmeet;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Meeting;
import meizhuo.org.lightmeeting.utils.L;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * 会议列表fragment
 * @author Jason
 *
 */
public class LMList_fm extends BaseFragment implements OnRefreshListener, OnScrollListener {
	private static final String TAG = "LMListFragment";
	
	@InjectView(R.id.lv) ListView lv;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	LMListAdapter adapter ;
	List<Meeting>data;
	String page="1",limit="";
	boolean hasMore = true, isloading=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_lmlist);
	 initData();
	 initLayout();
	 onRefresh();
	 return contentView;
	}
	
	private void initData(){
		data = new ArrayList<Meeting>();
		adapter = new LMListAdapter(getActivity(), data);
	}
	private void initLayout(){
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		MeetingAPI.getMeetingList(page,limit,new JsonResponseHandler() {
			
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				List<Meeting> meetinglist=Meeting.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(meetinglist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(meetinglist.size()<10)
				{
					hasMore = false;
				}else{
					hasMore = true;
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
			
			
		});
		
	}
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i+=1;
		page = String.valueOf(i);
		MeetingAPI.getMeetingList(page,limit,new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				List<Meeting> meetings = Meeting.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(meetings);
				adapter.notifyDataSetChanged();
				if(obj.isNull("response")||meetings.size()<5)
				{
					hasMore = false;
					toast("数据加载完毕!");
				}
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				toast("网络不给力,请检查你的网络设置!");
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
				isloading = false;
			}
		});
	}
	

	
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (swipeRefreshLayout.isRefreshing()|| isloading)
			return ;
		if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 0 && hasMore){
			isloading = true;
			onLoadMore();
		}
		
	}
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	
	@OnItemClick(R.id.lv) public void item_click(int position){
		openActivity(MeetingData.class);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.main, menu);
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.action_add_meeting:
			openActivity(Lm_meeting_addnewmeet.class);
			break;
		case R.id.action_sweep:
			toast("2");
			break;
		}
		
		return true;
	}



}
