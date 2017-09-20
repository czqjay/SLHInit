﻿package com.sunit.sysmanager.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunit.global.base.dao.BaseDAO;
import com.sunit.global.base.service.AbstractBaseServiceImpl;
import com.sunit.sysmanager.po.SysResource;
import com.sunit.sysmanager.service.SysResourceManager;


/**
 * 
 * 
 * 类名称：SysResourceManagerImpl
 * 类描述：实现资源管理接口
 * 创建人：liangrujian
 * 创建时间：Jul 24, 2013 10:30:27 AM
 * 修改人：liangrujian
 * 修改时间：Jul 24, 2013 10:30:27 AM
 * 修改备注：
 * @version 
 *
 */
@Service 
public class SysResourceManagerImpl extends AbstractBaseServiceImpl<SysResource>  implements SysResourceManager   {

	@Autowired 
	BaseDAO dao; 
	
	protected BaseDAO getDao() {
		dao.getHibernateTemplate().setCacheQueries(true); 
		return dao;
	}  
	
	public List<SysResource> isExtists(SysResource entity) {
		return this.isExtists(entity,true);
	}

	public List<SysResource> isExtists(SysResource entity, boolean excludeSelf) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	

}

