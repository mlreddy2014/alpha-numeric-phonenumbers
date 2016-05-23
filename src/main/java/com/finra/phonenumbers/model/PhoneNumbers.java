/**
 * 
 */
package com.finra.phonenumbers.model;

import java.util.List;

/**
 * @author LIMBA_2
 *
 */
public class PhoneNumbers {

	private List<String> phoneNumbers;
	
	private int totalPhoneNumbers;

	/**
	 * @return the totalPhoneNumbers
	 */
	public int getTotalPhoneNumbers() {
		return totalPhoneNumbers;
	}

	/**
	 * @param totalPhoneNumbers the totalPhoneNumbers to set
	 */
	public void setTotalPhoneNumbers(int totalPhoneNumbers) {
		this.totalPhoneNumbers = totalPhoneNumbers;
	}

	/**
	 * @return the phoneNumbers
	 */
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
}
