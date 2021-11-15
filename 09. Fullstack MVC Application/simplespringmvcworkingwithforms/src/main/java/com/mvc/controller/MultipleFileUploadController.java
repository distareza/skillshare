package com.mvc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MultipleFileUploadController implements ServletContextAware {

	private static final String UPLOAD_DIRECTORY = "/uploaded_items/";	
	private ServletContext servletContext;			
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.GET)
	public ModelAndView uploadMultipleFiles() {
		return new ModelAndView("uploadMultipleFiles");
	}	
	
	@RequestMapping(value = "/uploadMultipleFiles", method = RequestMethod.POST)	
	public @ResponseBody String uploadMultpleFilesHandler(
				@RequestParam("name") String[] fileNames,
				@RequestParam("file") MultipartFile[] files
			) {
		if (files.length != fileNames.length)
			return "Required information missing";
		String message = "";
		for (int i=0; i<files.length; i++) {
			MultipartFile file = files[i];
			String filename = fileNames[i];
			try {
				byte[] bytes = file.getBytes();
				String pathToFile = servletContext.getRealPath("/") + UPLOAD_DIRECTORY + filename;
				BufferedOutputStream stream = 
						new BufferedOutputStream(new FileOutputStream(new File(pathToFile)));
				
				stream.write(bytes);
				stream.flush();
				stream.close();

				message += String.format("file %s is successfully uploaded<br/>", filename);
			} catch (Exception ex) {
				return String.format("Failed to upload file %s, %s", filename, ex.getMessage());
			}
		}		
		return message;		
	}
}

