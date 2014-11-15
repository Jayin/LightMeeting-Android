package meizhuo.org.lightmeeting.adapter;

import meizhuo.org.lightmeeting.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {
	String[] menuName = null;
	private Context context;

	public DrawerAdapter(Context context, String[] menuName) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.menuName = menuName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuName.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return menuName[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder h = null;
		if(convertView == null){
			h = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_leftdrawer, null);
			h.menuName = (TextView)convertView.findViewById(R.id.tv_menuName);
			convertView.setTag(h);
		}else{
			h = (ViewHolder)convertView.getTag();
		}
		h.menuName.setText(menuName[position]);
		return convertView;
	}
	
	private class ViewHolder{
		TextView menuName;
	}

}
