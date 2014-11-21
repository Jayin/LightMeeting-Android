package meizhuo.org.lightmeeting.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KV implements Serializable{
	
	
	
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


}
