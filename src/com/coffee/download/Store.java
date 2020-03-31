package com.coffee.download;

import java.io.File;

/** 数据存储位置
 * 
 */
public class Store
{
	// 图片存储位置
	public static File dir = new File("c:/down/");
	
	static{
		// 创建目录
		dir.mkdirs();
	}
	
	// 数据目录
	public static File getDir()
	{
		return dir;
	}
	
	// 数据文件
	public static File getFile(String path)
	{
		return new File(dir, path);
	}	
}


