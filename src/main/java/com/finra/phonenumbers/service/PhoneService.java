package com.finra.phonenumbers.service;


import com.finra.phonenumbers.model.PhoneNumbers;

/**
 * @author LIMBA_2
 *
 */
public interface PhoneService  {
	/**
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public PhoneNumbers getPhones(String phoneNumber);
	
	/**
	 * 
	 * @param phoneNumber
	 * @param pageNumber
	 * @return
	 */
	public PhoneNumbers getPhonesByPageNumber(String phoneNumber, int pageNumber);
}
