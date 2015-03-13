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
	
	
	
	
	
	
	@Override
	public String toString() {
		return "KV [value=" + value + ", key=" + key + ", isclick=" + isclick
				+ "]";
	}




	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isIsclick() {
		return isclick;
	}

	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}



	private String value;
	private String key;
	private boolean isclick;
	
	
	
	@Override
	public int compareTo(KV another) {
		// TODO Auto-generated method stub
		int comparekey = this.key.compareTo(another.key);
		
		return comparekey;
	}


}
