package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.api.entity.Content;

public interface ContentService {
	Content saveContent(Content Content);
	Content getContentById(int id);
	List<Content> getAllContent();
	void deleteContentById(int id);
	Page<Content> getContentByPagination(int page, int pageSize);
}
