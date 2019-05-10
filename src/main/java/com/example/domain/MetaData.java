package com.example.domain;

public class MetaData {
	
	private String createdDate;
	private String fileName;
	
	public MetaData(String fileName, String createdDate) {
		super();
		this.createdDate = createdDate;
		this.fileName = fileName;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
