package com.microservices.services;

import java.util.List;

import com.microservices.entities.User;

public interface UserService {

	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getUser(String userId);
	
}
