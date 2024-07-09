package com.bikram.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bikram.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	// upload image : here I'm writing the logic for uploading the image in the folder named 'images'
	@Override
	public String uploadImage(MultipartFile imageFile, String folderPath) throws IOException {

		// original file name (let 'abc.png')
		String imageFileName = imageFile.getOriginalFilename();
		
		// generating random name ('xyz')
		String randomName = UUID.randomUUID().toString();
		
		// new imageFileName ('xyz' + '.png' = 'xyz.png')
		String newImageFileName = randomName.concat(imageFileName.substring(imageFileName.lastIndexOf("."))); 
		
		// complete filePath
		String completeFilePath = folderPath + File.separator + newImageFileName;
		
		// creating 'folder object' at the folderPath
		File f = new File(folderPath);
		
		// creating the folder if already not exists
		if (!f.exists()) {
			f.mkdir(); // creating folder at the given folderPath
		}
		
		// storing the file into the 'completeFilePath' : the new file name comes from the 'completeFilePath'
		Files.copy(imageFile.getInputStream(), Paths.get(completeFilePath));
		
		// retun new imageFileName
		return newImageFileName;
	}

	
	
	
	// download image : serve the image
	@Override
	public InputStream downloadImage(String folderPath, String imageFileName) throws FileNotFoundException {
		
		// complete filePath
		String completeFilePath = folderPath + File.separator + imageFileName;
		
		// getting the imageFile and converting into FileInputStream
		InputStream is = new FileInputStream(completeFilePath);
		
		// return the fileInputStream
		return is;
		
		
	}

}
