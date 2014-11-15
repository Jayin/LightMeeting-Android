package meizhuo.org.lightmeeting.acty;



import butterknife.InjectView;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.fragment.DrawerMain;
import meizhuo.org.lightmeeting.fragment.LMList_fm;

public class MainActivity extends BaseActivity { 
//	private static final String TAG = "MainActivity";
	
	
	
	private String DefaultTitle = "LightMeeting";
	private String MenuTitle = "Menu";
	//获取Fragment的管理员权限
	private FragmentManager manager = getSupportFragmentManager();
	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	private Menu mMenu;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.activity_main);

//		setAppTitle("LightMeeting");
		initLayout();
		
	}
	


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		mDrawerLayout.setDrawerListener(new MyDrawerListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.lm_ic_drawer_white, 
				R.string.drawer_open, R.string.drawer_close);
		//init the actionbar to Display
		mActionBar = getActionBar();
		//决定左上角是否可以点击
		mActionBar.setDisplayHomeAsUpEnabled(true);
		setActionBarTitle(DefaultTitle);
		manager.beginTransaction().add(R.id.container, new LMList_fm(),"LMList_fm").commit();
		manager.beginTransaction().add(R.id.left_container, new DrawerMain(),"DrawerMain").commit();
	}
	
	public void setMainContent(Fragment fragment)
	{
		mDrawerLayout.closeDrawers();
		// you need to pop some stacks in case setMainContent called after
		//add MainContent
		//activity 的后退栈中弹出fragment们(这可以模拟后退键引发的动作)
		getSupportFragmentManager().popBackStack();
		//回退之后切换原来的界面
		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		
	}
	
	private void setActionBarTitle(String title){
		mActionBar.setTitle(title);
	}
	
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			mDrawerLayout.closeDrawers();
		}
	
	@Override
		protected void onPostCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onPostCreate(savedInstanceState);
			mDrawerToggle.syncState();
		}
	
	@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		//handler the select
		return super.onOptionsItemSelected(item);
		}
	
	@Override
		public void onConfigurationChanged(Configuration newConfig) {
			// TODO Auto-generated method stub
			super.onConfigurationChanged(newConfig);
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	
	
	public class MyDrawerListener implements DrawerLayout.DrawerListener{

		@Override
		public void onDrawerClosed(View drawerView) {
			// TODO Auto-generated method stub
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.setTitle(DefaultTitle);
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			// TODO Auto-generated method stub
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.setTitle(MenuTitle);
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			// TODO Auto-generated method stub
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			// TODO Auto-generated method stub
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}
	
	


}
