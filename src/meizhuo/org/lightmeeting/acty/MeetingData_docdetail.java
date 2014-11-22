package meizhuo.org.lightmeeting.acty;





import butterknife.InjectView;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.RestClient;
import meizhuo.org.lightmeeting.app.BaseActivity;

@SuppressLint("SetJavaScriptEnabled")
public class MeetingData_docdetail extends BaseActivity{
	
	@InjectView(R.id.webview) WebView webview;
	@InjectView(R.id.pb)ProgressBar pb;
	String docid,title;
	ActionBar mActionBar;
	String url;

	@Override protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState, R.layout.meetdata_docdetail);
	
		initData();
		initLayout();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		docid = getIntent().getStringExtra("docid");
		title = getIntent().getStringExtra("title");
		String baseurl = RestClient.BASE_URL;
		url = baseurl + "/home/document/listdoc/" + docid;
		
	}

	@Override
	protected void initLayout() {
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		webview.getSettings().setJavaScriptEnabled(true);
		
		webview.loadUrl(url);
		webview.setWebViewClient(new MyWebViewClient());
		webview.setWebChromeClient(new MyWebChromeViewClient());
		//添加js交互接口类
		webview.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	
	
	private void addImageClickListner() {
		webview.loadUrl("javascript:(function(){" +
		"var objs = document.getElementsByTagName(\"img\"); " + 
				"for(var i=0;i<objs.length;i++)  " + 
		"{"
				+ "    objs[i].onclick=function()  " + 
		"    {  " 
				+ "        window.imagelistner.openImage(this.src);  " + 
		"    }  " + 
		"}" + 
		"})()");
	}
	
	
	/**
	 * js通信接口类
	 *
	 */
	public class JavascriptInterface {
		private Context context;
		
		public JavascriptInterface(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}
		
		@android.webkit.JavascriptInterface
		public void openImage(String img) {
			
			Intent intent = new Intent();
			intent.putExtra("image", img);
			intent.setClass(context, ShowWebImageActivity.class);
			context.startActivity(intent);
		}
	}
	
	
	
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			//html加载完成之后,添加监听图片的点击js函数
			addImageClickListner();
		}
		
		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}
		
		
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}
	
	private class MyWebChromeViewClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if(newProgress != 0){
				pb.setVisibility(View.VISIBLE);
				pb.setProgress(newProgress);
			}
			
			if(newProgress == 100){
				pb.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

}
