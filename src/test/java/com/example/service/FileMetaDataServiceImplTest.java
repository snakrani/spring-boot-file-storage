package com.example.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.domain.MetaData;
import com.example.service.metadata.FileMetaDataServiceImpl;
import com.example.service.storage.FileStorageService;

@RunWith(MockitoJUnitRunner.class)
public class FileMetaDataServiceImplTest {

	@Mock
	FileStorageService fileStorageServiceMock;
	
	
	@InjectMocks
	FileMetaDataServiceImpl fileMetaDataService;
	
	
	@Test
	public void verify_store_metadata() {
		
		String fileName = "test1";
		MetaData metaData =  new MetaData(fileName, "testTime");
		fileMetaDataService.store(metaData);
		
		assertEquals(metaData, fileMetaDataService.get(fileName));
		
	}
}
