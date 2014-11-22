package meizhuo.org.lightmeeting.acty;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_research_item_option_adapter;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.model.KV;

/**
 *  选项
 * @author Jason
 *
 */
public class MeetingData_research_item_option extends BaseActivity{
	
	
	String research_title;
	ActionBar mActionBar;
	JSONObject option;
	
	
	MeetingData_research_item_option_adapter adapter;
	
	@InjectView(R.id.research_option_lv) ListView research_option_lv;
	@InjectView(R.id.research_option_value) TextView research_option_value;
	@InjectView(R.id.research_option_key) TextView research_option_key;
	List<HashMap<String, String>>optionlist;
	HashMap<String, String>optionmap;
	List<KV>data;
	KV kv;
//	String option_key,option_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_research_item_option);
		
		initData();
		initLayout();
	}


	/**
	 it.putExtra("research_title", data.get(position).getTitle());
		it.putExtra("research_option", data.get(position).getOptions());
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		research_title=getIntent().getStringExtra("research_title");
		optionlist = new ArrayList<HashMap<String,String>>();
		data= (ArrayList<KV>) getIntent().getSerializableExtra("researchobj");
	       adapter  =new MeetingData_research_item_option_adapter(this, data);
		
	}
	
	/**确定选项*/
	@OnClick(R.id.research_confirm) public void research_confirm(){
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
		research_option_lv.setAdapter(adapter);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(research_title);
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
	

}
