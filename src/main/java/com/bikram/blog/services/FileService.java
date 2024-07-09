package com.bikram.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	 String uploadImage(MultipartFile imageFile, String folderPath) throws IOException;
	
	 InputStream downloadImage(String folderPath, String imageFileName) throws FileNotFoundException;
	
}
