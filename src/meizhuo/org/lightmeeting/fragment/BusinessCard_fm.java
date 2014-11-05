package meizhuo.org.lightmeeting.fragment;


import butterknife.InjectView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.BusinessCardAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class BusinessCard_fm extends BaseFragment implements  OnRefreshListener, OnScrollListener {
	
	
	@InjectView(R.id.lv_businesscard) ListView lv;
	BusinessCardAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_buinesscard);
	 String[] left_content = new String[] {"名字：","职业：","地址：","电话：","传真：","QQ："};
	 String[] right_content = new String[] {"黄振炼","web组长","北主楼16楼","123564","0525-544","94674637"};
	 adapter = new BusinessCardAdapter(getActivity(), left_content, right_content);
	 
	 lv.setAdapter(adapter);
	 return contentView;
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}




}
