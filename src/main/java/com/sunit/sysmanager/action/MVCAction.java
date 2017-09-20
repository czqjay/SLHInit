package com.sunit.sysmanager.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.Application;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sunit.global.base.binder.BaseAction;
import com.sunit.global.base.dao.BaseDAO;
import com.sunit.global.util.Paging;
import com.sunit.global.util.SessionContext;
import com.sunit.global.util.SpringContextUtils;
import com.sunit.global.util.SunitStringUtil;
import com.sunit.global.util.jsonlibConvert.DateProcessor;
import com.sunit.sysmanager.po.SysResource;
import com.sunit.sysmanager.po.User;
import com.sunit.sysmanager.service.NaviMenuManager;
import com.sunit.sysmanager.service.SysResourceManager;
import com.sunit.sysmanager.service.UserManager;

/**
 * a simple example for CRUD
 * 
 * @author joye
 * 
 */
@Controller
@RequestMapping("/base")
public class MVCAction extends BaseAction {

	static Logger logger = Logger.getLogger(MVCAction.class);

	@Autowired
	BaseDAO dao;

	@Autowired
	UserManager um;

	@Autowired
	NaviMenuManager nm;


	@Autowired
	SysResourceManager sm;

	public static Map<String, HttpSession> sessionMap = new HashMap();

	

	@RequestMapping("/getFile.action")
	public void getFile(HttpServletRequest request, HttpServletResponse response,String remoteFileName) {
		logger.debug("MVCAction.getFile()");
		InputStream in = null;
		OutputStream out = null;
		String remoteUrl="";
		try { 
			String fileName = remoteFileName; 
			 remoteUrl = SpringContextUtils.getGlobal()
					.getClusterFileServerDir()
					+ "/AppraisalInfo/" + fileName; 
			SmbFile remoteFile = new SmbFile(remoteUrl);
			if (remoteFile == null) {
				logger.error("共享文件不存在:"+remoteUrl); 
				return ;
			} 
			response.reset();
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			response.addHeader("Content-Disposition", "attachment;filename=" 
					+  new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			response.addHeader("Content-Length", "" + remoteFile.length()); 
			response.setContentType("application/octet-stream");
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[10240];
			while (in.read(buffer) != -1) { 
				out.write(buffer);
				buffer = new byte[10240];
			} 
		} catch (Exception e) { 
			logger.error("文件下载:"+remoteUrl, e);
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) { 
				logger.error("文件下载关闭流:", e); 
			}
		} 
	}
	
	@RequestMapping("/getRemoteImg.action")
	public void getRemoteImg(HttpServletRequest request, HttpServletResponse response,String remoteFileName,String upload) {
		logger.debug("MVCAction.getRemoteImg()");
		InputStream in = null;
		OutputStream out = null;
		String remoteUrl="";
		try { 
			if(StringUtils.isBlank(upload)){
				remoteUrl = "/waterMask";
			}else {
				remoteUrl = ""; 
			}
			String fileName = remoteFileName; 
			 remoteUrl = SpringContextUtils.getGlobal()
					.getClusterFileServerDir()
					+remoteUrl+"/" + fileName; 
			SmbFile remoteFile = new SmbFile(remoteUrl);
			logger.debug(remoteUrl);
			if (remoteFile == null) {
				logger.error("共享图片不存在:"+remoteUrl);  
				return ;
			} 
			response.reset();
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
//			response.addHeader("Content-Disposition", "attachment;filename=" 
//					+  new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
//			response.addHeader("Content-Disposition", "attachment;filename=" 
//					+   fileName); 
//			response.addHeader("Content-Length", "" + remoteFile.length()); 
//			response.setContentType("application/octet-stream");
			out = new BufferedOutputStream(response.getOutputStream()); 
			byte[] buffer = new byte[10240];
			while (in.read(buffer) != -1) { 
				out.write(buffer);
				buffer = new byte[10240];
			} 
			out.flush();
		} catch (Exception e) { 
			logger.error("图片下载:"+remoteUrl, e);
		} finally {
			try {
				out.close();
				in.close();
			} catch (Exception e) {  
				logger.error("图片下载关闭流:", e); 
			}
		} 
	}
	

	/**
	 * return to loginPage
	 * 
	 * @Title: login
	 * @Description:
	 * @param
	 * @param request
	 * @param
	 * @param userName
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/login.action")
	public String login(HttpServletRequest request, String userName) {
		logger.debug("MVCAction.login()");
		return "/login.jsp";
	}

	/**
	 * 删除用户
	 * 
	 * @Title: delUsers
	 * @Description:
	 * @param
	 * @param pw
	 * @param
	 * @param ids
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/delUsers.action")
	public String delUsers(PrintWriter pw, String ids) {
		logger.debug("MVCAction.delUsers()");
		Map map = new HashMap();
		map.put("success", false);
		try {
			String[] idArr = ids.split(",");
			um.delete(idArr);
			map.put("success", true);
		} catch (Exception e) {
			map.put("msg", e.getMessage());
			logger.error("删除用户：", e);
		}
		pw.print(JSONObject.fromObject(map).toString());
		return null;
	}

	/**
	 * 对象是否存在
	 * 
	 * @Title: isExists
	 * @Description:
	 * @param
	 * @param u
	 * @param
	 * @param pw
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/isExists.action")
	public String isExists(User u, PrintWriter pw) {
		logger.debug("MVCAction.isExists()");
		Map map = new HashMap();
		map.put("success", "false");
		List list = null;
		// List list = um.isExtists(u);
		if (list.size() == 0) {
			map.put("success", "1");
		}
		pw.print(JSONObject.fromObject(map).toString());
		return null;
	}

	/**
	 * return to gridPage
	 * 
	 * @Title: getList
	 * @Description:
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/getList.action")
	public String getList(HttpServletRequest request,
			HttpServletRequest response) {
		logger.debug("MVCAction.getList()");
		return "plan/testPlan";
	}

	/**
	 * get Uusers by id, return to editPage
	 * 
	 * @Title: updateUsers
	 * @Description:
	 * @param
	 * @param pw
	 * @param
	 * @param id
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/updateUsers.action")
	public String updateUsers(PrintWriter pw, String id,
			HttpServletRequest request, HttpServletRequest response) {
		logger.debug("MVCAction.updateUsers()");
		User u = um.get(id);
		request.setAttribute("user", u);
		return "plan/testPlanFormUpdate";
	}

	/**
	 * jqGrid 数据提供者 返回JSON
	 * 
	 * @Title: loadDataGrid
	 * @Description:
	 * @param
	 * @param pw
	 *            resopnse stream
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @param rows
	 *            jqgrid 单页行数
	 * @param
	 * @param page
	 *            当前页数
	 * @param
	 * @param para
	 *            搜索VO
	 * @param
	 * @return
	 * @return String
	 * @throws
	 * @author joye Jun 16, 2013
	 */
	@RequestMapping("/loadDataGrid.action")
	public String loadDataGrid(PrintWriter pw, HttpServletRequest request,
			HttpServletRequest response, final int rows, int page,
			SearchPara para) {
		logger.debug("MVCAction.loadDataGrid()");
		StringBuffer hqlBuf = new StringBuffer();
		hqlBuf
				.append("select  new map(id as id , name as name , beginDate as beginDate, endDate as endDate,idCard as idCard ,isOpen as isOpen) from Users where 1=1");
		if (!StringUtils.isBlank(para.getSName())) {
			para.setSName("%" + para.getSName() + "%");
			hqlBuf.append("  and name like:SName");
		}
		if (para.getSDate() != null) {
			hqlBuf.append(" and beginDate >:SDate");
		}
		Paging paging = um.paging(Paging.getCountForHQL(hqlBuf).toString(),
				hqlBuf.toString(), rows, page, para);
		Map map = new HashMap();
		map.put("records", paging.getTotalRow());// 记录总数
		map.put("total", paging.getTotalPage());// 总页数
		map.put("page", paging.getPage());// 当前页数
		map.put("rows", paging.getList());// 记录数据
		JsonConfig jc = new JsonConfig();
		jc.registerJsonValueProcessor(Timestamp.class, new DateProcessor());
		String reString = JSONObject.fromObject(map, jc).toString();
		pw.print(reString);
		return null;
	}

	/**
	 * @Title: initBinder
	 * @Description: from对象注入action时,定义绑定参数行为
	 * @param
	 * @param binder
	 * @param
	 * @param depart
	 * @return void
	 * @throws
	 * @author joye May 23, 2013
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, String depart) {
		logger.debug("MVCAction.initBinder()");
		// System.out.println("MVCAction.initBinder()");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sf, true));
	}

	@RequestMapping("/loadChildren.action")
	public void getTreeMenu(PrintWriter pw, String id,
			HttpServletRequest request) {

		String treeArgs = request.getParameter("postData");
		if (id.equals("root")) {
			if (treeArgs.equals("planManager")) {
				pw.write(JSONArray.fromObject(
						SessionContext.getAttribute(request,
								"planManagerRootMeunList")).toString());
			} else {
				pw.write(JSONArray.fromObject(
						SessionContext.getAttribute(request,
								"systemManagerRootMeunList")).toString());
			}
		} else {
			List authorityList = (List) SessionContext.getAttribute(request,
					"authorityList");

			List list = new ArrayList();
			SysResource sr = new SysResource();
			sr.setParent(id);
			for (SysResource s : sm.findByExample(sr)) {
				if (!authorityList.contains(s.getContent()))
					continue;
				Map map = new HashMap();
				map.put("title", s.getModuleCaption());
				map.put("url", s.getContent());
				map.put("id", s.getId());
				list.add(map);
			}
			pw.write(JSONArray.fromObject(list).toString());
		}
	}

	@RequestMapping("/getSelectBody.action")
	public String getSelectBody(PrintWriter pw, String id,
			HttpServletRequest request, HttpServletRequest response) {
		// System.out.println("MVCAction.getSelectBody()");

		List list = um.getAll();
		JsonConfig jc = new JsonConfig();
		jc.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("id") || arg1.equals("name")
						|| arg1.equals("list") || arg1.equals("success"))
					return false;
				else
					return true;
			}
		});
		Map map = new HashMap();
		map.put("success", true);
		map.put("list", list);
		String s = JSONObject.fromObject(map, jc).toString();
		pw.print(s);
		return null;

	}

	/**
	 * 處理request中的上傳文件,返回保存的文件名,多個文件時以 "," 分割
	 * 
	 * @Title: processFileUpload
	 * @Description:
	 * @param
	 * @param request
	 * @param
	 * @param FieldName
	 * @param
	 * @return
	 * @return String
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws
	 * @author joye Jun 4, 2015 9:08:51 PM
	 */ 
	@RequestMapping("/processFileUpload.action")
	public void processFileUpload(HttpServletRequest request,
			 PrintWriter pw) {

		Map<String, Object> map = new HashMap<String, Object>(); 
		String fileName = "";
		
		String path = request.getSession().getServletContext().getRealPath(
		"/WEB-INF/upload");
		
		
		try { 
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();
			
			MultipartHttpServletRequest mulitRequest = (MultipartHttpServletRequest) request;
			
			Enumeration  e =  request.getParameterNames(); 
			while (e.hasMoreElements()){
				Object o  = e.nextElement();
				System.out.println("o.toString()=" + o.toString());
			}
			
			Iterator it = mulitRequest.getFileNames();
			while (it.hasNext()){
				StringBuffer files = new StringBuffer(); 
				String fieldName = it.next().toString();
				List<MultipartFile> mFiles = mulitRequest.getFiles(fieldName);
				for (MultipartFile mFile : mFiles) {
					if (SunitStringUtil.isBlankOrNull(mFile.getOriginalFilename()))
						continue;  
					long fileSize = mFile.getSize()/1024;
					Long allowsSize= Long.valueOf(request.getSession().getServletContext().getAttribute(fieldName+"_size").toString());
					String  subDir= (String)request.getSession().getServletContext().getAttribute(fieldName+"_subDir");  
					if(allowsSize!=null && allowsSize>0 && allowsSize<fileSize){
						throw new Exception(mFile.getOriginalFilename()  +" 文件大小超出限制,不能超过"+allowsSize+"KB");
					}
					
					
					
					fileName = System.currentTimeMillis()+"_"
							+ mFile.getOriginalFilename();
					
					
					// 创建子目录 
					File f = null;
					if(SunitStringUtil.isBlankOrNull(subDir)){
						f  = new File(path + "/" + fileName); 
					}else{
						f  = new File(path + "/" + subDir+"/" + fileName);
					}
					
					if (!f.exists())
						f.mkdirs();
					
					mFile.transferTo(f);
					if (files.length() > 0)
						files.append(",");
					files.append(fileName); 
				}
				map.put("success", true);
				map.put(fieldName, files.toString() ); 
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", e.getMessage()); 
			logger.error("上传文件:", e);
		} 
		  pw.print(JSONObject.fromObject(map).toString()); 
	}

	@RequestMapping("/reConfigratrionLog4j.action")
	public String reConfigratrionLog4j(HttpServletRequest request) {

		PropertyConfigurator.configure(request.getRealPath("/")
				+ "WEB-INF\\classes\\log4j.properties");
		return null;
	}

	/**
	 * VO by 搜索
	 * 
	 * @author joye
	 * 
	 */
	public static class SearchPara {

		private String sName;
		private Date sDate;

		public String getSName() {
			return sName;
		}

		public void setSName(String name) {
			sName = name;
		}

		public Date getSDate() {
			return sDate;
		}

		public void setSDate(Date date) {
			sDate = date;
		}
	}
}
