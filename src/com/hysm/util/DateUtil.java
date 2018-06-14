package com.hysm.util;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {
	
	public static Long getTime() {
		try {
			URL url=new URL("http://www.bjtime.cn");// 取得资源对象
			URLConnection uc=url.openConnection();//生成连接对象
			uc.connect();// 发出连接
			long ld=uc.getDate();// 取得网站日期时间
			return ld;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Timestamp getTimestamp() {
		try {
			URL url=new URL("http://www.bjtime.cn");// 取得资源对象
			URLConnection uc=url.openConnection();//生成连接对象
			uc.connect();// 发出连接
			long ld=uc.getDate();// 取得网站日期时间
			 Timestamp ts = new Timestamp(ld);
			
			return ts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getFormatCN(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "yyyy年MM月dd日 EEEE kk:mm:ss";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	public static String getFormatTimes(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "yyyy-MM-dd kk:mm:ss";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	public static String chaDateTime(int num){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long timeStart=0;
		try {
			timeStart = System.currentTimeMillis();
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		long chatime=timeStart+3600*24*1000*num;
		return sdf.format(chatime);
		
	}
	
	public static String getFormatEN(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "yyyy/MM/dd kk:mm:ss";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	public  static String queryNow(){
		
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "yyyyMMddkkmmss";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(System.currentTimeMillis()); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
	public static String getFormatDate(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "MM-dd";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	
	public static String getFormatTime(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "kk:mm";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	public static String getFormatEn(long ld) {
		try {
			Locale locale=Locale.CHINA;//获得本地中国时区
			String pattern = "yyyy-MM-dd";//日期格式
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					locale);// 设定日期格式
			Date date=new Date();
			date = new Date(ld); // 转换为标准时间对象
			String bjTime=sdf.format(date);
			return bjTime;
		} catch (Exception e) {
			 
		}
		return null;
	}
	
	public static String[] getLastMonth() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int totle = year * 12 + month;
		int lastMonth = (totle - 1) % 12;
		int lastYear = (totle - lastMonth) / 12;
		String[] rs = new String[2];
		rs[0] = lastYear + "";
		rs[1] = lastMonth + "";
		return rs;

	}

	public static Integer[] getLastMonthInt() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int totle = year * 12 + month;
		int lastMonth = (totle - 1) % 12;
		int lastYear = (totle - lastMonth) / 12;
		Integer[] rs = new Integer[2];
		rs[0] = lastYear;
		rs[1] = lastMonth;
		return rs;

	}

	@SuppressWarnings("static-access")
	public static Date[] getMonthHeadAndEnd(String date) {
		String[] ym = date.split("-");
		int year = Integer.valueOf(ym[0]);
		int month = Integer.valueOf(ym[1]) - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		Date d1 = c.getTime();
		int end = c.getActualMaximum(c.DAY_OF_MONTH);
		c.set(Calendar.DATE, end);
		Date d2 = c.getTime();
		Date[] rd = new Date[2];
		rd[0] = d1;
		rd[1] = d2;
		return rd;

	}

	public static Date getStartDate(String date) {

		String[] ym = date.split("-");
		int year = Integer.valueOf(ym[0]);
		int month = Integer.valueOf(ym[1]) - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d1 = c.getTime();
		return d1;
	}

	// 获取三天后日期
	public static String  threedate(){
		 java.util.Calendar rightNow = java.util.Calendar.getInstance();
	        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd");
	        //得到当前时间，+3天
	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, 3);   
	        //如果是后退几天，就写 -天数 例如：
//	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
	        //进行时间转换
	        String date = sim.format(rightNow.getTime()); 
	     
	        return date;
		
	}
	public static String  nowdate(){
		 java.util.Calendar rightNow = java.util.Calendar.getInstance();
	        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd");
	        //得到当前时间，+3天
	       /* rightNow.add(java.util.Calendar.DAY_OF_MONTH, 3);  */ 
	        //如果是后退几天，就写 -天数 例如：
//	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
	        //进行时间转换
	        String date = sim.format(rightNow.getTime()); 
	     
	        return date;
		
	}
	public static String  nowdate(Date date){
	        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd");
	        //得到当前时间，+3天
	       /* rightNow.add(java.util.Calendar.DAY_OF_MONTH, 3);  */ 
	        //如果是后退几天，就写 -天数 例如：
//	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
	        //进行时间转换
	      return sim.format(date.getTime()); 
	     
		
	}
	public static String  nowdate(int days){
		 java.util.Calendar rightNow = java.util.Calendar.getInstance();
	        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM-dd");
	        //得到当前时间，+n天
	        rightNow.add(java.util.Calendar.DAY_OF_MONTH,days);
	        //如果是后退几天，就写 -天数 例如：
//	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
	        //进行时间转换
	        String date = sim.format(rightNow.getTime()); 
	     
	        return date;
		
	}
	
	public static String nowMonth(int i) {
		 java.util.Calendar rightNow = java.util.Calendar.getInstance();
	        java.text.SimpleDateFormat sim = new java.text.SimpleDateFormat("yyyy-MM");
	        //得到当前时间，+n天
	        rightNow.add(java.util.Calendar.MONTH,i);
	        //如果是后退几天，就写 -天数 例如：
//	        rightNow.add(java.util.Calendar.DAY_OF_MONTH, -3);
	        //进行时间转换
	        String date = sim.format(rightNow.getTime()); 
	     
	        return date;
	}
	@SuppressWarnings("static-access")
	public static Date getEndDate(String date) {

		String[] ym = date.split("-");
		int year = Integer.valueOf(ym[0]);
		int month = Integer.valueOf(ym[1]) - 1;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		int end = c.getActualMaximum(c.DAY_OF_MONTH);
		c.set(Calendar.DATE, end);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date d1 = c.getTime();
		return d1;
	}
	public static double getRound(double dSource){
	    double iRound;
	    //BigDecimal的构造函数参数类型是double
	    BigDecimal deSource = new BigDecimal(dSource);
	    //deSource.setScale(0,BigDecimal.ROUND_HALF_UP) 返回值类型 BigDecimal
	    //intValue() 方法将BigDecimal转化为int
	    iRound= deSource.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
	    return iRound;
	}
	public static int getCurrentMonth(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH+1);
		
	}

	public static int getCurrentDate() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DATE);
	}

	public static int getcurrentYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	public static long  getLocalTimeByDate(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return Long.parseLong(simpleDateFormat.format(new Date()));
	}
	public static long  getLocalByDate(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
		return Long.parseLong(simpleDateFormat.format(new Date()));
	}
	public static long  getLocalByDate2(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmm");
		return Long.parseLong(simpleDateFormat.format(new Date()));
	}
	public static long  getLocalByDate22(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddHHmm");
		return Long.parseLong(simpleDateFormat.format(new Date()));
	}
	public static String  getLocalByDate3(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmm");
		return simpleDateFormat.format(new Date());
	}
	public static String  getLocalByDate4(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		return simpleDateFormat.format(new Date());
	}
	public static long  getStringByDate(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmm");
		return Long.parseLong(simpleDateFormat.format(new Date()));
	}
	
	
	public static Date  getLocalDateByTime(long time){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=null;
		try {
			date = simpleDateFormat.parse(Long.toString(time));
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDatebySystem(){
		return DateUtil.getcurrentYear()+"_"+DateUtil.getFormatDate(System.currentTimeMillis());
	}
	public static Timestamp getTimestampByststem() {
		try {
			
			
			
			 Timestamp ts = new Timestamp(System.currentTimeMillis());
			
			return ts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date StringToDate(String date) {
		
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 long timeStart=0;
		try {
			timeStart = sdf.parse(date).getTime();
		} catch (ParseException e) {
			 
			e.printStackTrace();
			 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			 try {
				timeStart = sdf2.parse(date).getTime();
			} catch (ParseException e1) {
				 
				e1.printStackTrace();
			}
		}
		 return new Date(timeStart);
	}

	public static boolean bijiaoToday(String date){
		

		Date da=null;
		try{
		 da=StringToDate(date);
		}catch (Exception e) {
			
		}
		long now=System.currentTimeMillis();
		long cha=da.getTime()-now;
		if(cha>0){
			
			return true;
		}
		
		return false;
				
	}
	/**
	 * 比较日期
	 * 大等于今天返回true
	 * @param date
	 * @return
	 */
public static boolean bijiaoDays(String date){
		

	
		long da=StringToLong(date);
		long now=StringToLong(nowdate());
		long cha=da-now;
		if(cha>=0){
			
			return true;
		}
		
		return false;
				
	}
	
	public static long StringToLong(String date){
		
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 long timeStart=0;
		try {
			timeStart = sdf.parse(date).getTime();
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
		 return timeStart;
	}
	public static String chaDate(int num,String date){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 long timeStart=0;
		try {
			timeStart = sdf.parse(date).getTime();
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}
		long chatime=timeStart-3600*24*1000*num;
		return DateUtil.getFormatEn(chatime);
		
	}
	
	public static void main(String[] args) {
		/*System.out.println(DateUtil.StringToDate("2016-03-01 00:00:00"));
		try {
			int ss=DateUtil.daysBetween(DateUtil.StringToDate("2016-03-01 00:00:00"), new Date(System.currentTimeMillis()));
		System.out.println(ss);
		} catch (ParseException e) {
			 
			e.printStackTrace();
		}*/
//		System.out.println(chaDateTime(20));
//		System.out.println(getNowMonthOneDay());
//		System.out.println(DateUtil.chaDate(2,"2016-03-12"));
//		System.out.println(threedate());
//		System.out.println(getLocalByDate22());
//		System.out.println(nowdate(10));
//		System.out.println(bijiaoToday("2016-12-14"));
//		System.out.println(getDateByString("2017-7-31 16:00:00").after(new Date()));
		//System.out.println(DateUtil.nowdate(0));
//		System.out.println(DateUtil.nowMonth(-1));
//		System.out.println(DateUtil.nowdate(new Date()));
		
//		System.out.println(DateUtil.bijiaoDays("2017-12-31"));
//		System.out.println(getCurrentDate());
//		System.out.println(nowMonth(-1)+"-01 00:00:00");
	}

	 /**
     * 返回当前时间 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return String
     */
	
    public static String fromDate24H()
    {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        return sdformat.format(new Date());
    }
    public static String fromDate24HStr()
    {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmm");// 24小时制
        return sdformat.format(new Date());
    }
    public static String fromDate24H(long l)
    {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        return sdformat.format(l);
    }
    
    /**
	 * 返回当前时间 格式：yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String fromDateY() {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date());
	}
	
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)    
    {    
    	try{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));  
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return 0;
    }

	public static Date getDateByString(String string) {
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = null;
		 try {
			 date = df.parse(string);
		} catch (ParseException e){ 
			e.printStackTrace();
		}
		 
		return date;

	} 
	
	public static Date getNowMonthOneDay(){
		
		Calendar c = Calendar.getInstance();    
		         c.add(Calendar.MONTH, 0);
		        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		        c.set(Calendar.HOUR_OF_DAY,0);
		        c.set(Calendar.MINUTE,0);
		        c.set(Calendar.SECOND,0);
		        Date date = null;
				 try {
					 date = c.getTime();
				} catch (Exception e){ 
					e.printStackTrace();
				}
				 
				return date;
	}

	
	
    
    
}
