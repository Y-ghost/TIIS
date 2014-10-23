package com.rest.tiis.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @project:					TIIS 
 * @Title: 						FileUploadController.java 		
 * @Package 					com.rest.tiis.controller		
 * @Description: 				文件上传下载
 * @author 						杨贵松   
 * @date 						2014年5月10日 下午4:29:43 
 * @version 					V1.0
 */
@Controller
@RequestMapping("Files")
public class FileUploadController {
	private static final Logger log = Logger.getLogger(FileUploadController.class.getName());
	
	/**
	 * @Title: 				uploadApk 
	 * @author 				杨贵松
	 * @date 				2014年5月10日 下午4:38:51
	 * @Description: 		文件上传
	 * @param request
	 * @param response
	 * @return 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String uploadApk(HttpServletRequest request){
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");

			// 获得文件名：
			String realFileName = file.getOriginalFilename();
			// 获取路径
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "download/";
			// 创建文件
			File dirPath = new File(ctxPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			File uploadFile = new File(ctxPath + realFileName);
			log.info("文件路径："+ctxPath + realFileName);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
			return "redirect:/Files/success";
		} catch (IOException e) {
			log.info("上传文件异常！"+e);
			return "redirect:/error";
		}
	}

	/**
	 * @Title: 				loadFiles 
	 * @author 				杨贵松
	 * @date 				2014年5月12日 下午5:02:56
	 * @Description: 		获取文件列表
	 * @param request
	 * @return 
	 * List<String> 				返回
	 */
	@RequestMapping(value="fileList",method=RequestMethod.GET)
	public ModelAndView loadFiles(HttpServletRequest request) {
		List<String> files = new ArrayList<String>();
		ModelAndView model = new ModelAndView();
		try {
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "download/";
			File file = new File(ctxPath);
			if (file.exists()) {
				File[] fs = file.listFiles();
				String fname = null;
				for (File f : fs) {
					fname = f.getName();
					if (f.isFile()) {
						files.add(fname);
					}
				}
			}
			model.addObject(files);
		} catch (Exception e) {
			model.addObject("error");
		}
		return model;
	}

	/**
	 * @Title: 				download 
	 * @author 				杨贵松
	 * @date 				2014年5月10日 下午4:38:38
	 * @Description: 		文件下载 
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * ModelAndView 				返回
	 */
	@RequestMapping(value="download",method=RequestMethod.GET)
	public ModelAndView download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		fileName = fileName + ".apk";
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "download/";
			String downLoadPath = ctxPath + fileName;
			log.info("下载文件路劲！"+downLoadPath);
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			log.info("下载文件异常！"+e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
	
	@RequestMapping(value="success",method=RequestMethod.GET)
	public String success(){
		return "ok";
	}
}
