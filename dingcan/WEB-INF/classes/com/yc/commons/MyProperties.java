package com.yc.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyProperties extends Properties{
	public static MyProperties properties=null;
	
	private MyProperties() throws IOException{
		InputStream iss=MyProperties.class.getClassLoader().getResourceAsStream("db.properties");
		this.load(iss);
	}

	public static MyProperties getInstance() throws IOException {
		if(null==properties){
			properties=new MyProperties();
		}
		return properties;
	}

}
