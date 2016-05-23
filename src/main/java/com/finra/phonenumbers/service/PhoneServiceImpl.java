package com.finra.phonenumbers.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.finra.phonenumbers.controller.PhoneNumbersController;
import com.finra.phonenumbers.model.PhoneNumbers;


/**
 * @author LIMBA_2
 *
 */
@Service("phoneService")
public class PhoneServiceImpl implements PhoneService {
	private final static Logger LOG = Logger.getLogger(PhoneNumbersController.class);	
	private static final int PHONE_LENGTH_CON1=7;
	private static final int PHONE_LENGTH_CON2=10;
	private static final char[][] allowedLetters = new char[][] { { '0' },
			{ '1' }, {'2', 'A', 'B', 'C' }, {'3', 'D', 'E', 'F' }, { '4','G', 'H', 'I' },
			{ '5','J', 'K', 'L' }, {'6', 'M', 'N', 'O' }, {'7', 'P', 'Q', 'R', 'S' },
			{ '8','T', 'U', 'V' }, {'9', 'W', 'X', 'Y', 'Z' } };
	private static final int PAGE_PHONENUMBER_DISPLAY_COUNT = 50;
	private Map<String,List<String>> phoneCache;
	
	@PostConstruct
	public void init() {
		phoneCache = new HashMap<String, List<String>>();
	}

	/* (non-Javadoc)
	 * @see com.phone.service.PhoneConverter#getPhoneList(java.lang.String)
	 */
	@Override
	public PhoneNumbers getPhones(String phoneNumber) {
		if (phoneNumber !=null && (phoneNumber.length()==PHONE_LENGTH_CON1  || phoneNumber.length()==PHONE_LENGTH_CON2) ){
			 loadAlphaNumericPhoneNumbers(phoneNumber);
		}
		return getPhonesByPageNumber(phoneNumber, 0);
	}

	/**
	 * @param phoneNumber
	 * @return
	 */
	private void loadAlphaNumericPhoneNumbers(String phoneNumber) {
		if(!phoneCache.containsKey(phoneNumber)) {
		    List<String> phoneList=createPhoneDecoderList(phoneNumber, 0, new ArrayList<String>());
			phoneCache.put(phoneNumber, phoneList);
		}
		LOG.debug("Old List size:"+ phoneCache.get(phoneNumber).size());
	}

	/**
	 * 
	 */
	@Override
	public PhoneNumbers getPhonesByPageNumber(String phoneNumber, int pageNumber) {
		List<String>  phoneList = phoneCache.get(phoneNumber);
		int indexCount = 0;
		int endIndex=0;
		if(pageNumber > 0) {
			indexCount = pageNumber * PAGE_PHONENUMBER_DISPLAY_COUNT;
		}
		if (indexCount+PAGE_PHONENUMBER_DISPLAY_COUNT >phoneList.size()){
			endIndex = phoneList.size();
		}else 
		{
			endIndex=indexCount + PAGE_PHONENUMBER_DISPLAY_COUNT;
		}
		PhoneNumbers phoneNumbers=new PhoneNumbers();
		phoneNumbers.setPhoneNumbers(phoneList.subList(indexCount, endIndex));
		phoneNumbers.setTotalPhoneNumbers(phoneList.size());
		return phoneNumbers;
	}
	
	/**
	 * @param originalPhoneNumber
	 * @param index
	 * @param comobresults
	 */
	private List<String> createPhoneDecoderList(String originalPhoneNumber, int index, List<String> comobresults) {
		if (index == originalPhoneNumber.length()) {			
			return comobresults;
		}
		int num = Integer.parseInt(originalPhoneNumber.charAt(index) + "");

		if (index == 0) {
			for (int j = 0; j < allowedLetters[num].length; j++) {
				comobresults.add(allowedLetters[num][j] + "");
			}
			return createPhoneDecoderList(originalPhoneNumber, index + 1, comobresults);
		} else {
			List<String> newcomobresults = new ArrayList<String>();
			for (int j = 0; j < allowedLetters[num].length; j++) {
				for (String result : comobresults) {
					newcomobresults.add(result + allowedLetters[num][j]);
				}
			}
		 return	createPhoneDecoderList(originalPhoneNumber, index + 1, newcomobresults);
		}
	}

}
