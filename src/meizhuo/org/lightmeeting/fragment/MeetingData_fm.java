package meizhuo.org.lightmeeting.fragment;



import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class MeetingData_fm extends BaseFragment  implements OnRefreshListener, OnScrollListener{

	String meetid;
	LoadingDialog loadingDialog ;
	

	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_meetingdata);
	 initData();
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


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
		
	}


	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}
	

	
	

}
