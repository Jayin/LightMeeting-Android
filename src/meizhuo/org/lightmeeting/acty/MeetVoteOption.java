package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetVoteOptionAdapter;
import meizhuo.org.lightmeeting.adapter.MeetVoteOptionAdapter.ViewHolder;
import meizhuo.org.lightmeeting.api.VoteAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Option;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import org.apache.http.Header;
import org.json.JSONObject;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnClick;

public class MeetVoteOption extends BaseActivity implements OnRefreshListener,OnScrollListener{
	
	
	
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.option_lv)ListView   option_lv;
	
	ActionBar mActionBar;
	MeetVoteOptionAdapter adapter;
	String voteid,select_content,optionsid,title;
	
	boolean hasMore = true,isloading=false;
	List<Option>data;
	LoadingDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.item_meetdata_vote_item);
		initData();
		initLayout();
	}
	
	@OnClick(R.id.vote_confirm) public void to_vote(){
		VoteAPI.MemberVote(optionsid, voteid, new JsonHandler(){
			@Override
			public void onStart() {
				if(loadingDialog == null)
				{
					loadingDialog = new LoadingDialog(MeetVoteOption.this);
				}
				loadingDialog.setText("正在投票...");
				loadingDialog.show();
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				toast("投票成功!");
				MeetVoteOption.this.finish();
			}
			@Override
			public void onError(int error_code, Header[] headers, JSONObject obj)
					throws Exception {
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				String message = obj.getString("msg");
				toast(message);
				return ;
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				if(loadingDialog.isShowing()){
					loadingDialog.dismiss();
					loadingDialog = null;
				}
				toast("网络不给力，请检查你的网络设置!");
			}
		});
	}

	@Override
	protected void initData() {
		voteid =  getIntent().getStringExtra("voteid");
		title = getIntent().getStringExtra("title");
		data = new ArrayList<Option>();
		adapter = new MeetVoteOptionAdapter(this, data);
		
	}

	@Override
	protected void initLayout() {
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle("投票:" + title);
		
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		option_lv.setAdapter(adapter);
		option_lv.setOnScrollListener(this);
		option_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ViewHolder holder = (ViewHolder)view.getTag();
				if(data.get(position).isClick() == true){
					holder.vote_iv.setVisibility(View.GONE);
					data.get(position).setClick(false);
				}else{
					for(int i=0;i<data.size();i++){
						data.get(i).setClick(false);
					}
					adapter.notifyDataSetChanged();
					holder.vote_iv.setVisibility(View.VISIBLE);
					data.get(position).setClick(true);
					optionsid = data.get(position).getId();
				}
				
			}
		});
		onRefresh();
	}

	@Override
	public void onRefresh() {
		VoteAPI.getOptionList(voteid,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
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
	}
	
	private void onLoadMore(){
		VoteAPI.getOptionList(voteid,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
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
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if(swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount  !=0 && hasMore){
			isloading = true;
			onLoadMore();
		}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
