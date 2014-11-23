package meizhuo.org.lightmeeting.fragment;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MemberAdapter;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Member;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
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

public class Member_fm extends BaseFragment implements OnRefreshListener, OnScrollListener {

	@InjectView(R.id.lv) ListView lv;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	
	MemberAdapter adapter; 
	 String meetid ;
	
	List<Member>data;
	String page = "1";
	boolean hasMore = true,isloading = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_lmlist);
	 Bundle idbundle = getArguments();
	 meetid = idbundle.getString("meetid");
	 initData();
	 initLayout();
	 return contentView;
	}
	
	protected void initData(){
		data = new ArrayList<Member>();
		adapter = new MemberAdapter(getActivity(),data);
	}
	
	protected void initLayout(){
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		onRefresh();
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		MeetingAPI.getJoinMember(meetid, new JsonHandler(){
			
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Member>member = Member.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(member);
				adapter.notifyDataSetChanged();
				page = "1";
				if(member.size() < 20){
					hasMore = false;
				}else{
					hasMore = true;
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				toast("网络不给力,请检查你的网络设置!");
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
		MeetingAPI.getJoinMember(meetid,new JsonHandler(){
			
				@Override
				public void onStart() {
					swipeRefreshLayout.setRefreshing(true);
				}
				@Override
				public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
					List<Member>memberlist = Member.create_by_jsonarray(obj.toString());
					data.addAll(memberlist);
					adapter.notifyDataSetChanged();
					if(obj.isNull("response")||memberlist.size()<20)
					{
						hasMore = false;
						toast("数据加载完毕!");
					}
				}
				
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
					toast("网络不给力,请检查你的网络设置!");
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
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount !=0 && hasMore){
			isloading = true;
			onLoadMore();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	

}
