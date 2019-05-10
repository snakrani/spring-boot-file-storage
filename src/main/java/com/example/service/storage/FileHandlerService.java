package com.example.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.ResourceNotFoundException;

@Service
public class FileHandlerService implements FileStorageService {

	@Value("${storage.location.folder.name}")
	private String location;

	Logger logger = LoggerFactory.getLogger(FileHandlerService.class);
	
	@Override
	public Resource getFile(String filename) {
		logger.debug("FileHandlerService : getFileNames()");
		Path file = Paths.get(location, filename);
		Resource resource = null;
		try {
			resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				logger.error("FileHandlerService : File is Not Available.");
				throw new ResourceNotFoundException("File is not available or permission denied.");
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Path> getFiles() {
		logger.debug("FileHandlerService : getFiles()");
		List<Path> files = new ArrayList<>();
		try {
			Files.walk(Paths.get(location)).filter(Files::isRegularFile).forEach(path -> files.add(path));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return files;
	}
	
	@Override
	public List<String> getFileNames() {
		logger.debug("FileHandlerService : getFileNames()");
		List<String> fileNames = new ArrayList<>();
		try {
			getFiles().forEach(path -> fileNames.add(path.getFileName().toString()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return fileNames;
	}
	
	@Override
	public void store(MultipartFile file) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		logger.debug("FileHandlerService : store() " + fileName);
		
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, Paths.get(location + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to store file " + fileName, e);
		}
	}
}
