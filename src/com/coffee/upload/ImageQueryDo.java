package com.coffee.upload;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.coffee.DB.DB;
import com.coffee.entity.Picture;
import com.coffee.mysql.util.SqlWhere;
import com.coffee.web.FormData;
import com.coffee.web.restful.RestfulDo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class ImageQueryDo extends RestfulDo
{

	@Override
	public Object execute(JsonObject jreq) throws Exception
	{
		// 当前页面: 从1开始计数
		int pageNumber = jreq.get("pageNumber").getAsInt();
		
		int count = getCount(""); // 一共多少条记录
		int pageSize = 12; // 每页显示多少条
		int pageCount = count / pageSize; // 一共多少页
		if(count % pageSize != 0) pageCount += 1;
		
		// 查询条件 (暂无)
		SqlWhere asw = new SqlWhere(); 
		
		// 排序
		String order = " ORDER BY ID DESC ";
		// 条数限制
		String limit = String.format(" LIMIT %d,%d ", 
				pageSize *(pageNumber-1), pageSize);
		
		String sql = "select * from picture "
				+ asw
				+ order
				+ limit;
		System.out.println("查询: " + sql);
		
		
		List<Picture> rows = DB.query(sql, Picture.class);
		// 处理数据，转成前台需要的格式
		String contextPath = httpReq.getContextPath();
		
		Map<String, Object> jresp =new HashMap<String, Object>();
		jresp.put("count" , count);
		jresp.put("pageNumber", pageNumber);
		jresp.put("pageCount", pageCount);
		
		List<Object> items = new ArrayList<Object>();
		jresp.put("items", items);
		for(Picture row : rows )
		{
			Map<String, Object> j1 = FormData.pojo2Map(row);
			// 拼凑出图片的显示URL
			String url = contextPath + row.storePath;
			j1.put("url", url);
			
			items.add( j1 );
		}
		return jresp;
	}

//	public Map<String,Object> reflectObj(Object configDO) throws Exception{
//		Map<String, Object> map = new HashMap<String, Object>();
//	        //Object configDO = new Object();
//	        Class<?> configDOClass = configDO.getClass();
//	        Field[] fields = configDOClass.getDeclaredFields();
//	        for (Field field : fields) {
//	            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), configDOClass);
//	            Method getMethod = pd.getReadMethod();
//	            Object o = getMethod.invoke(configDO);
//	            map.put(field.getName(), o);
//	        }
//			return map;
//	}
	
	// 使用 SQL 里的 COUNT 函数来查询一共有多少条记录
	private int getCount(String where) throws Exception
	{
		String sql = " select count(id) from picture " + where;
		String[] row = DB.get(sql);
		if(row != null)
		{
			return Integer.valueOf( row[0]);
		}
		return 0;
	}
}
