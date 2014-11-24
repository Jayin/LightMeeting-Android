package meizhuo.org.lightmeeting.acty;






import java.io.File;
import java.util.ArrayList;
import java.util.List;


import butterknife.InjectView;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.app.CoreService;
import meizhuo.org.lightmeeting.fragment.DrawerMain;
import meizhuo.org.lightmeeting.fragment.LMList_fm;
import meizhuo.org.lightmeeting.model.KV;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import meizhuo.org.lightmeeting.utils.Constants;

public class MainActivity extends BaseActivity { 
	
	
	private BroadcastReceiver mReceiver = null;
	private String DefaultTitle = "轻会议";
	private String MenuTitle = "菜单";
	//获取Fragment的管理员权限
	private FragmentManager manager = getSupportFragmentManager();
	@InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	private DownloadManager downloadManager;
	private SharedPreferences prefs;
	private static final String DL_ID = "downloadId";
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.activity_main);
		initDownload();
		checkVersion();
		initReceiver();
	
		initLayout();
		
	}
	


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
	
	private void initDownload() {
		downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
	
	@Override protected void onDestroy() {
		super.onDestroy();
		if(mReceiver != null){
			unregisterReceiver(mReceiver);
		}
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
	private void initReceiver() {
		mReceiver = new AppStartReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.Action_Receive_VersionInfo);
		filter.addAction(Constants.Action_ChangePSW_Successful);
		registerReceiver(mReceiver, filter);

	}

	class AppStartReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			List<KV>kvlist = new ArrayList<KV>();
			String action = intent.getAction();
			List<KV> updateInfo = (List<KV>) intent.getSerializableExtra("updateInfo");
			String versionname = intent.getStringExtra("version_name");
			final String url = intent.getStringExtra("url");
			if (action.equals(Constants.Action_Receive_VersionInfo)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("发现新版本");
				try {
					
					String update_content = "";
					for(KV kv : updateInfo){
						update_content += kv.getKey() + "." +kv.getValue() + "\n";
					}
					
					builder.setMessage("当前版本:"
							+ AndroidUtils.getAppVersionName(MainActivity.this)
							+ "\n更新版本号:" + versionname + "\n" + update_content);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				builder.setPositiveButton("立刻更新 ",
						new DialogInterface.OnClickListener() {

							@SuppressLint("NewApi")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
//								Intent intent = new Intent(Intent.ACTION_VIEW);
								Uri uri = Uri.parse(url);
//								 intent.setData(uri);
//								 startActivity(intent);
								
									DownloadManager.Request request = new DownloadManager.Request(
											uri);
									request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
											| Request.NETWORK_WIFI);
									request.setAllowedOverRoaming(false);
									// 设置文件类型
									MimeTypeMap mimeTypeMap = MimeTypeMap
											.getSingleton();
									String mimeString = mimeTypeMap
											.getMimeTypeFromExtension(MimeTypeMap
													.getFileExtensionFromUrl(url));
									request.setMimeType(mimeString);
									// 在通知栏中显示
									request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
									request.setVisibleInDownloadsUi(true);
									// sdcard的目录下的download文件夹
									request.setDestinationInExternalPublicDir(
											"/download/", "轻会议.apk");
									request.setTitle("轻会议");
									long id = downloadManager.enqueue(request);
									// 保存id
									prefs.edit().putLong(DL_ID, id).commit();

								registerReceiver(
										receiver,
										new IntentFilter(
												DownloadManager.ACTION_DOWNLOAD_COMPLETE));
							}
						});
				builder.setNegativeButton("稍后更新", null);
				builder.setOnKeyListener(keylistener);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			if(action.equals(Constants.Action_ChangePSW_Successful))
			{
				MainActivity.this.finish();
			}
		}
	}
	
	OnKeyListener keylistener = new DialogInterface.OnKeyListener() {

		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return true;
			} else {
				return false;
			}

		}
	};
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
			queryDownloadStatus();
			if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
				Intent i = new Intent(Intent.ACTION_VIEW);
				String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append(File.separator).append("/download/").append(File.separator)
                .append("meizhuo.apk").toString();
				 File file = new File(apkFilePath);
			        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
			            i.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
			            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			           MainActivity.this.startActivity(i);
			        }
				
			}
		}
	};
	
	private void checkVersion() {
		Intent service = new Intent(getContext(), CoreService.class);
		service.setAction(Constants.Action_checkVersion);
		startService(service);
	}
	
	private void queryDownloadStatus() {
		DownloadManager.Query query = new DownloadManager.Query();
		query.setFilterById(prefs.getLong(DL_ID, 0));
		Cursor c = downloadManager.query(query);
		if (c.moveToFirst()) {
			int status = c.getInt(c
					.getColumnIndex(DownloadManager.COLUMN_STATUS));
			switch (status) {
			case DownloadManager.STATUS_PAUSED:
			case DownloadManager.STATUS_PENDING:
			case DownloadManager.STATUS_RUNNING:
				// 正在下载，不做任何事情
				break;
			case DownloadManager.STATUS_SUCCESSFUL:
				// 完成
			
				break;
			case DownloadManager.STATUS_FAILED:
				// 清除已下载的内容，重新下载
				downloadManager.remove(prefs.getLong(DL_ID, 0));
				prefs.edit().clear().commit();
				break;
			}
		}
	}
	


}
