package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.MetaData;
import com.example.service.metadata.MetaDataService;
import com.example.service.storage.StorageService;

@RestController
@RequestMapping("/file")
public class FileConroller {

	@Resource(name = "fileHandlerService")
	private StorageService storageService;

	@Resource
	private MetaDataService metaDataService;

	Logger logger = LoggerFactory.getLogger(FileConroller.class);

	@GetMapping()
	public ResponseEntity<List<String>> getFileNames() {
		
		logger.debug("FileConroller : getFileNames()");
		return ResponseEntity.ok().body(storageService.getFileNames());
	}

	@GetMapping("/{filename:.+}")
	public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable String filename) {
		
		logger.debug("FileConroller : getFile() with file Name " + filename);
		org.springframework.core.io.Resource file = storageService.getFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping
	public ResponseEntity<String> storeFile(@RequestParam("file") MultipartFile file) {

		logger.debug("FileConroller: storeFile() with file Name " + file.getName());
		storageService.store(file);
		storeMetaData(file);
		return new ResponseEntity<String>("File is created.", HttpStatus.CREATED);
	}

	private void storeMetaData(MultipartFile file) {
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		metaDataService.store(new MetaData(filename, LocalDateTime.now().toString()));
	}

}
