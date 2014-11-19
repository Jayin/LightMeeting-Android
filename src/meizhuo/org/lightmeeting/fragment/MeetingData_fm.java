package meizhuo.org.lightmeeting.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.WriterException;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.MeetingAPI;
import meizhuo.org.lightmeeting.encoding.EncodingHandler;
import meizhuo.org.lightmeeting.imple.JsonResponseHandler;
import meizhuo.org.lightmeeting.widget.LoadingDialog;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

public class MeetingData_fm extends BaseFragment  implements OnRefreshListener, OnScrollListener{

	String meetid;
	LoadingDialog loadingDialog ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
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
		Bundle idbundle = getArguments();
		meetid = idbundle.getString("meetid");
		
		
	}


	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.meetdata, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.create_code:
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View dialogView = inflater.inflate(R.layout.qr_code_dialog, null);
			final ImageView qr_code = (ImageView)dialogView.findViewById(R.id.iv_qr_image);
			try {
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(meetid, 350);
				qr_code.setImageBitmap(qrCodeBitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
			builder.setTitle("生成的二维码");
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
						loadingDialog = new LoadingDialog(getActivity());
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
