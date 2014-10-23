package com.rest.tiis.filter;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
/**
 * @project:					TIIS 
 * @Title: 						MappingJacksonJsonViewExt.java 		
 * @Package 					com.rest.tiis.filter		
 * @Description: 				重写MappingJacksonJsonView中的fileterModel方法，实现去除返回前台的json对象前的对象名
 * @author 						杨贵松   
 * @date 						2014年2月18日 下午4:04:45 
 * @version 					V1.0
 */
public class MappingJacksonJsonViewExt extends MappingJacksonJsonView{
	protected Object filterModel(Map<String, Object> model) {
		  Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
		  if (result.size() == 1) {
		    return result.values().iterator().next();
		  }else{
		    return result;
		  }
		}
}
