package meizhuo.org.lightmeeting.utils;

import meizhuo.org.lightmeeting.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

/**
 * Notification 轻量封装,常用的都在这里了</br>
 * How to use?</br>1.先创建对象,默认是ETips icon 可以自动取消(点击后显示Intent然后消失).震动and
 * 铃声，setSmallIcon(null);setVibrate(new long[0])</br>
 * 2.call:setContentText() / setContentIntent()</br>
 * 3.call.setNotificationId() (虽然有默认值)</br>
 * 4.call:show();</br>
 * @author Jayin Ton
 *
 */
public class BaseNotification extends NotificationCompat.Builder{
	private Context context;
	private int NotificationID = 0x0000f4;
	
	

	public BaseNotification(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setContentTitle(context.getResources().getString(R.string.app_name))
				.setSmallIcon(R.drawable.lm_icon)
				.setAutoCancel(true)
				.setVibrate(new long[] {100,300,500,300});
		this.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));//Android 2.3.5必须添加一个ContentIntent?
	}
	
	/**
	 * 显示Notification
	 */
	public void show(){
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(NotificationID, this.build());
	}
	
	/**
	 * 取消当前的Notification
	 */
	public void cancle(){
		((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NotificationID);
	}
	
	/**
	 * 设置Notification ID
	 * 
	 * @param id
	 */
	public void setNotificationID(int id) {
		this.NotificationID = id;
	}
    /**
     * 设置点击时的Intent
     */
	@Override
	public Builder setContentIntent(PendingIntent intent) {
		return super.setContentIntent(intent);
	}
   /**
    * 设置通知消失时的Intent
    */
	@Override
	public Builder setDeleteIntent(PendingIntent intent) {
		return super.setDeleteIntent(intent);
	}

	@Override
	public Builder setAutoCancel(boolean autoCancel) {
		return super.setAutoCancel(autoCancel);
	}

	@Override
	public Builder setContentText(CharSequence text) {
		return super.setContentText(text);
	}

	@Override
	public Builder setContentTitle(CharSequence title) {
		return super.setContentTitle(title);
	}

	@Override
	public Builder setSmallIcon(int icon) {
		return super.setSmallIcon(icon);
	}

	@Override
	public Builder setTicker(CharSequence tickerText) {

		return super.setTicker(tickerText);
	}

	@Override
	public Builder setVibrate(long[] pattern) {
		return super.setVibrate(pattern);
	}

	@Override
	public Builder setWhen(long when) {
		return super.setWhen(when);
	}

}
