package meizhuo.org.lightmeeting.utils;

import java.io.File;

import android.os.Environment;

/**
 * 文件路径管理
 * 
 * @author Jayin Ton
 * 
 */
public class FilePath {

	public final static String File_Save_Path = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ "LightMeeting" + File.separator;
	
	/**
	 * SD卡上保存主目录<br>
	 * @return
	 */
	public static String getFileSavePath(){
		return File_Save_Path;
	}
	
	public static String getImageFilePath(){
		File f = new File(File_Save_Path + "img" + File.separator);
		if (!f.exists())
			f.mkdir();
		return f.getAbsolutePath() + File.separator;
	}
	

}
