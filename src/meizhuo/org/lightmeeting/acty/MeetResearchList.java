package meizhuo.org.lightmeeting.acty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetResearchProblemAdapter;
import meizhuo.org.lightmeeting.api.ResearchAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.KV;
import meizhuo.org.lightmeeting.model.Problem;
import meizhuo.org.lightmeeting.utils.L;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * 问题列表
 * @author Jason
 *
 */
public class MeetResearchList extends BaseActivity implements OnRefreshListener,OnScrollListener{

	String researchid;
	MeetResearchProblemAdapter adapter;
	ActionBar mActionBar;
	@InjectView(R.id.problem_lv) ListView problem_lv;
	@InjectView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
	List<Problem>data;
	String page="1",limit;
	List<HashMap<String, String>>optionlist = new ArrayList<HashMap<String,String>>();
	boolean hasMore = true,isloading=false;
	JSONObject optionobj;
	Problem problem;
	List<Problem>problemlist;
	JSONObject optionsobj;
	JSONObject realoption;
	List<KV>kvlist;
	String research_title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.item_meetdata_research_item);
		
		initData();
		initLayout();
	}
	
	
	
	
	@Override
	protected void initData() {
		researchid = getIntent().getStringExtra("research_id");
		research_title = getIntent().getStringExtra("research_title");
		data = new ArrayList<Problem>();
		adapter  =  new MeetResearchProblemAdapter(this, data);
		kvlist = new ArrayList<KV>();
	}

	@Override
	protected void initLayout() {
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, 
				android.R.color.holo_blue_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_blue_light);
		problem_lv.setAdapter(adapter);
		problem_lv.setOnScrollListener(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(research_title);
		onRefresh();
	}
	
	@OnItemClick(R.id.problem_lv) public void to_option(int position){
		Intent it = new Intent(this, MeetResearchListOption.class);
		it.putExtra("research_title", data.get(position).getTitle());
		it.putExtra("questionid", data.get(position).getId());
		it.putExtra("option_type", data.get(position).getOption_type());
		try {
			JSONArray array = optionsobj.getJSONArray("response");
			optionobj = array.getJSONObject(position);
			 realoption = optionobj.getJSONObject("options");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		kvlist.clear();
		for(Iterator<String> keylter = realoption.keys();keylter.hasNext();){
			try {
				String key  = keylter.next();
				L.i("key" + key );
				KV kv = new KV();
				kv.setKey(key);
				kv.setValue(realoption.getString(key));
				kvlist.add(kv);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		it.putExtra("researchobj", (Serializable)kvlist);
		L.i("kvlist" + kvlist.toString());
		startActivity(it);
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




	@Override
	public void onRefresh() {
		ResearchAPI.getQuestionList(researchid, page, limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				optionsobj = obj;
				problemlist = Problem.create_by_jsonarray(obj.toString());
				data.clear();
				data.addAll(problemlist);
				adapter.notifyDataSetChanged();
				page = "1";
				if(problemlist.size() <10)
				{
					hasMore = false;
				}else{
					hasMore = true;
				}
				if(problemlist.size()==0)
				{
					toast("该调查暂无问题");
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				swipeRefreshLayout.setRefreshing(false);
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
		ResearchAPI.getQuestionList(researchid,page,limit,new JsonHandler(){
			@Override
			public void onStart() {
				swipeRefreshLayout.setRefreshing(true);
			}
			@Override
			public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
				List<Problem> problemlists =Problem.create_by_jsonarray(obj.toString());
				data.addAll(problemlists);
				adapter.notifyDataSetChanged();
				if(obj.isNull("response")||problemlists.size()<10)
				{
					hasMore = false;
					toast("数据加载完毕!");
				}
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				swipeRefreshLayout.setRefreshing(false);
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
		if(swipeRefreshLayout.isRefreshing() || isloading)
			return ;
		if(firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount!=0 && hasMore){
			isloading = true;
			onLoadMore();
		}
		
	}
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}

}
