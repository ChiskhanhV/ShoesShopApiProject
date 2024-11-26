package com.example.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.api.entity.Content;
import com.example.api.repository.ContentRepository;
import com.example.api.service.ContentService;
 @Service
public class ContentServiceImpl implements ContentService {
	@Autowired
    private ContentRepository ContentRepository;
	
	@Override
	public Content saveContent(Content Content) {
		// TODO Auto-generated method stub
		return ContentRepository.save(Content);
	}

	@Override
	public Content getContentById(int id) {
		return ContentRepository.getById(id);
	}

	@Override
	public List<Content> getAllContent() {
		// TODO Auto-generated method stub
		return ContentRepository.findAll();
	}

	@Override
	public void deleteContentById(int id) {
		// TODO Auto-generated method stub
		ContentRepository.deleteById(id);
	}

	@Override
	public Page<Content> getContentByPagination(int page, int pageSize) {
		// TODO Auto-generated method stub
		Page<Content> contents = ContentRepository.findAll(PageRequest.of(page, pageSize));
		return contents;
	}

}
