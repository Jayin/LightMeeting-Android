package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetDiscussToCommentAdapter;
import meizhuo.org.lightmeeting.api.DiscussAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Comment;
import meizhuo.org.lightmeeting.utils.Constants;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnClick;

/***
 * 评论列表（讨论中的一项）暂时处理：使用Listview 显示评论列表,不处理ListView跟ScollView的冲突事件
 * 
 * @author Jason
 * 
 */
public class MeetDiscussToCommentlist extends BaseActivity implements
		OnRefreshListener, OnScrollListener {

	String withComments = "1";
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	@InjectView(R.id.comment_lv) ListView comment_lv;
	@InjectView(R.id.to_comment) LinearLayout to_comment;

	MeetDiscussToCommentAdapter adapter;
	String discussid;
	String page = "1", limit = "";
	boolean hasMore = true, isloading = false;
	List<Comment> data;
	ActionBar mActionBar;
	String title;
	String with_member = "1";
	BroadcastReceiver mBroadcastReceiver;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.item_meetdata_discuss_item);
		openReceiver();
		initData();
		initLayout();
	}

	private void openReceiver() {
		mBroadcastReceiver = new CommentBroadcastReceiver();
		IntentFilter filter = new IntentFilter(
				Constants.Action_Comment_Successful);
		registerReceiver(mBroadcastReceiver, filter);
	}

	@Override protected void initData() {
		discussid = getIntent().getStringExtra("discussid");
		title = getIntent().getStringExtra("title");
		data = new ArrayList<Comment>();
		adapter = new MeetDiscussToCommentAdapter(this, data);

	}

	@Override protected void initLayout() {
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		comment_lv.setAdapter(adapter);
		comment_lv.setOnScrollListener(this);
		onRefresh();
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(title);
	}

	@OnClick(R.id.to_comment) public void to_comment() {
		Intent intent = new Intent(this, MeetDiscussToComment.class);
		intent.putExtra("discussid", discussid);
		startActivity(intent);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		if (mBroadcastReceiver != null)
			unregisterReceiver(mBroadcastReceiver);
	}

	@Override public void onRefresh() {
		page = "1";
		DiscussAPI.getCommentlist(discussid, with_member, page, limit,
				new JsonHandler() {
					@Override public void onStart() {
						swipeRefreshLayout.setRefreshing(true);
					}

					@Override public void onOK(int statusCode,
							Header[] headers, JSONObject obj) throws Exception {
						List<Comment> commentlist = Comment
								.create_by_jsonarray(obj.toString());
						data.clear();
						data.addAll(commentlist);
						adapter.notifyDataSetChanged();
						page = "1";
						if (commentlist.size() < 10) {
							hasMore = false;
						} else {
							hasMore = true;
						}
					}

					@Override public void onFailure(int statusCode,
							Header[] headers, byte[] data, Throwable arg3) {
						swipeRefreshLayout.setRefreshing(false);
						toast("出错了，请检查你的网络设置!");
					}

					@Override public void onFinish() {
						swipeRefreshLayout.setRefreshing(false);
						isloading = false;
					}
				});

	}

	private void onLoadMore() {
		int i = Integer.parseInt(page);
		i += 1;
		page = String.valueOf(i);
		DiscussAPI.getCommentlist(discussid, with_member, page, limit,
				new JsonHandler() {
					@Override public void onStart() {
						swipeRefreshLayout.setRefreshing(true);
					}

					@Override public void onOK(int statusCode,
							Header[] headers, JSONObject obj) throws Exception {
						List<Comment> commentlist = Comment
								.create_by_jsonarray(obj.toString());
						data.addAll(commentlist);
						adapter.notifyDataSetChanged();
						if (obj.isNull("response") || commentlist.size() < 5) {
							hasMore = false;
							toast("数据加载完毕!");
						}
					}

					@Override public void onFailure(int statusCode,
							Header[] headers, byte[] data, Throwable arg3) {
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

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	@Override public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (swipeRefreshLayout.isRefreshing() || isloading)
			return;
		if (firstVisibleItem + visibleItemCount >= totalItemCount
				&& totalItemCount != 0 && hasMore) {
			isloading = true;
			onLoadMore();
		}

	}

	@Override public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	class CommentBroadcastReceiver extends BroadcastReceiver {

		@Override public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.Action_Comment_Successful)) {
				onRefresh();
			}

		}

	}

}
