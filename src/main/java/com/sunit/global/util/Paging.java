package com.sunit.global.util;

import java.util.List;

public class Paging {
	
	//每一页的行数 
	private int rows; 
	//当前页
	private int page;
	//数据库数据总行数
	private long totalRow ; 
	//数据总页数
	private long totalPage;
	//展示数据
	private List List;
	
	public List getList() {
		return List;
	}
	public void setList(List list) {
		List = list;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;   
	}
	public void setPage(int l) { 
		this.page = l;
	}
	public long getTotalRow() {
		return totalRow; 
	}
	public void setTotalRow(long totalRow) {
		this.totalRow = totalRow;
	}
	public long getTotalPage() { 
		return totalPage;
	}
	public void setTotalPage(long totalPage) { 
		this.totalPage = totalPage; 
	} 
	
	/**
	 * 
	* @Title: getCountForHQL 
	* @Description:  为 hql 输出相匹配的 count 语句， 通常情况下用于分页时获取记录总数 
	* @param @param hqlBuf
	* @param @return     
	* @return StringBuffer  
	* @throws  
	* @author joye 
	* Jun 2, 2013
	 */
	public static StringBuffer  getCountForHQL(StringBuffer hqlBuf){
		int i= hqlBuf.indexOf("from ");
		StringBuffer countBuf =new StringBuffer(hqlBuf); 
		return    countBuf.delete(0, i).insert(0, "select count(*) "); 
	}
	
	
	/**
	 * 为 hql 输出相匹配的 count 语句(不计算重复行)， 通常情况下用于分页时获取记录总数 
	* @Title: getCountForHQLByDistinct 
	* @Description: 为 hql 输出相匹配的 count 语句(不计算重复行)， 通常情况下用于分页时获取记录总数 
	* @param @param hqlBuf  
	* @param @param distinctCol  需要distinct的列名 
	* @param @return     
	* @return StringBuffer  
	* @throws 
	* @author joye 
	* Sep 27, 2013 2:40:23 PM
	 */
	public static StringBuffer  getCountForHQLByDistinct(StringBuffer hqlBuf,String distinctCol){ 
		int i= hqlBuf.indexOf("from ");
		StringBuffer countBuf =new StringBuffer(hqlBuf); 
		return    countBuf.delete(0, i).insert(0, "select count(distinct "+distinctCol+") ");   
	}
}
