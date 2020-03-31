package com.coffee.entity; 

import com.coffee.mysql.annotation.COLUMNS; 
import com.coffee.mysql.annotation.TABLE; 
import java.util.Date; 

@TABLE(name="picture")  
@COLUMNS(auto=true,generated="id") 
public class Picture 
{ 
 
	public Long id ; 
	public String realName ; 
	public String name ; 
	public Long size ; 
	public String storePath ; 
	public String tag ; 
	public Date timeCreated ; 
	public Date timeModified ; 


	public void setId(Long id)
	{
		this.id=id;
	}
	public Long getId()
	{
		return this.id;
	}
	public void setRealName(String realName)
	{
		this.realName=realName;
	}
	public String getRealName()
	{
		return this.realName;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setSize(Long size)
	{
		this.size=size;
	}
	public Long getSize()
	{
		return this.size;
	}
	public void setStorePath(String storePath)
	{
		this.storePath=storePath;
	}
	public String getStorePath()
	{
		return this.storePath;
	}
	public void setTag(String tag)
	{
		this.tag=tag;
	}
	public String getTag()
	{
		return this.tag;
	}
	public void setTimeCreated(Date timeCreated)
	{
		this.timeCreated=timeCreated;
	}
	public Date getTimeCreated()
	{
		return this.timeCreated;
	}
	public void setTimeModified(Date timeModified)
	{
		this.timeModified=timeModified;
	}
	public Date getTimeModified()
	{
		return this.timeModified;
	}

} 
 