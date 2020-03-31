package com.coffee.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.web.MimeTypes;

/** 图片的显示
 * 映射 /store/ 到自定义 目录
 *
 */

@WebServlet("/store/*")
public class ImageUrlService extends HttpServlet
{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// requestUri: 例如 /coffee/store/4t5t45t3t23tjkjljlkjlkj.jpg
		String requestUri = request.getRequestURI();
		
		// 取得文件的名称 
		String prefix = "/store/";
		int p = requestUri.indexOf(prefix);
		String filePath = requestUri.substring( p + prefix.length());

		// 目标文件
		File targetFile = Store.getFile( filePath );
		
		// 检查目标文件是否存在
		if(!targetFile.exists() || ! targetFile.isFile())
		{
			System.out.println("目标文件不存在!" +targetFile);
			response.sendError(404);
			return;
		}
		
		// 根据后缀名，获取 Content-Type : image/jpeg, image/png等
		String suffix = urlSuffix( filePath );
		String contentType = MimeTypes.getMimeType(suffix);
		
		// 应答：设置 Content-Type 和 Content-Length 
		long contentLength = targetFile.length();
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(contentLength));
				
		// 应答：读取目标文件的数据, 发送给客户端
		InputStream inputStream = new FileInputStream(targetFile);
		OutputStream outputStream = response.getOutputStream();
		
		try
		{
			streamCopy (inputStream, outputStream);
		}catch(Exception e)
		{
			try{ inputStream.close();} catch(Exception e2){}
		}
		
		outputStream.close();
	}

	// 获取 URL 里的后缀名信息
	private String urlSuffix(String url)
	{
		int p1 = url.lastIndexOf('/');
		if(p1 <0) p1 = 0; // 如果没有/，则从头开始检查
		
		int p2 = url.indexOf('.', p1+1);
		if(p2 > 0)
		{
			return url.substring(p2+1);
		}
		
		return "";
	}
	
	private long streamCopy(InputStream in, OutputStream out) throws Exception
	{
		long count = 0;
		byte[] buf = new byte[8192];
		while (true)
		{
			int n = in.read(buf);
			if (n < 0)
				break;
			if (n == 0)
				continue;
			out.write(buf, 0, n);

			count += n;
		}
		return count;
	}
	
}
