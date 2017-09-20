<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/sunittag" prefix="sui"%>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="${ctxPath}/html/role/js/roleList.js"></script>

<div class="pageContent">
	<div id="role" style='height:100%'> 
		<sui:grid id="subResoucreGrid" pageId="subResoucreGrid_page" title=""
			offsetHeight="290"
			pkName="id" url="${ctxPath}/sysresource/loadSubResoucreListDataGrid.action?subID=${param.ID}"
			 > 
			<sui:gridCell name="id" title="" hidden="true" align="center"></sui:gridCell>
			<sui:gridCell name="moduleCaption" title="资源名称" align="left" width="250"
				align='center'></sui:gridCell>
			
			<sui:gridCell name="caption" title="资源说明" align="left" width="150"
				align='center'>
			</sui:gridCell>
			<sui:gridCell name="content" title="资源入口" align="left" width="500"
				align='center'></sui:gridCell>
		</sui:grid>
		
	</div>