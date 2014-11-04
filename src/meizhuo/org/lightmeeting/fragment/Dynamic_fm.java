package meizhuo.org.lightmeeting.fragment;

import butterknife.InjectView;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.adapter.DynamicAdapter;
import meizhuo.org.lightmeeting.adapter.LMListAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class Dynamic_fm extends BaseFragment implements OnRefreshListener,
		OnScrollListener {

	@InjectView(R.id.dynamic_lv) ListView dynamic_lv;
	DynamicAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState,
				R.layout.fm_dynamic);
		// String[] names = new String[]
		// {"动态 ","动态  ","动态  ","动态 ","动态 ","动态 ","动态 ","动态 ","动态 ","动态  ","动态  "};
		// lv.setAdapter(adapter);
		String[] namelist = new String[] { "黄振炼", "唐叫兽", "杨老师", "简全蛋","Jason","景恩" };
		String[] contentlist = new String[] { "关于Linux的趣事 | 2014年8月25日，是Linux的第23个生日。1991年8月25日，一位21岁的赫尔辛基大学的学生发布了举世闻名的新闻组(Usenet post)，标志着现在世界著名的Linux正式诞生。 23年以后的现在，Linux已经无处不在，不仅仅…"
				,"传 Jawbone 将推 Up MOVE 与 Up3 两款新设备】传 Jawbone 近期将推 Up MOVE 与 Up3 两款健康追踪设备，目标瞄准中低端用户，售价分别为 50 和 180 美元。"
				,"“火柴盒”，文艺碎片的社交转身，已获200万人民币天使投资 | 信息过载的时代，人们渴望回归轻量化阅读，这也引来了嗅觉敏锐的创业者们开发出一个个垂直细分的阅读产品。火柴盒就是这样一个文艺范儿 App。每天一首歌，一句话，一篇文章，三种内容样态下都有点赞和分享的操作按钮。"
				,"#简书征文#双十一即将到来，这是购物的狂欢，是网络的盛典，是用 一场欲望的革命迎接一年的末尾到来，用一场购物欲的释放让我们释怀。我们发起征文《解放你的购物欲》凡是你的购物故事，我们全部打包，让你尽情的解密你想说却没说的购物秘辛"
				,"一般来讲，病毒有特定的宿主，植物病毒很少能在动物细胞中存活，细菌病毒也无法生活在动物和植物细胞中，但也有意外的情况。@科学网 博主孙学军介绍了最近的一个发现，一种常见绿藻病毒竟可以感染人和动物，并能导致人类大脑功能下降，不过这一研究需要继续深化。"
				,"也就是年薪78.9W人民币//@教主: 一赵是年薪一百万港币的意思//@Laruence: 赵百万你是说@老赵 么? 这意思是, 一赵一百万了? //@只会放大的宙斯: 按赵百万的话说，这就是命……//@吕宏伟: 这才符合逻辑。 什么一年五百万啊，部门副总裁啊，我怎么听都不对劲。@老沉 这是忙活自家生意去了"
		};
		adapter = new DynamicAdapter(getActivity(), namelist, contentlist);
		dynamic_lv.setAdapter(adapter);
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

}
