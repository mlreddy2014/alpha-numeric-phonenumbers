package com.finra.phonenumbers.service;

import java.util.List;

import com.finra.phonenumbers.model.User;

public interface UserService {
	User findById(long id);
	User findByName(String name);
	User findByMailIdPassword(User user);
	List<User> findAllUsers(); 
	public boolean isUserExist(User user);
	
}
