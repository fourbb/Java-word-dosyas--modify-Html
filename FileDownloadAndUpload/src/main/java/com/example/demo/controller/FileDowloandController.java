package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileDowloandController {
	String folderPath="C:\\Users\\berke\\AppData\\Local\\Temp\\SofttechDowloand\\";
	
	@GetMapping("/Download")
	public String showFiles(Model model) {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		model.addAttribute("files", listOfFiles);
		return "showFiles";
	}
	@RequestMapping("/file/{fileName}")
	@ResponseBody
	public void show(@PathVariable("fileName") String fileName, HttpServletResponse response) {

	      if (fileName.indexOf(".doc")>-1) response.setContentType("application/msword");
	      if (fileName.indexOf(".docx")>-1) response.setContentType("application/msword");
	      response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
	      response.setHeader("Content-Transfer-Encoding", "binary");
	      try {
	    	  BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	    	  FileInputStream fis = new FileInputStream(folderPath+fileName);
	    	  int len;
	    	  byte[] buf = new byte[1024];
	    	  while((len = fis.read(buf)) > 0) {
	    		  bos.write(buf,0,len);
	    	  }
	    	  bos.close();
	    	  response.flushBuffer();
	      }
	      catch(IOException e) {
	    	  e.printStackTrace();
	    	  
	      }
	}

}