package meizhuo.org.lightmeeting.acty;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.InjectView;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.RelationListAdapter;
import meizhuo.org.lightmeeting.api.RelationAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Relation;

public class RelationList extends BaseActivity implements OnRefreshListener,OnScrollListener {

	
	
	@InjectView(R.id.relation_list) ListView lv_relation;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	RelationListAdapter adapter;
	List<Relation>data;
	
	String page = "1", limit = "";
	boolean hasMore = true, isloading = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.acty_relation_list);
		initData();
		initLayout();
	}
	
	
	@Override
	protected void initData() {
		data  =  new ArrayList<Relation>();
		adapter = new RelationListAdapter(this, data);
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
					data.clear();
					data.addAll(relationList);
					adapter.notifyDataSetChanged();
					page = "1";
					if (relationList.size() == 0) {
						toast("没有参加任何会议");
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
		// TODO Auto-generated method stub
		
	}


}
