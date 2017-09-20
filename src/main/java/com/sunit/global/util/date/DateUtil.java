package com.sunit.global.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sunit.global.util.SunitStringUtil;

public class DateUtil {

	private static final ThreadLocal<SimpleDateFormat> dateFormats = new ThreadLocal();

	private final static String MONTH_FORMAT = new String("yyyy-MM");

	private final static String SIMPLE_FORMAT = new String("yyyy-MM-dd");

	private final static String LONG_FORMAT = new String("yyyy-MM-dd HH:mm:ss");

	private final static String LONG_FORMAT_MINUTES = new String(
			"yyyy-MM-dd HH:mm");

	private final static String CN_FORMAT = new String("yyyy年MM月dd日");

	private final static String L8_DATE_FORMAT = new String("yyyyMMdd");

	private final static Log log = LogFactory.getLog(DateUtil.class);

	public static SimpleDateFormat getDateFormat(String format) {

		SimpleDateFormat sd = dateFormats.get();
		if (sd == null) {
			sd = new SimpleDateFormat(format); 
			dateFormats.set(sd);
		}
		sd.applyPattern(format);
		return sd;
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

	public static Date ifParse(String string) {

		Date date = null;

		if (SunitStringUtil.isBlankOrNull(string))
			return null;
		try {
			if (string.length() > 10)
				date = getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue())
						.parse(string);
			else if (string.length() > 7)
				date = getDateFormat(DateStyle.YYYY_MM_DD.getValue()).parse(
						string);
			else
				date = getDateFormat(DateStyle.YYYY_MM.getValue())
						.parse(string);
		} catch (ParseException e) {
			log.error(DateUtil.class, e);
		}
		return date;

	}

	public static String getCurrentTime() {
		return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(
				Calendar.getInstance().getTime());
	}

	public static String getCurrentDate() {
		return getDateFormat(DateStyle.YYYY_MM_DD.getValue()).format(
				Calendar.getInstance().getTime());
	}

	public static String getL8CurrentDate() {
		return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(
				Calendar.getInstance().getTime());
		// return L8_DATE_FORMAT.format(Calendar.getInstance().getTime());
	}

	public static SimpleDateFormat getLongFormat() {
		return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
	}

	public static SimpleDateFormat getSimpleFormat() {
		return getDateFormat(DateStyle.YYYY_MM_DD.getValue());
	}

	public static SimpleDateFormat getMonthFormat() {
		return getDateFormat(DateStyle.YYYY_MM.getValue());
	}

	public static SimpleDateFormat getMinuteFormat() { 
		return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM.getValue());
	}
	
	public static int subtractionDateMonth(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		int m = 0;
		try {
			m = (sdf.parse(endTime).getYear() - sdf.parse(startTime).getYear())
					* 12
					+ (sdf.parse(endTime).getMonth() - sdf.parse(startTime)
							.getMonth());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return m;
	}

	public static String addDateOneDay(String date, int day) {

		DateFormat df = new SimpleDateFormat("yyyy-MM");
		Date d2 = null;
		try {
			Date d1 = df.parse(date);
			Calendar g = Calendar.getInstance();
			g.setTime(d1);
			g.add(Calendar.MONTH, day);
			d2 = g.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(d2);
	}

}
