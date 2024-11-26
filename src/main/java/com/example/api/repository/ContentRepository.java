package com.example.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.entity.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
	List<Content> findAll();
	Content getById(int id);
}
