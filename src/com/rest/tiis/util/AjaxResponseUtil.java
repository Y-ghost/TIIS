package com.rest.tiis.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * 
 * 项目名称：BEMS
 * 类名称：AjaxResponseUtil   
 * 类描述：Response Util 用于封装AJAX异步获取JSON数据   
 * 创建人：Administrator
 * 创建时间：2013-9-9 下午07:13:08   
 * 修改人：Administrator   
 * 修改时间：2013-9-9 下午07:13:08   
 * 修改备注：
 */

public class AjaxResponseUtil {
	/**
	 * @Title: 				toJson 
	 * @author 				杨贵松
	 * @date 				2014年2月18日 下午3:22:23
	 * @Description: 		将对像直接转化成json对象，发送到前台 
	 * @param value 
	 * void 				返回
	 */
	public static void toJson(HttpServletResponse resp,Object value){
		Gson gson = new Gson();
		String json = gson.toJson(value);
		responseOut(resp, json);
	}
	
	/**
	 * @Title: 				toObject 
	 * @author 				杨贵松
	 * @date 				2014年2月18日 下午3:22:16
	 * @Description: 		将json转化成Object 
	 * @param value
	 * @param obj
	 * @return 
	 * Object 				返回
	 */
	public static Object toObject(String value,Class<?> obj){
		Gson gson = new Gson();
		Object object = gson.fromJson(value,obj);
		return object;
	}
	/**
	 * @Title: 				responseOut 
	 * @author 				杨贵松
	 * @date 				2014年2月18日 下午3:23:24
	 * @Description: 		返回前台打印
	 * @param resp
	 * @param value 
	 * void 				返回
	 */
   public static void responseOut(HttpServletResponse resp,Object value){
	   PrintWriter out = null;
		try {
			setContentTypeHtml(resp);
			out = resp.getWriter();
			if (out != null) {
				out.print(value);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
   }
   /**
    * @Title: 				setContentTypeHtml 
    * @author 				杨贵松
    * @date 				2014年2月18日 下午3:23:10
    * @Description: 		设置JSON模式及数据编码 
    * @param resp 
    * void 				返回
    */
   public static void setContentTypeHtml(HttpServletResponse resp){
	   resp.setCharacterEncoding("UTF-8");
	   resp.setContentType("text/json");
   }
}