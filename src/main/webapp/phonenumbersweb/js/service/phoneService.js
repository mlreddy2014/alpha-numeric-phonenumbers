'use strict';

App.factory('PhoneService', ['$http', '$q', function($http, $q){

	return {
		
		// This service will return all the phone numbers list 
		
		fetchPhoneNumberList: function(phoneNumber) {
    		 return $http.get('/phonenumbers/'+phoneNumber)
			.then(
					function(response){
						return response.data;
					}, 
					function(errResponse){
						console.error('Error while fetching Phone Numbers list');
						return $q.reject(errResponse);
					}
			);
	        },
	        // This service will return the phone numbers list from Restfull service paginatation ( in json format)
	        fetchSubPhoneNumberList: function(phoneNumberPara,pageNumber) {
	        	console.log(' sub phoneNumber:'+phoneNumberPara);
	        	console.log(' sub pageNumber:'+pageNumber);
				var url="/phonenumbers/"+phoneNumberPara+"/"+pageNumber;
				console.log(' sub phoneNumber  url:'+url);
				
	    		 return $http.get(url)
				.then(
						function(response){
							return response.data;
						}, 
						function(errResponse){
							console.error('Error while fetching Phone Numbers list');
							return $q.reject(errResponse);
						}
				);
		          },
		          
		          // This for form level validation service (poc is used for only user name exist or not validation)
		    validateUser: function(user){
				return $http.post('/loginuser', user)
						.then(
								function(response){
									console.log('Success validating user name:  '+response.status);
									return response;// ok 200 should be
								}, 
								function(errResponse){
									console.error('Validating user name Failed: '+errResponse.status);
									return $q.reject(errResponse);// no data 404 found
								}
						);
	    },
	
		
	};

}]);
