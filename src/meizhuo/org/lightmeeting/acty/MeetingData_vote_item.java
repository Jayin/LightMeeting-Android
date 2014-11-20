package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.DialerFilter;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_vote_item_adapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.api.VoteAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.model.Discuss;
import meizhuo.org.lightmeeting.model.Option;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class MeetingData_vote_item extends BaseActivity implements OnRefreshListener,OnScrollListener{
	
	
	
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.option_lv)ListView   option_lv;
	@InjectView(R.id.option_select) TextView option_select;
	
	ActionBar mActionBar;
	MeetingData_vote_item_adapter adapter;
	String voteid,title,intro,select_content,optionsid;
	
	boolean hasMore = true,isloading=false;
	List<Option>data;
	LoadingDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_vote_item);
		initData();
		initLayout();
	}
	@OnClick(R.id.vote_confirm) public void to_vote(){
		VoteAPI.MemberVote(optionsid, voteid, new JsonResponseHandler() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				if(loadingDialog == null)
				{
					loadingDialog = new LoadingDialog(MeetingData_vote_item.this);
				}
				loadingDialog.setText("正在投票");
				loadingDialog.show();
			}
			
			@Override
			public void onOK(Header[] headers, JSONObject obj) {
				// TODO Auto-generated method stub
				try {
					if(obj.getString("code").equals("20000")){
						if(loadingDialog.isShowing()){
							loadingDialog.dismiss();
							loadingDialog = null;
						}
						toast("投票成功!");
						MeetingData_vote_item.this.finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFaild(int errorType, int errorCode) {
				// TODO Auto-generated method stub
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				toast("网络不给力，请检查你的网络设置!");
			}
			
		});
		 
	}
	
	@OnItemClick(R.id.option_lv) public void to_select(int position){
		option_select.setText(data.get(position).getVpintro().toString());
		select_content = data.get(position).getVpintro().toString();
		optionsid = data.get(position).getId();
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		voteid =  getIntent().getStringExtra("voteid");
		title = getIntent().getStringExtra("title");
		intro = getIntent().getStringExtra("intro");
		
		data = new ArrayList<Option>();
		adapter = new MeetingData_vote_item_adapter(this, data);
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("进行投票");
		
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		option_lv.setAdapter(adapter);
		option_lv.setOnScrollListener(this);
		onRefresh();
	}



	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
	VoteAPI.getOptionList(voteid,new JsonResponseHandler() {
			
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
						List<Option>optionlist =  Option.create_by_jsonarray(obj.toString());
						data.clear();
						data.addAll(optionlist);
						adapter.notifyDataSetChanged();
						if(optionlist.size() <10){
							hasMore = false;
						}else{
							hasMore = true;
						}
						if(optionlist.size() == 0){
							toast("暂无投票项!");
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
				toast("errorcode" + errorCode);
				toast("errortype" + errorType);
				toast("voteid" + voteid);
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
		VoteAPI.getOptionList(voteid,new JsonResponseHandler() {
			
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
						List<Option>optionlist = Option.create_by_jsonarray(obj.toString());
						data.addAll(optionlist);
						adapter.notifyDataSetChanged();
						hasMore = true;
						if(obj.isNull("response")||optionlist.size()<10)
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
