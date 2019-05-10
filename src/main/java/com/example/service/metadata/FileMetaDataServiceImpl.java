package com.example.service.metadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.domain.MetaData;
import com.example.service.storage.FileHandlerService;
import com.example.service.storage.FileStorageService;

@Service
public class FileMetaDataServiceImpl implements MetaDataService {

	@Resource(name = "fileHandlerService") 
	FileStorageService fileStorageService;
	
	Map<String, MetaData> metaDataStore = new HashMap<>();
	
	Logger logger = LoggerFactory.getLogger(FileHandlerService.class);
	
	/**
	 * Stores file meta data on cache on server start.
	 */
	@PostConstruct
	private void init() {
		logger.debug("FileMetaDataServiceImpl : init()");
		this.populateMetaData();
	}

	@Override
	public MetaData get(String fileName) {
		logger.debug("FileMetaDataServiceImpl : get()");
		return metaDataStore.get(fileName);
	}

	@Override
	public void store(MetaData metaData) {
		logger.debug("FileMetaDataServiceImpl : store()");
		metaDataStore.put(metaData.getFileName(), metaData);
	}

	@Override
	public List<MetaData> getAll() {
		logger.debug("FileMetaDataServiceImpl : getAll()");
		return new ArrayList<MetaData>(metaDataStore.values());
	}

	private void populateMetaData() {
		logger.debug("FileMetaDataServiceImpl : populateMetaData()");
		try {
			for (Path path : fileStorageService.getFiles()) {
				BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
				this.store(new MetaData(path.getFileName().toString(), 
						millsToLocalDateTimeString(attr.creationTime().toMillis())));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	public String millsToLocalDateTimeString(long millis) {
	      Instant instant = Instant.ofEpochMilli(millis);
	      LocalDateTime date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	      return date.toString();
	}
}
