package meizhuo.org.lightmeeting.acty;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetingData_research_item_option_adapter;
import meizhuo.org.lightmeeting.adapter.MeetingData_research_item_option_adapter.ViewHolder;
import meizhuo.org.lightmeeting.api.ResearchAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.KV;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 调查 选项
 * @author Jason
 *
 */
public class MeetingData_research_item_option extends BaseActivity{
	
	
	String research_title;
	ActionBar mActionBar;
	JSONObject option;
	String select_option;
	String questionid;
	String optionid; 
	String option_content;
	String option_type;
	MeetingData_research_item_option_adapter adapter;
	
	@InjectView(R.id.research_option_lv) ListView research_option_lv;
	@InjectView(R.id.research_option_value) TextView research_option_value;
	@InjectView(R.id.research_option_key) TextView research_option_key;
	List<HashMap<String, String>>optionlist;
	HashMap<String, String>optionmap;
	List<KV>data;
	KV kv;
	LoadingDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.item_meetdata_research_item_option);
		
		initData();
		initLayout();
	}
	
	
	
/*	
	@OnItemClick(R.id.research_option_lv) public void select_option(int position){
		research_option_value.setText(data.get(position).getKey() + ":" + data.get(position).getValue());
		select_option = data.get(position).getKey() + ":" + data.get(position).getValue();
		
		optionid = data.get(position).getKey();
		option_content = data.get(position).getValue();
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initData() {
		research_title=getIntent().getStringExtra("research_title");
		questionid = getIntent().getStringExtra("questionid");
		optionlist = new ArrayList<HashMap<String,String>>();
		data= (ArrayList<KV>) getIntent().getSerializableExtra("researchobj");
		option_type = getIntent().getStringExtra("option_type");
		Collections.sort(data);
	       adapter  =new MeetingData_research_item_option_adapter(this, data);
		
	}
	
	/**确定选项*/
	@OnClick(R.id.research_confirm) public void research_confirm(){
		ResearchAPI.answer(questionid, optionid, option_content, new JsonHandler(){
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					if(dialog == null)
					{
						dialog = new LoadingDialog(getContext());
						dialog.setText("正在提交答案...");
					}
					dialog.show();
				}
				@Override
				public void onOK(int statusCode, Header[] headers, JSONObject obj)
					throws Exception {
					if(dialog.isShowing())
					{
						dialog.dismiss();
						dialog = null;
					}
					toast("提交答案成功!");
					MeetingData_research_item_option.this.finish();
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
				// TODO Auto-generated method stub
					if(dialog.isShowing())
					{
						dialog.dismiss();
						dialog = null;
					}
					toast("网络不给力，请检查你的网络设置！");
					return ;
					
				}
				
		});
	}
	
	

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
		research_option_lv.setAdapter(adapter);
		research_option_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ViewHolder holder=(ViewHolder)view.getTag();
				if(option_type.equals("1")){
					if(data.get(position).isIsclick() == true)
					{
						holder.option_iv.setVisibility(View.GONE);
						data.get(position).setIsclick(false);
					}else{
						for(int i=0;i<data.size();i++)
						{
							data.get(i).setIsclick(false);
						}
						adapter.notifyDataSetChanged();
						holder.option_iv.setVisibility(View.VISIBLE);
						data.get(position).setIsclick(true);
					}
				}
				if(data.get(position).isIsclick() == true)
				{
					holder.option_iv.setVisibility(View.GONE);
					data.get(position).setIsclick(false);
				}else{
					holder.option_iv.setVisibility(View.VISIBLE);
					data.get(position).setIsclick(true);
				}
				
			}
		});
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(research_title);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			adapter.notifyDataSetChanged();
			finish();
			break;

		default:
			break;
		}
		return true;
	}
	
}
