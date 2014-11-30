package meizhuo.org.lightmeeting.fragment;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MeetingData_docdetail;
import meizhuo.org.lightmeeting.adapter.MeetingData_fm_adapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.api.DocAPI;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Discuss;
import meizhuo.org.lightmeeting.model.Doc;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 会议资料的fragment
 * @author Jason
 *
 */
public class MeetdataFm extends BaseFragment  implements OnRefreshListener, OnScrollListener{

	String meetid;
	LoadingDialog loadingDialog ;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.meetdata_lv) ListView meetdata_lv;
	MeetingData_fm_adapter adapter;
	List<Doc>data;
	String page="1",limit="";
	boolean hasMore = true,isloading = false;
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_meetingdata);
	 Bundle mBundle = getArguments();
		meetid = mBundle.getString("meetid");
		L.i("meetid" + meetid);
	 initData();
	 initLayout();
	 return contentView;
	}

	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
		data = new ArrayList<Doc>();
		adapter = new MeetingData_fm_adapter(getActivity(), data);
	}


	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		meetdata_lv.setAdapter(adapter);
		meetdata_lv.setOnScrollListener(this);
		onRefresh();
	}
	

	@Override
	public void onRefresh() {
		DocAPI.getDocList(meetid,page, limit,new JsonHandler(){
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				// TODO Auto-generated method stub
				List<Doc>Doclist =  Doc.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(Doclist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(Doclist.size() <10){
					hasMore = false;
				}else{
					hasMore = true;
				}
				if(Doclist.size() == 0){
					toast("暂无会议文档!");
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
		DocAPI.getDocList(meetid, page, limit, new JsonHandler(){
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				// TODO Auto-generated method stub
				List<Doc>doclist = Doc.create_by_jsonarray(obj.toString());
				data.addAll(doclist);
				adapter.notifyDataSetChanged();
				hasMore = true;
				if(obj.isNull("response")||doclist.size()<10)
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
	
	/**
	 * 
	 * @param position
	 */
	@OnItemClick(R.id.meetdata_lv) public void opendoc(int position){
		Intent it = new Intent(getActivity(), MeetingData_docdetail.class);
		it.putExtra("docid", data.get(position).getId());
		it.putExtra("title", data.get(position).getTitle());
		startActivity(it);
	}





	
	

}