package com.example.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.entity.User;
import com.example.api.repository.UserRepository;
import com.example.api.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User GetUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByIdAndRole(String id, String role) {
		// TODO Auto-generated method stub
		return userRepository.findByIdAndRole(id, role);
	}

	@Override
	public void deleteUserById(String id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findByRole(String role) {
		return userRepository.findByRole(role);
	}

	@Override
	public Page<User> getAllUser(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> getUserByID(String id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

}
