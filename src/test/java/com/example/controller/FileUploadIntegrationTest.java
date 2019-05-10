package com.example.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	FileConroller fileConroller;

	@Test
	public void shouldUploadFile() throws Exception {

		MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain",
				"Test File".getBytes());

		this.mockMvc.perform(multipart("/file").file(multipartFile)).andExpect(status().isCreated())
				.andExpect(content().string(containsString("File is created.")));
	}

	@Test
	public void shouldGetFile() throws Exception {

		this.mockMvc.perform(get("/file/test.txt")).andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.txt\""));
	}

	@Test
	public void shouldGetFileNames() throws Exception {

		this.mockMvc.perform(get("/file")).andExpect(status().isOk())
				.andExpect(content().string(containsString("test.txt")));

	}
	
	@Test
	public void shouldGetMetaDataList() throws Exception {

		this.mockMvc.perform(get("/file/meta-data")).andExpect(status().isOk())
				.andExpect(content().string(containsString("test.txt")));

	}

}
