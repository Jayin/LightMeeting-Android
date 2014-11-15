package meizhuo.org.lightmeeting.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Map;

import com.loopj.android.http.Base64;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * DataPool is tool that helps you easier to do some operation in the
 * SharedPreference <br>
 * <h1>Like : add(),remove(),get(),set()</h1> <h1>Note :</h1> <li>In currrent
 * version of android SDK,DataPool is not support multi thread
 * @author Jason
 *
 */
public class DataPool {
	
	public final static String SP_Name_User = "User"; //SharedPreference's name
	public final static String SP_Key_User = "user"; // key name in SharedPreference
	public final static String SP_Key_User_Name = "username"; //登录账号
	public final static String SP_Key_User_PSW = "psw";  //登录密码
	
	private Context context;
	private String DataPoolName = "DataPool";
	private SharedPreferences sp;

	public DataPool(Context context) {
		// TODO Auto-generated constructor stub
		this("DataPool", context);
	}
	public DataPool(String dataPoolName, Context context) {
		// TODO Auto-generated constructor stub
		this.DataPoolName = dataPoolName;
		this.context = context;
		sp = context.getSharedPreferences(this.DataPoolName, Context.MODE_PRIVATE);
	}
	
	/**
	 * add a key
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(String key, Serializable value){
		boolean flag = false;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos =  new ObjectOutputStream(bos);
			oos.writeObject(value);
			String base64String = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
			sp.edit().putString(key, base64String).apply();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * add a key(String, default is "temp")-value(object) into SharePreference
	 * @param value value of this pair , with the default key = "temp"
	 * @return true if add successfully
	 */
	public boolean put(Serializable value){
		return put("temp", value);
	}
	
	/**
	 * get value(Serializable Object) from DataPool(SharedPreference) with the given key
	 * @param key key of this pair, with
	 * @return
	 */
	public Serializable get(String key){
		if(!contains(key))
			return null;
		String base64String = sp.getString(key, "");
		byte[] buf = Base64.decode(base64String, Base64.DEFAULT);
		ByteArrayInputStream bis = new ByteArrayInputStream(buf);
		ObjectInputStream ois = null;
		Serializable result = null;
		try {
			ois = new ObjectInputStream(bis);
			result = (Serializable)ois.readObject();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(ois != null){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return result;
	}
	
	/**
	 * check if DataPool(SharedPreference)contain the given key
	 * @param key the given key
	 * @return true if it contains
	 */
	public boolean contains(String key){
		return sp.contains(key);
	}
	
	/**
	 * check if DataPool(SharedPreference)is empty
	 * @return
	 */
	public boolean isEmpty(){
		return sp.getAll().size() == 0;
	}
	
	/**
	 * remove a key-value of this pair
	 * @param key
	 */
	public void remove(String key){
		if(!contains(key))
			return ;
		sp.edit().remove(key).apply();
	}
	
	/**
	 * remove all the key-value of this pair
	 */
	public void removeAll(){
		if(isEmpty())
			return ;
		Map<String, ?>map = sp.getAll();
		for(String key : map.keySet())
		{
			remove(key);
		}
	}
	
	/**
	 * update the key-value
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Serializable value){
		if (!contains(key))
			return false;
		return put(key, value);
	}

}
