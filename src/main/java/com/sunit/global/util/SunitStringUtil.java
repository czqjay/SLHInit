package com.sunit.global.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * 
 * @class name：StringUtil
 * @desc：字符串处理,根据数组长度生成对应的?
 * @user：shanjizhou
 * @createTime：Jul 22, 2013 2:35:11 PM
 * @update user：shanjizhou
 * @updateTime：Jul 22, 2013 2:35:11 PM
 * @update desc：
 * @version 
 *
 */
public class SunitStringUtil {
	 
	public String getStringUtil(String[] strString){
		if(null==strString || strString.length==0){
			return "";
		}
		if(strString.length==1){
			return "?";
		}
		String str= "";
		while (((str.split(",").length)!=(strString.length))) {
			str+="?,";
		}
		str = str.substring( 0, str.length()-1);
		
		return str.toString();
	}
	
	/**
	 * 根据预编译数据长度得到预编译?号串 
	* @Title: getPrecompileMark 
	* @Description: 
	* @param @param strString
	* @param @return     
	* @return String  
	* @throws 
	* @author joye 
	* Dec 20, 2013 4:14:58 PM
	 */
	public static String getPrecompileMark(String[] strString){
		if(null==strString || strString.length==0){
			return "";
		}
		if(strString.length==1){
			return "?";
		}
		String str= "";
		while (((str.split(",").length)!=(strString.length))) {
			str+="?,";
		}
		str = str.substring( 0, str.length()-1);
		
		return str.toString();
	}
	
	
	/**
	 * 
	* @Title: getStringbyCommaSeparate 
	* @Description:  从List中获取以逗号分割的字符
	* @param @return     
	* @return String  
	* @throws 
	* @author joye 
	* Jul 2, 2014 11:07:43 AM
	 */
	public static String getStringbyCommaSeparate(List<String>  list){
		StringBuffer sb =new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String s =list.get(i);
			if(StringUtils.isBlank(s))
				continue;
			if(!sb.toString().equals(""))
				sb.append(",");	 
			sb.append(s);
		}
		return sb.toString();
	} 
	
	public static String getStringbyCommaSeparate(String [] arr){
		return getStringbyCommaSeparate (Arrays.asList(arr));
	}
	
	
	/**
	 * 
	* @Title: isBlankIfNotNull 
	* @Description: 对象是否为空值或空串 
	* @param @param object
	* @param @return     
	* @return boolean  
	* @throws 
	* @author joye 
	* Jul 9, 2014 2:43:52 PM
	 */
	public static boolean isBlankOrNull(Object object){
		if(object!=null) 
			return StringUtils.isBlank(object.toString()); 
		return true;
	}

}
