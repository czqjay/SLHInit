package com.sunit.global.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sunit.global.base.Global;

@Component
public class SpringContextUtils implements ApplicationContextAware{
	
	static ApplicationContext applicationContext=null;
	
	public static ApplicationContext getSpringContext(){
			  
		return applicationContext;
	} 

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		applicationContext=arg0;
	}
	 
	  
	public static  Global getGlobal(){ 
		Global g = (Global) SpringContextUtils.getSpringContext().getBean("globalPara");
		return g;
	}
	
	
	public static  String  getWXAppid(){ 
		return getGlobal().getAppid(); 
	}
	
	  
	public static  String  getWXSecret(){ 
		return getGlobal().getSecret(); 
	}
	
	
	
}
