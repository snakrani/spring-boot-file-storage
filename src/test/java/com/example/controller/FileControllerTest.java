package com.example.controller;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.MetaData;
import com.example.service.metadata.MetaDataService;
import com.example.service.storage.StorageService;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {
	
	@Mock
	StorageService storageServiceMock;

	@Mock 
	MetaDataService metaDataServiceMock;

	@Mock
	Resource testFileMock;
	
	@Mock
	MultipartFile multipartFileMock;
	
	@InjectMocks
	FileConroller fileConroller;
	
	@Test
	public void getFileNames() {
		when(storageServiceMock.getFileNames()).thenReturn(singletonList("Test"));
		
		ResponseEntity<List<String>> entity = fileConroller.getFileNames();
		
		verify(storageServiceMock).getFileNames();
		assertEquals("Test", entity.getBody().get(0));
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
	@Test(expected = RuntimeException.class)
	public void getFileNames_throwsException() {
		when(storageServiceMock.getFileNames()).thenThrow(new RuntimeException("Error in retrieving files"));
		
		fileConroller.getFileNames();
	}
	
	@Test
	public void getFile() {
		String filename = "test";
		when(storageServiceMock.getFile(filename)).thenReturn(testFileMock);
		
		ResponseEntity<Resource> response = fileConroller.getFile(filename);
		
		verify(storageServiceMock).getFile(filename);
		assertEquals(testFileMock, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getHeaders().containsKey(HttpHeaders.CONTENT_DISPOSITION));
	}
	
	@Test
	public void storeFile() {
		
		ResponseEntity<String> responseEntity = fileConroller.storeFile(multipartFileMock);
		
		verify(storageServiceMock).store(multipartFileMock);
		verify(metaDataServiceMock).store(any(MetaData.class));
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("File is created.", responseEntity.getBody());
		
	}
}
