<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; chareset=UTF-8"%> 
<%@ taglib uri="/sunittag" prefix="sui"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.sunit.global.util.SessionContext" %>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	session.setAttribute("ctxPath", path);
	String userId = SessionContext.getUserId(request);
%>
<html > 
	<head>  
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>sunit系统</title>

		<%@ include file="common/common.jsp"%> 

		<!--[if IE]> 
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

		<style type="text/css">
.ui-accordion .ui-accordion-content {
	padding: 0 0;
}
.dynatree-container * {
	line-height: 100%;
}

ul.dynatree-container {
	background-color: transparent;
	border: 0px dotted gray;
	font-family: tahoma, arial, helvetica;
	font-size: 10pt;
	margin: 0;
	overflow: visible; 
	padding: 3px;
	white-space: nowrap;
	backgrand: "";
}


ul.dynatree-container a { 
	color: #333;
	margin: 0 0 0 3px;
	text-decoration: none;
	vertical-align: top;
}   
 
.tabsPage .tabsPageHeader .tabsPageHeaderContent  {
     
    background:url("js/jquery-ui/themes/redmond/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC
}
.toggleCollapse{
	 background:url("js/jquery-ui/themes/redmond/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC
}

.ui-dialog .ui-dialog-content {

  border:0 currentColor;
  overflow:hidden;
  padding:0 0; 
  position:relative;
  zoom:1;
}

td a span { 
    color: blue; 
}


.custom-combobox { 
   /** margin-left: 5px;*/
}
</style>
 

		<script type="text/javascript">  

		
		
$(function(){
	DWZ.init("${ctxPath}/html/js/dwz/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug: true,	// 调试模式 【true|false】
		callback:function(){
			$(document).bind(DWZ.eventType.initEnvAfter,function(){
				$accordion = $("#accordion").accordion({
		 				heightStyle: "fill" 
				}); 
				var id = '<%=userId%>';
				if(id=='0'){
					navTab.openTab('402880084105e4120141071171390163', '${ctxPath}/html/adminScore/adminScoreList.jsp',{title:'admin', fresh: true, external: false,close:false});

				}else{
					navTab.openTab('scheduleTest', '${ctxPath}/html/user/userTodoList.jsp',{title:'待办事项', fresh: true, external: false,close:false});
				}
			})
			initEnv();
			$("#themeList").theme({themeBase:"dwz/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});  
	 

	
	 
	//当得到logintop.jsp 的html时 , 返回登录页面
	$(document).ajaxError( function(event, jqXHR, ajaxSettings, thrownError){
		var sessionExipiredTag="sessionTimeout";
		sessionExipiredTag="<"+sessionExipiredTag+">";
		if(jqXHR.responseText&&jqXHR.responseText.indexOf(sessionExipiredTag)>-1){ 
			//if(thrownError.message.indexOf("unexpected character")>-1){ 
				window.top.location='${ctxPath}/login.jsp';  
			//}
		}             
	} 
	)
	
	
	
	 
	window.WWWROOT = '<%=path%>'; 
	window.titleSplit = '---'; 
	suntoolkit = {
		openOptions :function(options,url,dialogId){ 
			$.extend(options,{close:function(event, ui ){
				suntoolkit.destroyDialog($(event.target).attr('id')) 
			}})
			$.extend(options,{modal:true}) 
			if(!dialogId)
				dialogId='mydialog';
			if($('#'+dialogId).length==0)
				 $('body').append('<div id='+dialogId+'></div>'); 
		 	$('#'+dialogId+':first').attr('sunitDialog','1');  
			$('#'+dialogId+':first').dialog(options).dialog('open');  
			$('#'+dialogId+':first').load(url,{nd:Math.random()},function(){
				$('#'+dialogId+':first').dialog(options).dialog('open'); 
				initUI($('#'+dialogId+':first').get(0));  
			}); 
			
		}, 
		open :function(url,name,iWidth,iHeight) 
			{ 
				var iTop = ( $(window).height()-iHeight)/2; //获得窗口的垂直位置;
				var iLeft = ( $(window).width() -10-iWidth)/2; //获得窗口的水平位置;
				window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
				DWZ.debug(url)
				DWZ.debug(name)
				DWZ.debug('height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no')
			}, 
		getDialogEL :function(dialogId){
			$.extend(suntoolkit.dialogOptions,{close:function(event, ui ){
				suntoolkit.destroyDialog($(event.target).attr('id')) 
			}})
			if(!dialogId)
				dialogId='mydialog';
			if($('#'+dialogId).length==0)
				 $('body').append('<div id='+dialogId+'></div>');
			$('#'+dialogId+':first').dialog(suntoolkit.dialogOptions).dialog('open'); 
			return 	 $('#'+dialogId+':first')
		}, 
		showDialog :function(options,dialogId){
			
			if(!dialogId)
				dialogId='mydialog';
			if($('#'+dialogId).length==1)
			return  $('#'+dialogId).dialog(options).dialog('open')
		},
		destroyDialog :function(dialogId){
			DWZ.debug(arguments)
			if(typeof dialogId =='object'){
				$(dialogId).closest('[sunitdialog]').find('iframe').remove() // 在 destroy()时, 防止iframe事件的onload事件触发
				return $(dialogId).closest('[sunitdialog]').dialog('destroy')
			}
			if(!dialogId)
				dialogId='mydialog';
				 
			$('#'+dialogId).html(''); 
			if($('#'+dialogId).length==1) 
			 	return $('#'+dialogId).dialog('destroy');
			
			 	
		}, 
		contextMenuClick :function (action, dtnote){
			DWZ.debug(arguments)
		},
		getGridHeight :function (dialog){  
				var dialogId;
				if(dialog){ 
					dialogId = dialog=='dialog'?'mydialog':dialog
					 var $dialog=$('#'+dialogId)
					 DWZ.debug($('.pageHeader',$dialog))   
					 DWZ.debug( $dialog )
					 DWZ.debug(  $dialog.height() )
					return $dialog.height()- $('.pageHeader',$dialog).height()-60;
				}
				return $('.navTab-panel').height()- $('.pageHeader:visible').height()-89;		   
		},
		offLoadingMask :function (){
			$("#background").attr('offLoading','true');   
		}
		
		
		
	};
	
	
	indexMainTree={
		plhs:{},
			deptClick:function(node, event){   
				if(node.data.isFolder) return; 
					navTab.openTab(node.data.id, window.WWWROOT+node.data.url,{title: node.data.title, fresh: true, external: false});
					//event.preventDefault();  
		 	},
		 	postProcess: function(data,dataType){  //后处理 ajax结果的拦截处理
	        	for(v in data){
	        		if(data[v].caption){ 
	        			data[v].isFolder=true 
	        			data[v].isLazy=true 
	        			data[v].expand=false   
	        			data[v].title=data[v].caption
	        		//	data[v].noLink=true   
	        		} 
	        		if(data[v].url){  
	        			data[v].isFolder=false
	        			data[v].isLazy=false 
	        			data[v].expand=false   
	        			data[v].title=data[v].title  
	        			//data[v].noLink=true    
	        		} 
	        	} 
        		return data; 
        	} 
		}
		
		
		
}); 



(function( $ ) {
  })( jQuery );
</script>
	</head>

	<body scroll="no">
		<div id="layout">
			<div id="header">
				<div class=""> 
				
					<ul class="nav" id='mynav'>
                       <li>
                           你好,${userName}
						</li>
						<li>
							<a href="<%=path%>/j_spring_security_logout">退出</a>
						</li>
					</ul>
					<ul class="themeList" id="themeList">
					<!--  
						<li theme="default">
							<div class="selected"> 
								蓝色
							</div>
						</li>
						<li theme="green">
							<div>
								绿色
							</div>
						</li> -->
						<!--<li theme="red"><div>红色</div></li>-->
					<!-- 	<li theme="purple">
							<div>
								紫色
							</div>
						</li>
						<li theme="silver">
							<div>
								银色
							</div>
						</li>
						<li theme="azure">
							<div>
								天蓝
							</div>
						</li>
						-->
					</ul>
				</div>

				<!-- navMenu -->

			</div>

			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse">
							<div></div>
						</div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse">
						<h2>
							主菜单
						</h2>
						<div>
							收缩
						</div>
					</div>


					<div id="accordion">
						<h3>
							业务管理
						</h3> 
						<div >
						  
								  
					<sui:tree  id="mainTreeForPlan" postData="planManager"  postProcess ="indexMainTree.postProcess" 
 						 onclick="indexMainTree.deptClick"  url="${ctxPath}/base/loadChildren.action" 
								></sui:tree> 
 
						</div>
						<h3>
							系统管理
						</h3>
						<div>
				<sui:tree  id="mainTreeForSystem" postData="systemManager"  postProcess ="indexMainTree.postProcess" 
 						 onclick="indexMainTree.deptClick"  url="${ctxPath}/base/loadChildren.action" 
								></sui:tree> 
						</div>
					</div> 

				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent">
							<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
							</ul>
						</div> 
						<div class="tabsLeft">
							left
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">
							right
						</div>
						<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">
							more
						</div>
					</div>
					<ul class="tabsMoreList">

					</ul>
					<div class="navTab-panel tabsPageContent layoutBox" style='overflow-y:auto;overflow-x:hidden'>
					</div>
				</div>
			</div>
		</div> 
		<div id='mydialog' style='overflow:auto'></div>
		<div id="footer"> 
			CopyRight2017 longerinfo
		</div>
	</body>
	<script type="text/javascript">
	</script>
	
	
</html>