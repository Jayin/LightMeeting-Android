package meizhuo.org.lightmeeting.fragment;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_fm_adapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.api.DocAPI;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Discuss;
import meizhuo.org.lightmeeting.model.Doc;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
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
public class MeetingData_fm extends BaseFragment  implements OnRefreshListener, OnScrollListener{

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
		// TODO Auto-generated method stub
	DocAPI.getDocList(meetid,page, limit, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				L.i("会议资料obj" + obj);
				try {
					if(obj.getString("code").equals("20000"))
					{
						
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
		});
	}
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i+=1;
		page = String.valueOf(i);
		DocAPI.getDocList(meetid,page, limit, new JsonResponseHandler() {
			
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
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					L.i("异常"+e.getMessage());
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





	
	

}
