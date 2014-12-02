package meizhuo.org.lightmeeting.acty;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.MeetResearchOptionAdapter;
import meizhuo.org.lightmeeting.adapter.MeetResearchOptionAdapter.OnPositionClickListener;
import meizhuo.org.lightmeeting.adapter.MeetResearchOptionAdapter.ViewHolder;
import meizhuo.org.lightmeeting.api.ResearchAPI;
import meizhuo.org.lightmeeting.app.BaseActivity;
import meizhuo.org.lightmeeting.imple.JsonHandler;
import meizhuo.org.lightmeeting.model.KV;
import meizhuo.org.lightmeeting.utils.L;
import meizhuo.org.lightmeeting.widget.LoadingDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 调查 选项
 * @author Jason
 *
 */
public class MeetResearchListOption extends BaseActivity{
	
	
	String research_title;
	ActionBar mActionBar;
	JSONObject option;
	String select_option;
	String questionid;
	String optionid; 
	String option_content;
	String option_type;
	MeetResearchOptionAdapter adapter;
	ViewHolder holder;
	String answer;
	
	@InjectView(R.id.research_option_lv) ListView research_option_lv;
//	@InjectView(R.id.research_option_value) TextView research_option_value;
//	@InjectView(R.id.research_option_key) TextView research_option_key;
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initData() {
		research_title=getIntent().getStringExtra("research_title");
		questionid = getIntent().getStringExtra("questionid");
		optionlist = new ArrayList<HashMap<String,String>>();
		data= (ArrayList<KV>) getIntent().getSerializableExtra("researchobj");
		option_type = getIntent().getStringExtra("option_type");
		Collections.sort(data);
	       adapter  =new MeetResearchOptionAdapter(this, data);
		
	}
	
	/**确定选项*/
	@OnClick(R.id.research_confirm) public void research_confirm(){
		L.i("提交的答案" + optionid + "内容" + option_content);
		if(option_type.equals("1")){
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
					MeetResearchListOption.this.finish();
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
					byte[] data, Throwable arg3) {
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
		if(option_type.equals("2")){
			String multi_option = "";
			JSONObject obj = new JSONObject();
			String objset="";
			for(int i=0;i<data.size();i++){
				if(data.get(i).isIsclick()){
					try {
						obj.put("option_content", data.get(i).getValue());
						obj.put("optionid", data.get(i).getKey());
					} catch (JSONException e) {
						e.printStackTrace();
					}
					objset += obj.toString() +",";
				}
			}
			objset = objset.substring(0, objset.length()-1);
			multi_option = "[" +objset +  "]";
			L.i("多选提交" + multi_option.toString());
			ResearchAPI.answerMulti(questionid, multi_option, new JsonHandler(){
				@Override
				public void onStart() {
					if(dialog == null)
					{
						dialog = new LoadingDialog(getContext());
						dialog.setText("正在提交答案...");
					}
					dialog.show();
				}
				
				@Override
				public void onOK(int statusCode, Header[] headers,
						JSONObject obj) throws Exception {
					if(dialog.isShowing())
					{
						dialog.dismiss();
						dialog = null;
					}
					toast("提交答案成功!");
					MeetResearchListOption.this.finish();
				}
				 
				@Override
				public void onError(int error_code, Header[] headers,
						JSONObject obj) throws Exception {
					super.onError(error_code, headers, obj);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] data, Throwable arg3) {
					if(dialog.isShowing())
					{
						dialog.dismiss();
						dialog = null;
					}
					toast("网络不给力，请检查你的网络设置！");
					return ;
				}
				
				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					super.onFinish();
				}
				
			});
		}
	}
	
	

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
		research_option_lv.setAdapter(adapter);
		adapter.setOnItemClickListener(new OnPositionClickListener() {
			@Override
			public void onItemClick(int position) {
				Intent it =  new Intent(MeetResearchListOption.this,MeetResearchAnswer.class);
				startActivityForResult(it, 320);
			}
		});
		research_option_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				 holder=(ViewHolder)view.getTag();
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
						optionid=data.get(position).getKey();
						option_content=data.get(position).getValue();
					}
				}
				if(option_type.equals("2")){
				
				if(data.get(position).isIsclick() == true){
					holder.option_iv.setVisibility(View.GONE);
					data.get(position).setIsclick(false);
				}else{
					if(data.get(position).getValue().equals("")){
						holder.option_iv.setVisibility(View.GONE);
					}else{
						holder.option_iv.setVisibility(View.VISIBLE);
						data.get(position).setIsclick(true);
					}
				}
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
		if(requestCode == 320 && resultCode == 321){
			answer = data1.getStringExtra("answer");
			for(int i=0;i<data.size();i++){
				if(data.get(i).getValue().equals("")){
					data.get(i).setKey(data.get(i).getKey());
					data.get(i).setValue(answer);
					data.get(i).setIsclick(true);
				}
			}
			adapter.notifyDataSetChanged();
		}
	}
	
}
