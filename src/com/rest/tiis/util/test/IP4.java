/**
 * 
 */
package com.rest.tiis.util.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @project:					TIIS 
 * @Title: 						IP4.java 		
 * @Package 					com.rest.tiis.util.test		
 * @Description: 				获得客户端IP和port
 * @author 						杨贵松   
 * @date 						2014年2月10日 上午1:05:39 
 * @version 					V1.0
 */
@Controller
@RequestMapping("index")
public class IP4 {
	@RequestMapping("ip.json")
	public ModelAndView getIP4(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr() + ":" + request.getRemotePort();
		}
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("ip", ip);
//		TimeClient.connect();
		return modelView;
	}
}
