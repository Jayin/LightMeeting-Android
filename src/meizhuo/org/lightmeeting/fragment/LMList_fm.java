package meizhuo.org.lightmeeting.fragment;


import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.acty.MeetingData;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class LMList_fm extends BaseFragment implements OnRefreshListener, OnScrollListener {
	private static final String TAG = "LMListFragment";
	
	@InjectView(R.id.lv) ListView lv;
	
	LMListAdapter adapter ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 super.onCreateView(inflater, container, savedInstanceState,R.layout.fm_lmlist);
	 String[] names = {"袂卓第一次会议","袂卓第二次会议","袂卓第二次会议","袂卓第二次会议","袂卓第二次会议"};
	 String[] Meet_Time = {"2014-9-8","2014-9-8","2014-9-8","2014-9-8","2014-9-8"};
	 String[] Meet_Place = {"北主楼16楼","北主楼16楼","北主楼16楼","北主楼16楼","北主楼16楼"};
	 adapter = new LMListAdapter(getActivity(), names, Meet_Time, Meet_Place);
	 lv.setAdapter(adapter);
	 return contentView;
	}
	
	@OnItemClick(R.id.lv) public void item_click(int position){
		openActivity(MeetingData.class);
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
