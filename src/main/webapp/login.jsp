<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="org.springframework.security.core.AuthenticationException"%> 
<%@ page import="org.springframework.security.web.WebAttributes"%>
<%@page import="org.springframework.security.authentication.BadCredentialsException"%>
<%@page import="org.springframework.security.authentication.LockedException"%>
<%@page import="com.sunit.global.util.SessionContext"%>
<%@page import="java.util.*"%>

<%  
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<style > 
#errorMsg{
	margin-top:20px;
	color:red;
	font-size:18px;
	font-famliy:youyuan;
	text-align:center;
} 
</style>
<link rel="icon" href="<%=path%>/html/theme/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="<%=path%>/html/theme/css/login.css" type="text/css" media="screen" />
<script src="<%=path%>/html/js/jqGrid/js/jquery-1.7.2.min.js"
	type="text/javascript"></script>
<TITLE>sunit系统- 登陆</TITLE>
  
<script language="javascript" type="text/javascript"> 
	var logined=false;
	function login(){
		if(logined){
			return false; 
		}  
		if($('#j_password').val()==''||$('#j_username').val()==''){ 
			$('#errorMsg').text('请输入用户名和密码'); 
			return false;  
		}
		logined=true; 
		$('#loginForm').submit();
	
	}; 
 
	$(function(){
		$('#j_username').blur(function(event){
				if($(this).val()==''){
					$('#namelable').text('用户名'); 
				}  
		});
		
		$('#j_username').focus(function(event){ 
				$('#namelable').text('');
		});
		
		$('#j_password').blur(function(event){
				if($(this).val()==''){ 
					$('#pwdlable').text('密码'); 
				}
		});
		
		$('#j_password').focus(function(event){  
				$('#pwdlable').text('');
		});
		
		$(document).keypress(function(event){
		  if ( event.which == 13 ) {
		     login(); 
		   } 
		}); 
 		
 	
 	});
</script>
</HEAD>

<body background="<%=path%>/html/theme/images/bg.png" > 
<div class="loginBox" >
  <div class="contentBox" style='  margin-top: 152px;  '> 
    <div class="sliderPicsBox">  
      <div class="pics"><img src="<%=path%>/html/theme/images/img1.png" width="418" height="295" /></div> 
      
    </div> 
    <div class="textBox"> 
      <div class="textName">登录系统</div> 
      <div class="inputContent">
        <form method="post" name="loginForm" id="loginForm" action='<%=path%>/j_spring_security_check'> 
          <table width="353" border="0" cellpadding="0" cellspacing="0">
          	
          	<tr>
              <td height="40" colspan=2><div id='errorMsg'>
						<%   
				                AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
								
				              	String errorMsg = ex != null ? ex.getMessage() : null;
				              	if(ex!=null){
				              		if(ex.getClass().isAssignableFrom(BadCredentialsException.class)){
				              			errorMsg="登录用户名或密码错误"; 
				              		}else if(ex.getClass().isAssignableFrom(LockedException.class)){ 
				              			errorMsg=ex.getMessage(); 
				              		}else{ 
				              			errorMsg="登录发生错误 请重试";
				              		}
				              	}else{
				              		errorMsg="";
				              	} 
									
				                out.print(errorMsg);  
						%>  
						</div></td> 
            </tr>
           
            <tr>
              <td width="69" height="60" align="right">用户名：</td>
              <td width="284"><label for="textfield"></label>
               <input type='text' name='j_username' id='j_username'/>
               </td> 
            </tr>
            <tr>
              <td height="60" align="right">密&nbsp;&nbsp;&nbsp;码：</td>
              <td><label for="textfield2"></label>
               <input type='password' name='j_password' id='j_password'/>
               </td>
            </tr>
            
            <tr>
              <td>&nbsp;</td>  
              <td height="60">
              <input onclick ='return login()' type='button' name="loginSubmit" id="loginSubmit"  onFocus="this.blur()" value="登录" tabindex="3" />
       
           </td>
            </tr> 
          </table>
        </form>
      </div>
    </div>
  </div>
  <div class="copyRightBox">CopyRight©2013 sunit</div>
</div>

</body> 

</HTML>
