package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.fragment.MeetingData_fm;
import meizhuo.org.lightmeeting.fragment.Meeting_function_fm;
import meizhuo.org.lightmeeting.fragment.Member_fm;
import meizhuo.org.lightmeeting.utils.L;

/***
 * 一个会议相关的所有东西，包含三个fragment
 * @author Jason
 *
 */
public class MeetingData extends BaseActivity{

	@InjectView(R.id.tabs) com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;
	@InjectView(R.id.viewpager) ViewPager mViewPager;

	List<Fragment>fragments = new ArrayList<Fragment>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.lm_list_one_meeting);
		
		String meetid = getIntent().getStringExtra("meetid");
		
		Member_fm member_fm = new Member_fm();
		Bundle bundle = new Bundle();
		bundle.putString("meetid", meetid);
		member_fm.setArguments(bundle);
		
		/*bundle传给meetfunction*/
		Meeting_function_fm meet_function = new Meeting_function_fm();
		meet_function.setArguments(bundle);
		
		
		fragments.add(new MeetingData_fm());
		fragments.add(meet_function);
		fragments.add(member_fm);
		
		
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
		mPagerSlidingTabStrip.setViewPager(mViewPager);
	}
	
	
	public class MyPagerAdapter extends FragmentPagerAdapter {
		List<Fragment>fragments;
		private final String[] TITLES = { "会议资料","功能","成员"};

		public MyPagerAdapter(FragmentManager fm, List<Fragment>fragments) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.fragments = fragments;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return TITLES[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return TITLES.length;
		}
		

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragments.get(position);
		}
		
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
