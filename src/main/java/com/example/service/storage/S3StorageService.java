package com.example.service.storage;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class S3StorageService implements StorageService {

	@Override
	public void store(MultipartFile file) {
	}

	@Override
	public Resource getFile(String key) {
		return null;
	}

	@Override
	public List<String> getFileNames() {
		return null;
	}

}
