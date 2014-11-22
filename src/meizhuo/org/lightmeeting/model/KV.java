package meizhuo.org.lightmeeting.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KV implements Serializable ,Comparable<KV>{
	
	
	public KV() {
		// TODO Auto-generated constructor stub
	}
	
	public KV(String mkey,String mvalue) {
		// TODO Auto-generated constructor stub
		this.key = mkey;
		this.value = mvalue;
	}
	
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String key;
	@Override
	public String toString() {
		return "KV [key=" + key + ", value=" + value + "]";
	}
	private String value;
	@Override
	public int compareTo(KV another) {
		// TODO Auto-generated method stub
		int comparekey = this.key.compareTo(another.key);
		
		return comparekey;
	}


}
