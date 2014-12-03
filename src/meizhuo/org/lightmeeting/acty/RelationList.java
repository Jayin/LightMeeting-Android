package meizhuo.org.lightmeeting.acty;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;

import butterknife.InjectView;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.RelationListAdapter;
import meizhuo.org.lightmeeting.api.RelationAPI;
import meizhuo.org.lightmeeting.api.RestClient;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Relation;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

public class RelationList extends BaseActivity implements OnRefreshListener,OnScrollListener {

	
	
	@InjectView(R.id.relation_list) ListView lv_relation;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	RelationListAdapter adapter;
	List<Relation>data;
	ActionBar mActionBar;
	
	String page = "1", limit = "";
	boolean hasMore = true, isloading = false;
	LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.acty_relation_list);
		dialog = new LoadingDialog(this);
		initData();
		initLayout();
	}
	
	
	@Override
	protected void initData() {
		data  =  new ArrayList<Relation>();
		adapter = new RelationListAdapter(this, data);
		mActionBar = getActionBar();
		mActionBar.setTitle("人脉");
		mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initLayout() {
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		lv_relation.setAdapter(adapter);
		lv_relation.setOnScrollListener(this);
		onRefresh();
	}


	@Override
	public void onRefresh() {
		page = "1";
		RelationAPI.getRelationList(page,limit,new JsonHandler(){
				@Override
				public void onStart() {
					swipeRefreshLayout.setRefreshing(true);
				}
				@Override
				public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
					List<Relation> relationList = Relation.create_by_jsonarray(obj
							.toString());
					L.i("解析正确了？" + relationList);
					data.clear();
					data.addAll(relationList);
					adapter.notifyDataSetChanged();
					page = "1";
					if (relationList.size() == 0) {
						toast("暂无人脉");
					}
					if (relationList.size() < 5) {
						hasMore = false;
					} else {
						hasMore = true;
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
	
	private void onLoadMore(){
		int i = Integer.parseInt(page);
		i += 1;
		page = String.valueOf(i);

		RelationAPI.getRelationList(page, limit, new JsonHandler() {
			@Override public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}

			@Override public void onOK(int statusCode, Header[] headers,
					JSONObject obj) throws Exception {
				List<Relation> relationList = Relation.create_by_jsonarray(obj
						.toString());
				data.addAll(relationList);
				adapter.notifyDataSetChanged();
				if (obj.isNull("response") || relationList.size() < 5) {
					hasMore = false;
					toast("数据加载完毕!");
				}
			}

			@Override public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				swipeRefreshLayout.setRefreshing(false);
				toast("网络不给力,请检查你的网络设置!");
				return;
			}

			@Override public void onFinish() {
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (swipeRefreshLayout.isRefreshing() || isloading)
			return;
		if (firstVisibleItem + visibleItemCount >= totalItemCount
				&& totalItemCount != 0 && hasMore) {
			isloading = true;
			onLoadMore();
		}
		
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.acty_relation, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		break;
		case R.id.add_relation:
			Intent openCameraIntent = new Intent(this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 330);

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 330 && resultCode == 331){
			String qrurl = data.getStringExtra("resultcode");
			L.i("resultcode执行" + qrurl);
			AsyncHttpClient client;
			client = RestClient.getClient();
			client.post(qrurl, new JsonHandler(){
				@Override
				public void onStart() {
					if (dialog == null) {
						dialog = new LoadingDialog(RelationList.this);
					}
					dialog.setText("正在添加人脉...");
					dialog.show();
				}
				@Override
				public void onOK(int statusCode, Header[] headers,
						JSONObject obj) throws Exception {
					if (dialog.isShowing()) {
						dialog.dismiss();
						dialog = null;
					}
					String response = obj.getString("response");
					toast("添加人脉成功");
					onRefresh();
				}
				@Override public void onError(int error_code, Header[] headers,
						JSONObject obj) throws Exception {
					L.i( "错误" + obj.toString());
					if (dialog.isShowing()) {
						dialog.dismiss();
						dialog = null;
					}
					String msg = obj.getString("msg");
					toast(msg);
					return ;
				}

				@Override public void onFailure(int statusCode,
						Header[] headers, byte[] data, Throwable arg3) {
					if (dialog.isShowing()) {
						dialog.dismiss();
						dialog = null;
					}
					toast("网络不给力,请检查你的网络设置!");
					return;
				}
			});
		}
	}

}
