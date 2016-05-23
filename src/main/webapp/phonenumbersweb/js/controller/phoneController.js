'use strict';

App.controller('PhoneController', ['$scope', 'PhoneService', function($scope, PhoneService) {
          var self = this;
          $scope.onlyNumbers = /^\d+$/;
          self.user={id:'',username:'',phone:'',email:'',password:''};// user detail structure.
          self.phones=[];
         // self.subphones=[];
          self.loginSuccess=false;
          self.loginFailureMessage='';
          self.status='';
          self.currentPage = 0;
          self.totalPhoneCount = 0;
          self.pageSize = 50;
         
          self.numberOfPages=function(){
        	  if (self.loginSuccess==true){
                 return Math.ceil(self.totalPhoneCount/self.pageSize); 
        	  }else{
        		  return 1;
        	  }
            };
              self.getSubPhoneNumberList = function(phoneNumber,pageNumber){
              PhoneService.fetchSubPhoneNumberList(phoneNumber,pageNumber)
                    .then(
        					       function(d) {
        					    	 self.phones  = d.phoneNumbers;
        					    	 self.totalPhoneCount=d.totalPhoneNumbers;
        					    	 console.log('Sub Phone List  count:'+self.totalPhoneCount);
               				    	 self.loginSuccess=true;
        					       },
              					function(errResponse){
              						 console.error('Error while fetching Sub Phone List');
              						 self.countmessage='Error while fetching Phone list';
                 					    
              					}
        			       );
              };
             self.fetchPhoneNumberList = function(phoneNumber){
        	  PhoneService.fetchPhoneNumberList(phoneNumber)
                  .then(
      					       function(d) {
      					    	
      					    	 self.phones  = d.phoneNumbers;
    					    	 self.totalPhoneCount=d.totalPhoneNumbers;
    					    	 console.log('Sub Phone List  :'+self.phones);	
    					    	 console.log('Total Phone List  count:'+self.totalPhoneCount);
           				    	 self.loginSuccess=true;
      					    	 self.countmessage='Total number of Phone List combinations: '+self.totalPhoneCount;
      					       },
            					function(errResponse){
            						 console.error('Error while fetching Phone');
            						 self.countmessage='Error while fetching Phone list';
               					    
            					}
      			       );
            };
          self.validateUser = function(user){
        	  PhoneService.validateUser(user)
		              .then(           
			              function(validResponse){
					               console.log('validateUser   :'+validResponse.status);
					               self.status =  validResponse.status;
					               if(self.status ==  '200'){
					            	   console.log('controller validation sucess for ting for email :'+user.email);
						               console.log('self.user.phone  testing for  :'+user.phone);
					                  self.fetchPhoneNumberList(user.phone);
						             // self.fetchSubPhoneNumberList(user.phone,1);
					                self.loginFailureMessage="";
				                    }
					             },function(errResponse){
			              	      self.status='';
			             	      console.log('controller validation Failed validation for email :'+user.email);
					     	      console.log('Login Failed case  :'+user.phone);
					    	      self.loginSuccess=false; 
			               	      self.loginFailureMessage='Invalid User data( validation done on mail id exist or not in the service.)';
		      			
			                 }	
                     );
             };
          
           self.submit = function() {
               self.validateUser(self.user);
              //  self.reset();
            };
          self.reset = function(){
              self.user={id:'',username:'',phone:'',email:'',password:''};// user detail structure.
              self.currentPage = 0;
              $scope.myForm.$setPristine(); //reset Form
          };
      }]);
