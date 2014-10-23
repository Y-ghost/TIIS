package com.rest.tiis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @project:					TIIS 
 * @Title: 						CommonUtiles.java 		
 * @Package 					com.rest.tiis.util		
 * @Description: 				通用公共方法封装类 
 * @author 						杨贵松   
 * @date 						2014年1月22日 下午11:56:34 
 * @version 					V1.0
 */
public class CommonUtiles {

	/**
	 * @Title: 				getSystemDateTime 
	 * @author 				杨贵松
	 * @date 				2014年1月22日 下午11:53:47
	 * @Description: 		获得系统时间
	 * @return 
	 * Date 				返回
	 * @throws ParseException 
	 */
	public static Date getSystemDateTime() throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time= sdf.parse(sdf.format(new Date()));
		return time;
	}
	
	/**
	 * @Title: 				getLastOneSecondDate 
	 * @author 				杨贵松
	 * @date 				2014年3月26日 上午10:59:56
	 * @Description: 		获取比当前时间早10秒的时间
	 * @return
	 * @throws ParseException 
	 * Date 				返回
	 */
	public static Date getLastOneSecondDate() throws ParseException{
		long t = System.currentTimeMillis();
		Date date = new Date(t + 10*1000);
		return date;
	}
	
	/**
	 * @Title: 				getLastTwoSecondDate 
	 * @author 				杨贵松
	 * @date 				2014年4月22日 下午3:47:45
	 * @Description: 		获取比当前时间早30秒的时间
	 * @return
	 * @throws ParseException 
	 * Date 				返回
	 */
	public static Date getLastTwoSecondDate() throws ParseException{
		long t = System.currentTimeMillis();
		Date date = new Date(t + 30*1000);
		return date;
	}
}
