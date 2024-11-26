package com.example.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	User findByIdAndRole(String id, String role);
	
	User findByEmail(String email);
	
	Optional<User> findById(String id);
	
	List<User> findByRole(String role);
	
	void deleteById(String id);
	
	Page<User> findAll(Pageable pageable);

}
