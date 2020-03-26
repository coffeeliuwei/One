package com.coffee.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.coffee.web.FormData;
import com.coffee.web.fileupload.UploadHandler;
import com.coffee.web.fileupload.UploadUtils;

public class ImageUploader extends UploadHandler
{
	String tmpFileName;

	@Override
	public File getTmpDir()
	{
		// httpReq 在父类 UploadHandler里定义
		String path = httpReq.getServletContext().getRealPath("/tmp");
		return new File( path);
	}
	
	@Override
	public File getTmpFile(File tmpDir, String realName)
	{
		String suffix = UploadUtils.fileSuffix(realName);
		String uuid = UploadUtils.createUUID();
		this.tmpFileName = uuid + "." + suffix;
		
		return new File(tmpDir, tmpFileName);
	}

	@Override
	public Object complete(long size, FormData formData)
	{
		String storePath = "/tmp/" + tmpFileName;
		String contextPath = httpReq.getServletContext().getContextPath();
		String url =  contextPath + storePath;
		
		Map<String, Object> result = new HashMap <String, Object>();
		result.put("storePath", storePath);
		result.put("url", url);	
		return result;
	}
}
