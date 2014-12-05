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
import android.content.DialogInterface;
import android.content.Intent;
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
import meizhuo.org.lightmeeting.fragment.MeetdataFm;
import meizhuo.org.lightmeeting.fragment.MeetfunctionFm;
import meizhuo.org.lightmeeting.fragment.MemberFm;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.utils.Constants;
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
		super.onCreate(savedInstanceState, R.layout.lm_list_one_meeting);
		initData();
		initLayout();
		
		MemberFm member_fm = new MemberFm();
		Bundle bundle = new Bundle();
		bundle.putString("meetid", meetid);
		member_fm.setArguments(bundle);
		
		MeetfunctionFm meet_function = new MeetfunctionFm();
		meet_function.setArguments(bundle);
		
		MeetdataFm meet_data =  new MeetdataFm();
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
			this.fragments = fragments;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
		

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}
		
	}
	


	@Override
	protected void initData() {
		 meetid = getIntent().getStringExtra("meetid");
		 meet_title = getIntent().getStringExtra("title");
	}


	@Override
	protected void initLayout() {
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(meet_title);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.meetdata, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
				finish();
			break;
		case R.id.create_code:
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogView = inflater.inflate(R.layout.qr_code_dialog, null);
			final ImageView qr_code = (ImageView)dialogView.findViewById(R.id.iv_qr_image);
			try {
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(RestClient.BASE_URL+"/home/meet/addjoinClient/meetid/" + meetid, 350);
				qr_code.setImageBitmap(qrCodeBitmap);
			} catch (WriterException e) {
				e.printStackTrace();
			}
			AlertDialog.Builder builder =  new AlertDialog.Builder(this);
			builder.setTitle("          会议二维码");
			builder.setView(dialogView);
			AlertDialog dialog = builder.create();
			dialog.show();
			
			break;
		case R.id.quite_meet:
			AlertDialog.Builder logoutBuilder =  new AlertDialog.Builder(this);
			logoutBuilder.setTitle("      确定退出会议 ?");
			logoutBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MeetingAPI.quiteMeet(meetid, new JsonHandler(){
						
						@Override
						public void onStart() {
							if(loadingDialog == null){
								loadingDialog = new LoadingDialog(MeetingData.this);
							}
							loadingDialog.setText("正在退出会议...");
							loadingDialog.show();
						}
						
						@Override
						public void onOK(int statusCode, Header[] headers,
								JSONObject obj) throws Exception {
							if(loadingDialog.isShowing()){
								loadingDialog.dismiss();
								loadingDialog = null;
								toast("成功退出会议!");
							}
							sendBroadcast(new Intent(Constants.Action_Logout_Meeting_Successful));
							MeetingData.this.finish();
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] data, Throwable arg3) {
							if(loadingDialog.isShowing()){
								loadingDialog.dismiss();
								loadingDialog = null;
							}
							toast("退出会议失败，请检查你的网络设置!");
						}
						
					});
				}
			});
			logoutBuilder.setNegativeButton("暂不退出会议", null);
			AlertDialog logoutDialog = logoutBuilder.create();
			logoutDialog.show();
			break;
			

		default:
			break;
		}
		return true;
	}
}
