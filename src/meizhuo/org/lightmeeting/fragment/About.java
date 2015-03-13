package meizhuo.org.lightmeeting.fragment;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;

public class About extends BaseFragment {

	@InjectView(R.id.tv_versionCode) TextView tv_versionCode;
	@InjectView(R.id.tv_homepage) TextView tv_homepage;

	@Override public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState,
				R.layout.fragment_about);
		String versionName;
		try {
			versionName = AndroidUtils.getAppVersionName(getActivity());
			tv_versionCode.setText("版本 : " + versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return contentView;
	}

	@OnClick(R.id.tv_homepage)
	public void homepage(){
		Intent intent = new Intent();        
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse(tv_homepage.getText().toString());   
        intent.setData(content_url);  
        startActivity(intent);
	}

	@Override protected void initData() {

	}

	@Override protected void initLayout() {

	}

}
