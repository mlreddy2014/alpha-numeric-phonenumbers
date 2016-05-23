'use strict';

App.factory('PhoneService', ['$http', '$q', function($http, $q){

	return {
		
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
		    validateUser: function(user){
				return $http.post('/loginuser', user)
						.then(
								function(response){
									console.log('Success validating user:  '+response.status);
									return response;// ok 200 should be
								}, 
								function(errResponse){
									console.error('Validating user Failed: '+errResponse.status);
									return $q.reject(errResponse);// no data 404 found
								}
						);
	    },
	
		
	};

}]);
