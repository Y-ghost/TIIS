package com.rest.tiis.filter;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * @project:					TIIS 
 * @Title: 						SpringObjectFactoryHolder.java 		
 * @Package 					com.rest.tiis.filter		
 * @Description: 				手动获取Spring上下文，便于未注入的依赖，手动注入
 * @author 						杨贵松   
 * @date 						2014年3月7日 下午7:25:47 
 * @version 					V1.0
 */
public class SpringObjectFactoryHolder {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {

		if (null == SpringObjectFactoryHolder.context) {
			SpringObjectFactoryHolder.context = ContextLoader.getCurrentWebApplicationContext();
		}
		if (null == SpringObjectFactoryHolder.context) {
			SpringObjectFactoryHolder.context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringObjectFactoryHolder.context = context;
	}
}