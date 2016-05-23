package com.finra.phonenumbers.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.finra.phonenumbers.model.PhoneNumbers;
import com.finra.phonenumbers.model.User;
import com.finra.phonenumbers.service.PhoneService;
import com.finra.phonenumbers.service.UserService;

/**
 * @author LIMBA_2
 *
 */
@RestController
public class PhoneNumbersController {

	private final static Logger LOG = Logger
			.getLogger(PhoneNumbersController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private PhoneService phoneService;

	/**
	 * 
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping(value = "/phonenumbers/{phoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhoneNumbers> getPhones(
			@PathVariable("phoneNumber") String phoneNumber) {
		LOG.debug("Rest service Fetching Phone Numbers List for: "
				+ phoneNumber);
		PhoneNumbers phoneNumbers = phoneService.getPhones(phoneNumber);
		return validateAndReturnAlphaNumericPhoneNumbers(phoneNumbers);
	}
	
   /*
	 * 
	 * @param phoneNumber
	 * @param pageNumber
	 * @return
	 */
   @RequestMapping(value = "/phonenumbers/{phoneNumber}/{pageNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PhoneNumbers> getSubPhones(
			@PathVariable("phoneNumber") String phoneNumber, @PathVariable("pageNumber") int pageNumber     ) {
		LOG.debug("Rest service Fetching sub Phone Numbers List for: "
				+ phoneNumber);
		PhoneNumbers phoneNumbers = phoneService.getPhonesByPageNumber(phoneNumber,pageNumber);
		return validateAndReturnAlphaNumericPhoneNumbers(phoneNumbers);
	}
	
	/**
	 * 
	 * @param user
	 * @param ucBuilder
	 * @return
	 */
	@RequestMapping(value = "/loginuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> validateUser(@RequestBody User user,
			UriComponentsBuilder ucBuilder) {
		LOG.debug("Validating User email " + user.getEmail());
		ResponseEntity<String> response = null;
		if (userService.isUserExist(user)) {
			LOG.debug("A User with maild id " + user.getEmail()
							+ " email  exist" + user.getPassword()
							+ " password  exist");
			response = new ResponseEntity<String>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	/**
	 * @param phoneNumbers
	 * @return
	 */
	private ResponseEntity<PhoneNumbers> validateAndReturnAlphaNumericPhoneNumbers(
			PhoneNumbers phoneNumbers) {
		ResponseEntity<PhoneNumbers> response = null;
		if (phoneNumbers == null || phoneNumbers.getPhoneNumbers() == null || phoneNumbers.getPhoneNumbers().isEmpty()) {
			response = new ResponseEntity<PhoneNumbers>(phoneNumbers,
					HttpStatus.NO_CONTENT);											
		} else {
			response = new ResponseEntity<PhoneNumbers>(phoneNumbers, HttpStatus.OK);
		}
		LOG.debug("Total number of combinations "
				+ phoneNumbers.getTotalPhoneNumbers());

		return response;
	}

}