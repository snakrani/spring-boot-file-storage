package com.example.service.storage;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	  
	void store(MultipartFile file);

	Resource getFile(String filename);
	
	List<String> getFileNames();
	
}
