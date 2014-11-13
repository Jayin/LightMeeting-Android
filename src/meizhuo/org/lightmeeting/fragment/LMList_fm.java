package meizhuo.org.lightmeeting.fragment;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Meeting;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
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
	String page="1";
	boolean hasMore = true, isloading=false;
	
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
		MeetingAPI.getMeetingList(new JsonResponseHandler() {
			
			
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
		toast("已经到达底部");
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




}
