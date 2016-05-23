package com.finra.phonenumbers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.finra.phonenumbers.model.User;

/**
 * @author LIMBA_2
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<User> users;

	@PostConstruct
	public void init() {
		users = populateDummyUsers();
	}

	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public User findById(long id) {
		User usr = null;
		for (User user : users) {
			if (user.getId() == id) {
				usr = user;
				break;
			}
		}
		return usr;
	}

	@Override
	public User findByName(String name) {
		User usr = null;
		for (User user : users) {
			if (user.getUsername().equalsIgnoreCase(name)) {
				usr = user;
				break;
			}
		}
		return usr;
	}

	@Override
	public User findByMailIdPassword(User user) {
		User usr = null;
		for (User userLocal : users) {
			if (userLocal.getEmail().equalsIgnoreCase(user.getEmail())
					& userLocal.getPassword().equals(user.getPassword())) {
				usr = user;
				break;
			}
		}
		return usr;
	}

	@Override
	public boolean isUserExist(User user) {
		return findByMailIdPassword(user) != null;
	}

	private static List<User> populateDummyUsers() {
		List<User> users = new ArrayList<User>();
		users.add(new User(counter.incrementAndGet(), "LIMBA", "1234567",
				"finra.es.test@gmail.com", "test123"));// 7
		users.add(new User(counter.incrementAndGet(), "REDDY", "1234567890",
				"testreddy@gmail.com", "test123")); // 10
		return users;
	}

}
