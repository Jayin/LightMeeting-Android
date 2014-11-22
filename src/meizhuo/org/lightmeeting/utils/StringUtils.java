package meizhuo.org.lightmeeting.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import meizhuo.org.lightmeeting.model.KV;

public class StringUtils {
	
	/**
	 * 判断给定字符串是否为空白串。空白串是由空格( ' ' ).制符表(\t).回车符"\r".换行符'\n'组成的字符串
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input){
		if (input == null && "".equals(input.trim())){
			return true;
		}
		for (int i = 0; i < input.length(); i++){
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\n' && c != 'r') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 检测字符串只能包含：中文
	 * @param sequence
	 * @return
	 */
	public static boolean isNickname(String sequence) {
		final String format = "[\u4e00-\u9fa5]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		return matcher.find();
	}
	/**
	 * create by json
	 * @param phonename
	 * @return
	 */
	public static boolean isPhone(String phonename){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher match = pattern.matcher(phonename);
		return match.find();
	}
	/**
	 * 判断是否邮箱
	 * @param sequence
	 * @return
	 */
	public static boolean isEmail(String sequence){
		Pattern pattern  = Pattern.compile("^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher = pattern.matcher(sequence);
		return matcher.find();
	}
	/**
	 * 判断是否密码
	 * @param password
	 * @return
	 */
	public static boolean isPassword(String password){
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]{8,16}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.find();
	}
	
	/**
	 * 服务器不支持中文路径的情况下需要转换url的编码
	 * @param url
	 * @return
	 */
	public static String encodeGB(String url){
		//转换中文编码
		String split[] = url.split("/");
		for(int i = 0 ; i <split.length;i++){
			try {
				split[i] = URLEncoder.encode(split[i], "GB2312");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			split[0] = split[0] + "/" + split[i];
		}
		
		 split[0] = split[0].replaceAll("\\+", "%20");//处理空格  
	        return split[0]; 
	}
	
/*	
	public static List<KV> sortkv(List<KV>list){
		List<KV>data =  new ArrayList<KV>();
		KV kv= new KV();
		String[] array = new String[list.size()];
		for(int i=0;i<array.length-1;i++){
			array[i] = list.get(i).getKey();
		}
		Arrays.sort(array);
		List listkey=Arrays.asList(array);
		for(int g=0;g<listkey.size();g++)
		{
			String p = String.valueOf(listkey.get(g));
			
		}
		for(int i=0;i<listkey.size();i++)
		{
			String key = (String) listkey.get(i);
			String value;
			for(int j=0;i<list.size();j++)
			{
				if(list.get(j).getKey().equals(key))
				{
					value = list.get(j).getValue();
					kv.setKey(key);
					kv.setValue(value);
				}
			}
			data.add(kv);
		}
		return data;
		
	}*/

}
