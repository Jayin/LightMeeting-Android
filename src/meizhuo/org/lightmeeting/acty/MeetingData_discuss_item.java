package meizhuo.org.lightmeeting.acty;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.app.BaseActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;

/***
 * 评论列表（讨论中的一项）暂时处理：使用Listview 显示评论列表,不处理ListView跟ScollView的冲突事件
 * @author Jason
 *
 */
public class MeetingData_discuss_item extends BaseActivity {
	
	
	String withComments = "1";
	
	@InjectView(R.id.comment_lv)ListView   comment_lv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState,R.layout.item_meetdata_discuss_item);
		
		initData();
		initLayout();
	}
	

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		String discussid = getIntent().getStringExtra("discussid");
		
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		
	}

}
