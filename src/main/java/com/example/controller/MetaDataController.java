package com.example.controller;

import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.MetaData;
import com.example.service.metadata.MetaDataService;

@RestController
@RequestMapping("/file/meta-data")
public class MetaDataController {

	@Resource 
	private MetaDataService metaDataService;
	
	Logger logger = LoggerFactory.getLogger(MetaDataController.class);
	
	@GetMapping
	public ResponseEntity<Collection<MetaData>> getMetaDataList() {
		logger.debug("MetaDataController : getMetaDataList()");
		return ResponseEntity.ok().body(metaDataService.getAll());
	}
}
