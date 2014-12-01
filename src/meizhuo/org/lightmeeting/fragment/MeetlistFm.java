package meizhuo.org.lightmeeting.fragment;

import java.util.ArrayList;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.CaptureActivity;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.adapter.MeetListAdapter;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.api.RestClient;
import meizhuo.org.lightmeeting.app.App;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.Meeting;
import meizhuo.org.lightmeeting.utils.Constants;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

import com.loopj.android.http.AsyncHttpClient;

/**
 * 会议列表fragment
 * 
 * @author Jason
 * 
 */
public class MeetlistFm extends BaseFragment implements OnRefreshListener,
		OnScrollListener {

	@InjectView(R.id.lv) ListView lv;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	MeetListAdapter adapter;
	List<Meeting> data;
	String page = "1", limit = "";
	boolean hasMore = true, isloading = false;
	LoadingDialog dialog;
	BroadcastReceiver mBroadcastReceiver;

	@Override public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState,
				R.layout.fm_lmlist);
		openReceiver();
		initData();
		initLayout();
		onRefresh();
		return contentView;
	}

	private void openReceiver() {
		mBroadcastReceiver = new LogoutMeetingReceiver();
		IntentFilter filter = new IntentFilter(
				Constants.Action_Logout_Meeting_Successful);
		getActivity().registerReceiver(mBroadcastReceiver, filter);
	}

	protected void initData() {
		data = new ArrayList<Meeting>();
		adapter = new MeetListAdapter(getActivity(), data);

	}

	@Override protected void initLayout() {
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);

	}

	@Override public void onRefresh() {
		MeetingAPI.getMeetingList(page, limit, new JsonHandler() {
			@Override public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}

			@Override public void onOK(int statusCode, Header[] headers,
					JSONObject obj) throws Exception {
				List<Meeting> meetings = Meeting.create_by_jsonarray(obj
						.toString());
				data.clear();
				data.addAll(meetings);
				adapter.notifyDataSetChanged();
				page = "1";
				if (meetings.size() == 0) {
					toast("没有参加任何会议");
				}
				if (meetings.size() < 5) {
					hasMore = false;
				} else {
					hasMore = true;
				}
				String[] t = new String[meetings.size()];
				for (int i = 0; i < meetings.size(); i++) {
					System.out.println("get meeting--> "
							+ meetings.get(i).getId());
					t[i] = meetings.get(i).getId();
				}
				App app = (App) (getActivity().getApplication());
				app.addTags(t, App.TAG_TYPE_MEET);
			}

			@Override public void onError(int error_code, Header[] headers,
					JSONObject obj) throws Exception {
				toast("出错了，请检查你的网络设置!");
				return;
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

		MeetingAPI.getMeetingList(page, limit, new JsonHandler() {
			@Override public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}

			@Override public void onOK(int statusCode, Header[] headers,
					JSONObject obj) throws Exception {
				List<Meeting> meetings = Meeting.create_by_jsonarray(obj
						.toString());
				data.addAll(meetings);
				adapter.notifyDataSetChanged();
				if (obj.isNull("response") || meetings.size() < 5) {
					hasMore = false;
					toast("数据加载完毕!");
				}

				for (Meeting m : meetings) {
					App app = (App) (getActivity().getApplication());
					app.addTag(m.getId(), App.TAG_TYPE_MEET);
				}
			}

			@Override public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				toast("网络不给力,请检查你的网络设置!");
				return;
			}

			@Override public void onFinish() {
				swipeRefreshLayout.setRefreshing(false);
				isloading = false;
			}
		});

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
		// TODO Auto-generated method stub

	}

	@OnItemClick(R.id.lv) public void item_click(int position) {
		Intent it = new Intent(getActivity(), MeetingData.class);
		it.putExtra("meetid", data.get(position).getId());
		it.putExtra("title", data.get(position).getTitle());
		startActivity(it);
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_sweep:
			Intent openCameraIntent = new Intent(getActivity(),
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 50);
			break;
		}
		return true;
	}

	@Override public void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode == 50 && resultCode == 51) {
			String qrurl = data.getStringExtra("resultcode");
			L.i("resultcode" + qrurl);
			AsyncHttpClient client;
			client = RestClient.getClient();
			client.get(qrurl, new JsonHandler() {
				@Override public void onStart() {
					if (dialog == null) {
						dialog = new LoadingDialog(getActivity());
					}
					dialog.setText("正在加入会议..");
					dialog.show();
				}

				@Override public void onOK(int statusCode, Header[] headers,
						JSONObject obj) throws Exception {
					if (dialog.isShowing()) {
						dialog.dismiss();
						dialog = null;
					}
					String response = obj.getString("response");
					toast(response);
					onRefresh();
				}

				@Override public void onError(int error_code, Header[] headers,
						JSONObject obj) throws Exception {
					if (dialog.isShowing()) {
						dialog.dismiss();
						dialog = null;
					}
					String msg = obj.getString("msg");
					toast(msg);
				}

				@Override public void onFailure(int statusCode,
						Header[] headers, byte[] data, Throwable arg3) {
					toast("网络不给力,请检查你的网络设置!");
					return;
				}

			});

		}

	}

	@Override public void onDestroy() {
		super.onDestroy();
		if (mBroadcastReceiver != null)
			getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	class LogoutMeetingReceiver extends BroadcastReceiver {

		@Override public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constants.Action_Logout_Meeting_Successful)) {
				onRefresh();
			}
		}

	}

}
