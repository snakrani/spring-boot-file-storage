package com.example.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import static java.util.Collections.singletonList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.domain.MetaData;
import com.example.service.metadata.MetaDataService;

@RunWith(MockitoJUnitRunner.class)
public class MetaDataControllerTest {

	@Mock 
	MetaDataService metaDataServiceMock;

	@InjectMocks
	MetaDataController metaDataConroller;
	
	@Test
	public void getFile() {
		
		String fileName = "TestFile";
		when(metaDataServiceMock.getAll()).thenReturn(singletonList(new MetaData(fileName,"")));
		
		ResponseEntity<Collection<MetaData>> response = metaDataConroller.getMetaDataList();
		
		verify(metaDataServiceMock).getAll();
		assertEquals(fileName, response.getBody().iterator().next().getFileName());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
