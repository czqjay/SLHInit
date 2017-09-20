package com.sunit.global.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	public final static SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public final static SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static SimpleDateFormat LONG_FORMAT_MINUTES = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public final static SimpleDateFormat CN_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");

	public final static HashMap formatPool; 

	static {
		formatPool = new HashMap();
		formatPool.put("yyyy-MM-dd", SIMPLE_FORMAT);
		formatPool.put("yyyy-MM-dd HH:mm:ss", LONG_FORMAT);
	}

	private final static Log log = LogFactory.getLog(DateUtil.class);

	public static SimpleDateFormat getDateFormat(String format) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) formatPool.get(format);

		if (null == dateFormat) {
			synchronized (DateUtil.class) {
				if (null == formatPool.get(format)) {
					dateFormat = new SimpleDateFormat(format);
					formatPool.put(format, dateFormat);
				}
			}
		}

		return dateFormat;
	}

	public static Date parseDate(String format, String date) {
		SimpleDateFormat fmt = getDateFormat(format);
		Date dt = null;
		try {
			dt = fmt.parse(date);
		} catch (ParseException e) {
			log.error(DateUtil.class, e);
		}

		return dt;
	}

	public static Date parseDate(SimpleDateFormat format, String date) {
		Date dt = null;

		try {
			dt = format.parse(date);
		} catch (Exception e) {
			log.error(DateUtil.class, e);
		}

		return dt;
	}

	public static Date parseDate(String string) {
		Date date = null;

		if (null == string || 0 == string.length())
			return null;

		try {
			if (string.length() > 10)
				date = LONG_FORMAT.parse(string);
			else
				date = SIMPLE_FORMAT.parse(string);
		} catch (ParseException e) {
			log.error(DateUtil.class, e);
		}

		return date;
	}

	public static Date parseDate(Object object) {
		if (object instanceof Date)
			return (Date) object;

		if (object instanceof Calendar)
			return ((Calendar) object).getTime();

		return parseDate(object.toString());
	}

	public static Calendar parseCalendar(Object format, String string) {
		Calendar cal = null;
		Date date = null;

		if (format instanceof SimpleDateFormat)
			date = parseDate((SimpleDateFormat) format, string);
		else
			date = parseDate(format.toString(), string);

		if (null != date) {
			cal = Calendar.getInstance();
			cal.setTime(date);
		}

		return cal;
	}

	public static Calendar parseCalendar(String string) {
		Calendar cal = null;
		Date date = null;

		date = parseDate(string);

		if (null != date) {
			cal = Calendar.getInstance();
			cal.setTime(date);
		}

		return cal;
	}

	public static Calendar parseCalendar(Object object) {
		Calendar cal = Calendar.getInstance();

		if (object instanceof Date) {
			cal.setTime((Date) object);
			return cal;
		}

		if (object instanceof Calendar)
			return (Calendar) object;

		return parseCalendar(object.toString());
	}
	
	public static String getCurrentTime(){
		return LONG_FORMAT.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	* @Title: getTime 
	* @Description: 对字符串日期进行分钟加减操作，typeCode 为0表示加法。
	* @param @param date
	* @param @param minute
	* @param @param sft
	* @param @return     
	* @return String  
	 * @throws ParseException 
	* @throws 
	* @author sjz 
	* 2013-9-11 下午2:15:07
	 */
	public static String getOperationTime(String date,int minute, int typeCode,SimpleDateFormat sft ) throws ParseException{
		String descTime = "";
		Date srcDate = sft.parse( date );
		Calendar cTime = Calendar.getInstance();
		cTime.setTime( srcDate );
		if(typeCode==0){
			cTime.add( Calendar.MINUTE, minute);
		}else{
			cTime.add( Calendar.MINUTE, -minute);
		}
			
		Date descDate = cTime.getTime();
		descTime = sft.format( descDate );
		
		return descTime;
	}
	
}
