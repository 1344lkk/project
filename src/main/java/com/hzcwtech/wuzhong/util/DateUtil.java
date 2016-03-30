package com.hzcwtech.wuzhong.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName DateUtil 
 * @Description 日期格式公共类
 * @author ieastar
 * @date 2013-1-24
 */
public class DateUtil
{
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
	public static final String yyyy__MM__dd = "yyyy/MM/dd";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM = "yyyy-MM";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String HH_mm = "HH:mm";
	public static final String HHmm = "HHmm";
	

	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static String format(Date date, String pattern)
	{
		if (date == null) {
			return "";
		}
		if (StringUtil.isTrimBlank(pattern)) {
			pattern = yyyy_MM_dd_HH_mm_ss;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String format(String d,String pattern){
		Date date = DateUtil.getCurrentDate();
		if(StringUtil.isNotTrimBlank(d)){
			date = parse(d, pattern);
		}
		if (StringUtil.isTrimBlank(pattern)) {
			pattern = yyyy_MM_dd_HH_mm_ss;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date parse(String str,String pattern){
		if (StringUtil.isTrimBlank(str)) {
			return DateUtil.getCurrentDate();
		}
		if (StringUtil.isTrimBlank(pattern)) {
			pattern = yyyy_MM_dd_HH_mm_ss;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = DateUtil.getCurrentDate();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			date = DateUtil.getCurrentDate();
		}
		return date;
	}

	public static Date shortDate(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);
		c.set(14, 0);
		return c.getTime();
	}

	/**
	 * 指定日期加
	 * @param date 指定日期
	 * @param field 所加项：年、月、日
	 * @param amount 加几
	 * @return
	 */
	public static Date add(Date date, int field, int amount)
	{
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(field, amount);
		return c.getTime();
	}
	
	public static String add(String date,int field,int amount,String pattern){
		Calendar c = Calendar.getInstance();
		if(StringUtil.isTrimBlank(pattern)){
			pattern = yyyy_MM_dd_HH_mm_ss;
		}
		c.setTime(parse(date,pattern));
		c.add(field, amount);
		return format(c.getTime(),pattern);
	}
	
	public static int monthSpance(String startDate,String endDate){
		int i = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		try {
            Calendar s = Calendar.getInstance();
    		Calendar e = Calendar.getInstance();
    		s.setTime(df.parse(startDate));
    		e.setTime(df.parse(endDate));
    		i = (e.get(Calendar.YEAR)-s.get(Calendar.YEAR))*12+(e.get(Calendar.MONTH)-s.get(Calendar.MONTH));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static int dateSpance(String startDate,String endDate){
		int i = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
    		i = (int) ((df.parse(endDate).getTime() - df.parse(startDate).getTime())/(24*60*60*1000));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static int minute(Date date){
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.MINUTE);
	}
	
	public static int hour(Date date){
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int date(Date date)
	{
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(5);
	}

	public static int month(Date date)
	{
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(2);
	}

	public static int year(Date date)
	{
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(1);
	}

	public static int maxDate(Date date)
	{
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(2, 1);
		c.set(5, 1);
		c.add(5, -1);
		return c.get(5);
	}
	
	/**
	 * 得到前day天的日期
	 * @param day 前几天
	 * @return
	 */
	public static String getDayDateString(int day){
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)-day);
		String d = c.get(Calendar.YEAR)+"-"+String.format("%1$02d",(c.get(Calendar.MONTH)+1))+"-"+String.format("%1$02d",c.get(Calendar.DATE));
	    return d;
	}
	
	/**
	 * 得到前day天的日期
	 * @param day 前几天
	 * @return
	 */
	public static String getDayDateString(String date,int day){
		Calendar c = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		e.setTime(parse(date,yyyy_MM_dd));
		c.set(e.get(Calendar.YEAR),e.get(Calendar.MONTH),e.get(Calendar.DATE)-day);
		String d = c.get(Calendar.YEAR)+"-"+String.format("%1$02d",(c.get(Calendar.MONTH)+1))+"-"+String.format("%1$02d",c.get(Calendar.DATE));
	    return d;
	}
	
	public static String getCurrentMonthDate(){
		Calendar c = Calendar.getInstance();
		String d = c.get(Calendar.YEAR)+"-"+String.format("%1$02d",(c.get(Calendar.MONTH)+1));
		return d;
	}
	
	public static int compareDate(String date1,String date2){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
            	System.out.println("dt1在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
            	System.out.println("dt1 在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
	}
	
	/**
	 * 判断第一个日期是否在后两个日期之间
	 * @param d 需判断日期
	 * @param date1 开始日期
	 * @param date2 结束日期
	 * @return
	 */
	public static boolean compareDate(String d,String date1,String date2,String parten){
		if(StringUtil.isTrimBlank(parten)){
			parten = "yyyy-MM-dd";
		}
		DateFormat df = new SimpleDateFormat(parten);
		boolean flag = false;
        try {
        	Date dt0 = df.parse(d);
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if((dt1.getTime()<=dt0.getTime())&&dt0.getTime()<=dt2.getTime()){
            	flag = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
	}
}