package com.example.service.metadata;

import java.util.List;

import com.example.domain.MetaData;

public interface MetaDataService {
	public MetaData get(String fileName);
	public List<MetaData> getAll();
	public void store(MetaData metaData);
}
