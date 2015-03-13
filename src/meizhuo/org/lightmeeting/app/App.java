package meizhuo.org.lightmeeting.app;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import meizhuo.org.lightmeeting.R;
import meizhuo.org.lightmeeting.api.RestClient;
import meizhuo.org.lightmeeting.utils.DataPool;
import android.app.Application;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class App extends Application {
	// 该用户的JPUSH标签
	private Set<String> tags = Collections.synchronizedSet(new LinkedHashSet<String>());
	public static final int TAG_TYPE_MEET = 1;//会议组
	public static final int  TAG_TYPE_PERSON = 2; //用户

	@Override public void onCreate() {
		ButterKnife.setDebug(true);
		// init RestClient
		RestClient.init(getApplicationContext());

		// 在baseActivity onDestroy中清理clearMemoryCache();
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				// 在内存和sd卡中缓存,如果经常OOM 移除.cacheInMemory(true) or
				// 每个Activity.onDestory()的时候调用用ImageLoader.getInstance().clearMemoryCache()
				.cacheOnDisk(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 适配大小来display
				.showImageOnLoading(R.drawable.ic_image_load_loading) // 正在加载
				.showImageForEmptyUri(R.drawable.ic_image_load_normal) // 没有图片
				.showImageOnFail(R.drawable.ic_image_load_faild).build(); // 加载失败

		// Create global configuration and initialize ImageLoader with this
		// configuration
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).diskCacheSize(50 * 1024 * 1024)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.diskCacheFileCount(100).memoryCacheSize(10 * 1024 * 1024)
				.defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		initJPush();
		// JPUSH

	}

	private void initJPush() {
		tags.clear();
		tags.add("a");//全部用户标签
		//tags.add("m_id");//会议id
		//tags.add("p_id"); //用户id
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
		// 参数：context, String alias,Set<String> tags (覆盖，而不是新增),
		// TagAliasCallback callback（打标签的回调函数）
		JPushInterface.setAliasAndTags(getApplicationContext(), null,tags ,null);
	}
	/** 
	 * 新增一个标签
	 * @param tag
	 * @param type 标签类型
	 */
	public void addTag(String tag,int type){
		if(type == TAG_TYPE_MEET){
			tag = "m_" + tag;
		}else if(type == TAG_TYPE_PERSON){
			tag = "p_" + tag;
		}else{
			return;
		}
		this.tags.add(tag);
		JPushInterface.setAliasAndTags(getApplicationContext(), null,tags ,null);
	}
	/**
	 * 新增一组标签
	 * @param _tags
	 * @param type
	 */
	public void addTags(String[] _tags,int type){
		Set<String> tmp_tags = Collections.synchronizedSet(new LinkedHashSet<String>());
		for(int i=0;i<_tags.length;i++){
			if(type == TAG_TYPE_MEET){
				_tags[i] = "m_" + _tags[i];
			}else if(type == TAG_TYPE_PERSON){
				_tags[i] = "p_" + _tags[i];
			}else{
				return;
			}
			tmp_tags.add(_tags[i]);
		}
		this.tags.addAll(tmp_tags);
		JPushInterface.setAliasAndTags(getApplicationContext(), null,tags ,null);
	}
	/**
	 *  移除一标签
	 * @param tag
	 * @param type 标签类型
	 */
	public void deleteTag(String tag,int type){
		if(type == TAG_TYPE_MEET){
			tag = "m_" + tag;
		}else if(type== TAG_TYPE_PERSON){
			tag = "p_" + tag;
		}else{
			return;
		}
		this.tags.remove(tag);
		JPushInterface.setAliasAndTags(getApplicationContext(), null,tags ,null);
	}
	
	/**
	 *  移除一组标签
	 * @param _tags
	 * @param type 标签类型
	 */
	public void deleteTags(String[] _tags,int type){
		Set<String> tmp_tags = Collections.synchronizedSet(new LinkedHashSet<String>());
		for(int i=0;i<_tags.length;i++){
			if(type == TAG_TYPE_MEET){
				_tags[i] = "m_" + _tags[i];
			}else if(type == TAG_TYPE_PERSON){
				_tags[i] = "p_" + _tags[i];
			}else{
				return;
			}
			tmp_tags.add(_tags[i]);
		}
		this.tags.removeAll(tmp_tags);
		JPushInterface.setAliasAndTags(getApplicationContext(), null,tags ,null);
	}

	public void cleanUpInfo() {
		// 清除所有缓存
		DataPool dp = new DataPool(DataPool.SP_Name_User, this);
		dp.removeAll();

		PersistentCookieStore cs = new PersistentCookieStore(this);
		cs.clear();

	}

}
