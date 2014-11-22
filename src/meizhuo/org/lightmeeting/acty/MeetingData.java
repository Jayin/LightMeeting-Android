package meizhuo.org.lightmeeting.acty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.WriterException;

import butterknife.InjectView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.api.RestClient;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.encoding.EncodingHandler;
import meizhuo.org.lightmeeting.fragment.MeetingData_fm;
import meizhuo.org.lightmeeting.fragment.Meeting_function_fm;
import meizhuo.org.lightmeeting.fragment.Member_fm;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

/***
 * 一个会议相关的所有东西，包含三个fragment
 * @author Jason
 *
 */
public class MeetingData extends BaseActivity{

	@InjectView(R.id.tabs) com.astuetz.PagerSlidingTabStrip mPagerSlidingTabStrip;
	@InjectView(R.id.viewpager) ViewPager mViewPager;
	ActionBar mActionBar;
	String meetid,meet_title;
	LoadingDialog loadingDialog ;

	List<Fragment>fragments = new ArrayList<Fragment>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState, R.layout.lm_list_one_meeting);
		initData();
		initLayout();
		
		Member_fm member_fm = new Member_fm();
		Bundle bundle = new Bundle();
		bundle.putString("meetid", meetid);
		member_fm.setArguments(bundle);
		
		Meeting_function_fm meet_function = new Meeting_function_fm();
		meet_function.setArguments(bundle);
		
		MeetingData_fm meet_data =  new MeetingData_fm();
		meet_data.setArguments(bundle);
		
		
		fragments.add(meet_data);
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
		 meetid = getIntent().getStringExtra("meetid");
		 meet_title = getIntent().getStringExtra("title");
	}


	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(meet_title);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.meetdata, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
				finish();
			break;
		case R.id.create_code:
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogView = inflater.inflate(R.layout.qr_code_dialog, null);
			final ImageView qr_code = (ImageView)dialogView.findViewById(R.id.iv_qr_image);
			try {
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(RestClient.BASE_URL+"/home/meet/addjoin/meetid/id/" + meetid, 350);
				qr_code.setImageBitmap(qrCodeBitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AlertDialog.Builder builder =  new AlertDialog.Builder(this);
			builder.setTitle("          会议二维码");
			builder.setView(dialogView);
			AlertDialog dialog = builder.create();
			dialog.show();
			
			break;
		case R.id.quite_meet:
			MeetingAPI.quiteMeet(meetid, new JsonResponseHandler() {
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					if(loadingDialog == null){
						loadingDialog = new LoadingDialog(MeetingData.this);
					}
					loadingDialog.setText("正在退出会议...");
					loadingDialog.show();
				}
				
				@Override
				public void onOK(Header[] headers, JSONObject obj) {
					// TODO Auto-generated method stub
					try {
						if(obj.getString("code").equals("20000")){
							if(loadingDialog.isShowing()){
								loadingDialog.dismiss();
								loadingDialog = null;
								toast("成功退出会议!");
							}
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onFaild(int errorType, int errorCode) {
					// TODO Auto-generated method stub
					if(loadingDialog.isShowing()){
						loadingDialog.dismiss();
						loadingDialog = null;
					}
					toast("网络不给力，请检查你的网络设置!");
				}
			});
			break;
			

		default:
			break;
		}
		return true;
	}
}
