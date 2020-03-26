package com.coffee.upload;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.coffee.DB.DB;
import com.coffee.entity.Picture;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.FormData;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.JsonObject;



/** 图片信息获取接口
 *
 */
public class ImageViewDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		int id = jreq.get("id").getAsInt();
		SqlWhere asw = new SqlWhere();
		asw.add2("id", id);
		
		String sql = "select * from picture " + asw;
		Picture row = (Picture) DB.get(sql, Picture.class);
		if(row == null)
			throw new Exception("无此图片 id=" + id);
		
		Map<String, Object> jresp =FormData.pojo2Map(row);;	
		String contextPath = httpReq.getContextPath();
		String url = contextPath + row.storePath;
		jresp.put("url", url);		
		
		return jresp;
	}

	
}
