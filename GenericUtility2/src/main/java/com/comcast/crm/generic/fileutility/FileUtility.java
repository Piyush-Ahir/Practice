package com.comcast.crm.generic.fileutility;

import java.io.FileInputStream;
import java.util.Properties;

public class FileUtility {
	public String getDataFromPropertyFile(String key) throws Throwable {
		FileInputStream fis = new FileInputStream("./configAppData/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		return pObj.getProperty(key);
	}
}
