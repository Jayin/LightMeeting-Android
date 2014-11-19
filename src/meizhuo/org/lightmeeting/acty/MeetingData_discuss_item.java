package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_discuss_item_adapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Comment;
import meizhuo.org.lightmeeting.model.Meeting;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;

/***
 * 评论列表（讨论中的一项）暂时处理：使用Listview 显示评论列表,不处理ListView跟ScollView的冲突事件
 * @author Jason
 *
 */
public class MeetingData_discuss_item extends BaseActivity implements OnRefreshListener,OnScrollListener{
	
	
	String withComments = "1";
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.comment_lv)ListView   comment_lv;
	MeetingData_discuss_item_adapter adapter;
	String discussid;
	String page="1",limit="";
	boolean hasMore = true,isloading=false;
	List<Comment>data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item);
		
		initData();
		initLayout();
	}
	

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		 discussid = getIntent().getStringExtra("discussid");
		 data =new ArrayList<Comment>();
		 adapter = new MeetingData_discuss_item_adapter(this, data);
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		comment_lv.setAdapter(adapter);
		comment_lv.setOnScrollListener(this);
		onRefresh();
	}



	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		DiscussAPI.getCommentlist(discussid, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				List<Comment>commentlist = Comment.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(commentlist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(commentlist.size() <10)
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
			
			private void onFinsh() {
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
		DiscussAPI.getCommentlist(discussid,new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				List<Comment> commentlist =Comment.create_by_jsonarray(obj.toString());
				data.addAll(commentlist);
				adapter.notifyDataSetChanged();
				if(obj.isNull("response")||commentlist.size()<5)
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
		if(swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount!=0 && hasMore){
			isloading = true;
			onLoadMore();
		}
		
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
