package com.coffee.upload;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;


import com.coffee.web.restful.RestfulDo;
import com.coffee.DB.DB;
import com.coffee.entity.Picture;
import com.google.gson.JsonObject;

/** 图片入库
 * 文件从 WebRoot/tmp/ 转移到 WebRoot/data/ ，同时在数据库里插入记录
 *
 */
public class ImageSaveDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		String tmpPath = jreq.get("tmpPath").getAsString(); // 临时文件路径
		String tag = jreq.get("tag").getAsString(); // 分类用的标签
		long size = jreq.get("size").getAsLong(); // 文件大小
		String realName = jreq.get("realName").getAsString(); // 原始的文件名
		
		// 移动文件: 从tmp目录移到 data目录
		String storePath = moveFile( tmpPath);
				
		// 创建记录
		Picture row = new Picture();
		row.setName(jreq.get("realName").getAsString());
		row.setRealName(jreq.get("realName").getAsString());
		row.setSize(size);
		row.setStorePath(storePath);
		row.setTag(tag);
		row.setTimeCreated(new Date());
		row.setTimeModified(new Date());
		
		// 插入到数据库
		DB.insert( row );
		return null;
	}

	// 转移临时文件
	private String moveFile(String tmpPath) throws Exception
	{
		String docBase = httpReq.getServletContext().getRealPath("/");
		File docBaseDir = new File(docBase);
		
		// 源文件
		File srcFile = new File(docBaseDir, tmpPath);
		String fileName = srcFile.getName();
		
		// 目标目录
		File dstDir = new File(docBaseDir, "data");
		dstDir.mkdirs();
		
		
		FileUtils.moveFileToDirectory(srcFile, dstDir, true);
		
		return "/data/" + fileName;
	}
}
