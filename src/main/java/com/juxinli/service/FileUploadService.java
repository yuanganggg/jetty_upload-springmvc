package com.juxinli.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/fileupload")
public class FileUploadService {
	
	private Logger logger = Logger.getLogger(FileUploadService.class);
	
	@RequestMapping(consumes = "multipart/form-data", value = "/hello", method = RequestMethod.GET)
	public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.getWriter().write("Hello, jetty server start ok.");
	}
	
	@RequestMapping(consumes = "multipart/form-data", value = "/upload", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "";
		
		if (request.getContentLength() > 0) {
			
	           InputStream inputStream = null;
	           FileOutputStream outputStream = null;
	           
			try {
				inputStream = request.getInputStream();
				// 给新文件拼上时间毫秒，防止重名
				long now = System.currentTimeMillis();
				File file = new File("c:/", "file-" + now + ".txt");
				file.createNewFile();
				
				outputStream = new FileOutputStream(file);
				
				byte temp[] = new byte[1024];
				int size = -1;
				while ((size = inputStream.read(temp)) != -1) { // 每次读取1KB，直至读完
					outputStream.write(temp, 0, size);
				}
				
				logger.info("File load success.");
				result = "File load success.";
			} catch (IOException e) {
				logger.warn("File load fail.", e);
				result = "File load fail.";
			} finally {
				outputStream.close();
				inputStream.close();
			}
		}
		
		response.getWriter().write(result);
	}

}
