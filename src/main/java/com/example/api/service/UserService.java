package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.api.entity.User;

public interface UserService {
	User saveUser(User user);
	Page<User> getAllUser(int page, int pageSize);
	User GetUserByEmail(String email);
	User findByIdAndRole(String id, String role);
	void deleteUserById(String id);
	List<User> findByRole(String role);
	Optional<User> getUserByID(String id);
}
