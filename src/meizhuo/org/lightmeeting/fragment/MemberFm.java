package meizhuo.org.lightmeeting.fragment;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MdMemberBusinessCard;
import meizhuo.org.lightmeeting.adapter.MemberAdapter;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Member;
import android.content.Intent;
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

public class MemberFm extends BaseFragment implements OnRefreshListener, OnScrollListener {

	@InjectView(R.id.lv) ListView lv;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	
	MemberAdapter adapter; 
	 String meetid ;
	 List<Integer>pics;
	
	List<Member>data;
	String page = "1";
	boolean hasMore = true,isloading = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_lmlist);
	 Bundle idbundle = getArguments();
	 meetid = idbundle.getString("meetid");
	 initData();
	 initLayout();
	 return contentView;
	}
	
	protected void initData(){
		pics = new ArrayList<Integer>();
		pics.add(R.drawable.aa_pic_head1);
		pics.add(R.drawable.aa_pic_head2);
		pics.add(R.drawable.aa_pic_head3);
		pics.add(R.drawable.aa_pic_head4);
		pics.add(R.drawable.aa_pic_head5);
		data = new ArrayList<Member>();
		adapter = new MemberAdapter(getActivity(),data,pics);
	
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
	
	@OnItemClick(R.id.lv) public void ToBusinessCard(int position){
		Intent intent =  new Intent(getActivity(), MdMemberBusinessCard.class);
		intent.putExtra("memberid", data.get(position).getId());
		intent.putExtra("nickname", data.get(position).getNickname());
		intent.putExtra("checkin", data.get(position).getCheckin());
		startActivity(intent);
	}
	
	@Override
	public void onRefresh() {
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
				if(member.size() < 10){
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
		if (swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount !=0 && hasMore){
			isloading = true;
			onLoadMore();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

	

}
