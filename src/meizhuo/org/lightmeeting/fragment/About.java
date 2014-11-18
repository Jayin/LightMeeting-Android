package meizhuo.org.lightmeeting.fragment;

import butterknife.InjectView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.utils.AndroidUtils;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class About extends BaseFragment {
	
	@InjectView(R.id.tv_versionCode) TextView tv_versionCode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState,R.layout.fragment_about);
		String versionName;
		try {
			versionName = AndroidUtils.getAppVersionName(getActivity());
			tv_versionCode.setText("版本" + versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentView;
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
