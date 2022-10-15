package com.example.demo.controller;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@GetMapping("/Upload")
	public String hello() {
		return "uploader";
	}
	@PostMapping("/upload")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
		String extension = null;
		String extension2="docx";
		String filename=file.getOriginalFilename();
		int index=filename.lastIndexOf(".");
		if(index>0) {
			 extension= filename.substring(index+1);
		}
		if(extension.equals(extension2)) {
			try {
				file.transferTo(new File("C:\\Users\\berke\\AppData\\Local\\Temp\\softtechdeneme\\"+filename));
			}catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		
		return ResponseEntity.ok(" ");
		
	}
}
