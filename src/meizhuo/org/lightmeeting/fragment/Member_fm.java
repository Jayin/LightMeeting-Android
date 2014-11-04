package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import meizhuo.org.lightmeeting.adapter.MemberAdapter;
import butterknife.InjectView;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class Member_fm extends BaseFragment implements OnRefreshListener, OnScrollListener {

	@InjectView(R.id.lv) ListView lv;
	
	MemberAdapter adapter; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_lmlist);
	 String[] username ={"代码家","代码家","代码家","代码家","代码家","代码家","代码家","代码家","代码家","代码家","代码家","代码家",};  
	String[]  intro= {"我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID","我爱我的ID",};
	adapter = new MemberAdapter(getActivity(), username, intro);
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
