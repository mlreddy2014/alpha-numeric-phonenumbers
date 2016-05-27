/**
 * 
 */
package com.finra.phonenumbers.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.Assert;

import com.finra.phonenumbers.model.User;
import com.finra.phonenumbers.service.UserService;
import com.finra.phonenumbers.service.UserServiceImpl;

/**
 * @author LIMBA_2
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PhoneNumberUserTest {
	@Autowired
	UserService userService;

	@Configuration
	static class ContextConfiguration {
		@Bean
		public UserService userService() {
			UserService userService = new UserServiceImpl();
			return userService;
		}
	}

	@Test
	public void testUserMailExist() {
		User userT = new User(1, "LIMBA", "1234567", "finra.es.test@gmail.com",
				"test123");// 7
		Assert.isTrue(userService.isUserExist(userT));
	}

	@Test
	public void testUserMailNotExist() {
		User userT = new User(1, "LIMBA", "1234567", "noUser@gmail.com",
				"test123");// 7
		Assert.isTrue(!userService.isUserExist(userT));
	}

}
