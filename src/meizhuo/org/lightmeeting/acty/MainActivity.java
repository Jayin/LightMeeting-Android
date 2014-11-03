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
import meizhuo.org.lightmeeting.fragment.BusinessCardFragment;
import meizhuo.org.lightmeeting.fragment.DynamicFragment;
import meizhuo.org.lightmeeting.fragment.LMListFragment;

public class MainActivity extends BaseActivity {
//	private static final String TAG = "MainActivity";
	
	@InjectView(R.id.tabs) com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;
	@InjectView(R.id.viewpager) ViewPager mViewPager;

	List<Fragment>fragments = new ArrayList<Fragment>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.activity_main);
		setAppTitle("LightMeeting");
		
//		ViewPager mViewPager = (ViewPager)findViewById(R.id.viewpager);
//		PagerSlidingTabStrip mPagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
		
		fragments.add(new BusinessCardFragment());
		fragments.add(new LMListFragment());
		fragments.add(new DynamicFragment());
		
		
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		
	}
	
	public class MyPagerAdapter extends FragmentPagerAdapter {
		List<Fragment>fragments;
		private final String[] TITLES = { "会议列表","动态","名片"};

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


}
