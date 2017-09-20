package com.sunit.global.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Global {

	public static final String IllegalAccessPage="/j_spring_security_logout";
	private   boolean scheduleActive=false; 
	private   boolean compressActive=false; 
	private  String AnonymousUserAccessURL=""; 
	private  String ClusterFileServerDir="";  
	private  String ClusterFileServerURL="";
	public  boolean funcSwitch=true; //版本开关变量 (阅卷功能.)
	private  String rootName="";
	 
	 String appid="";  //  微信 的 appid
	 String secret=""; //  微信 的 密码 
	 String unRegMsg="" ; //未注册msg 
	 String signMsg=""; //签到成功msg 
	 
 

	public String getUnRegMsg() {
		return unRegMsg;
	}

	public void setUnRegMsg(String unRegMsg) {
		this.unRegMsg = unRegMsg;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getAppid() {
		return appid;
	}
 
	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getClusterFileServerURL() {
		return ClusterFileServerURL; 
	}

	public void setClusterFileServerURL(String clusterFileServerURL) {
		ClusterFileServerURL = clusterFileServerURL;
	}

	public String getClusterFileServerDir() { 
		return ClusterFileServerDir;
	} 

	public void setClusterFileServerDir(String clusterFileServerDir) {
		this.ClusterFileServerDir = clusterFileServerDir; 
	}

	public static  void processIllegalAccess(HttpServletRequest request, 
			HttpServletResponse response) throws IOException{  
			response.sendRedirect(request.getContextPath()+Global.IllegalAccessPage);
	} 

	public  boolean isScheduleActive() {  
		return scheduleActive;
	} 
  
	public  void setScheduleActive(boolean scheduleActive) {
		this.scheduleActive = scheduleActive;
	}

	public  boolean isCompressActive() { 
		return compressActive;
	}

	public  void setCompressActive(boolean compressActive) {
		this.compressActive = compressActive;
	}

	public String getAnonymousUserAccessURL() {
		return AnonymousUserAccessURL;
	}

	public void setAnonymousUserAccessURL(String anonymousUserAccessURL) {
		this.AnonymousUserAccessURL = anonymousUserAccessURL;
	}
 
	public static String getIllegalAccessPage() {
		return IllegalAccessPage;
	}


	
	
	
	
	
}
 