 package meizhuo.org.lightmeeting.fragment;


import java.util.ArrayList;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.Lm_meeting_addnewmeet;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.acty.Update_meeting;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import meizhuo.org.lightmeeting.adapter.LMListAdapter.OnItemClickListener;
import meizhuo.org.lightmeeting.adapter.LMListAdapter.OnUpdateBtnClickListener;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Meeting;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.dimen;
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
	LoadingDialog dialog;
	
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
	
	protected void initData(){
		data = new ArrayList<Meeting>();
		adapter = new LMListAdapter(getActivity(), data);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(final int position) {
				// TODO Auto-generated method stub
				toast("" + position);
				MeetingAPI.deleteMeeting(data.get(position).getId(), new JsonResponseHandler() {
					
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						if(dialog == null){
							dialog = new LoadingDialog(getActivity());
							dialog.setText("正在删除！");
							dialog.show();
						}
					}
					
					@Override
					public void onOK(Header[] headers, JSONObject obj) {
						// TODO Auto-generated method stub
						L.i(obj.toString());
						try {
							if(obj.getString("code").equals("20000")){
								if(dialog.isShowing()){
									dialog.dismiss();
									dialog =null;
								}
								toast("删除成功");
								data.remove(position);
								adapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFaild(int errorType, int errorCode) {
						// TODO Auto-generated method stub
						toast("网络不给力,请检查你的网络设置!");
						return ;
						
					}
				});
			
				
			}
		});
		
		adapter.setOnUpdateBtnClickListener(new OnUpdateBtnClickListener() {
			@Override
			public void onUpdateClick(int position) {
				// TODO Auto-generated method stub
				Intent intent = new  Intent(getActivity(), Update_meeting.class);
				intent.putExtra("id", data.get(position).getId());
				intent.putExtra("title", data.get(position).getTitle());
				intent.putExtra("intro", data.get(position).getIntro());
				intent.putExtra("address", data.get(position).getAddress());
				intent.putExtra("starttime", data.get(position).getStarttime());
				intent.putExtra("endtime", data.get(position).getEndtime());
				startActivity(intent);
			}
		});
	}
	@Override
	protected void initLayout(){
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
				if(meetinglist.size()<5)
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
				swipeRefreshLayout.setRefreshing(false);
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
		Intent it  =  new Intent(getActivity(), MeetingData.class);
		it.putExtra("meetid", data.get(position).getId());
		startActivity(it);
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
