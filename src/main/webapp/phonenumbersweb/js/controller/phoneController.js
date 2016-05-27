'use strict';

App.controller('PhoneController', ['$scope', 'PhoneService','$window', function($scope, PhoneService,$window) {
          var self = this;
          $scope.onlyNumbers = /^\d+$/;
          self.user={id:'',username:'',phone:'',email:'',password:''};// user detail structure.
          self.phones=[];
          self.formSuccess=false;
          self.formFailureMessage='';
          self.status='';
          self.currentPage = 0;
          self.totalPhoneCount = 0;
          self.pageSize = 50;// This can be parameterized from the prperties file.
          
          // code  for google sign in validation success or failure 
          $scope.$on('event:google-plus-signin-success', function (event, authResult) {
              // User successfully authorized the G+ App!
              console.log('Signed in!',authResult);
              $scope.signedInStatus=true;
              $scope.getUserInfo();
             });
            $scope.$on('event:google-plus-signin-failure', function (event, authResult) {
              // User has not authorized the G+ App!
               $scope.signedInStatus=false;
              console.log('Not signed into Google Plus.',authResult);
            });
          
       //  Start code for This is optional extra POC  code if we want paly around the logged in user related data
  
       // Process user info.
      // userInfo is a JSON object.
      $scope.processUserInfo = function(userProfileInfo) {
          // You can check user info for domain.
    	   console.log('userProfileInfo:....Got USER DATA from google api fu :',userProfileInfo);
           console.log('userProfileInfo:....Got email id from google api :',userProfileInfo.emails[0].value);
           self.user.email=userProfileInfo.emails[0].value;
      }
       
      // When callback is received, process user info.
      $scope.userInfoCallback = function(userInfo) {
          $scope.$apply(function() {
              $scope.processUserInfo(userInfo);
          });
      };
       
      // Request user info.
      $scope.getUserInfo = function() {
          gapi.client.request(
              {
                  'path':'/plus/v1/people/me',
                  'method':'GET',
                  'callback': $scope.userInfoCallback
              }
          );
      };
      //  end optional logged in user related check info
          
          
             
          self.numberOfPages=function(){
        	  if (self.formSuccess==true){
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
               				    	 self.formSuccess=true;
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
           				    	 self.formSuccess=true;
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
					            	   console.log('controller validation sucess for ting for user name :'+user.email);
						               console.log('self.user.phone  testing for  :'+user.phone);
					                  self.fetchPhoneNumberList(user.phone);
						             // self.fetchSubPhoneNumberList(user.phone,1);
					                self.formFailureMessage="";
				                    }
					             },function(errResponse){
			              	      self.status='';
			             	      console.log('controller validation Failed validation for user name :'+user.email);
					     	      console.log('Login Failed case  :'+user.phone);
					    	      self.formSuccess=false; 
			               	      self.formFailureMessage='Invalid User data( validation done on user name not exist or  the rest service call is failed.)';
		      			
			                 }	
                     );
             };
          
           self.submit = function() {
               self.validateUser(self.user);
              //  self.reset();
            };
          self.reset = function(){// here mail id is not reset retained as is..
              self.user={id:'',username:'',phone:'',email:self.user.email,password:''};// user detail structure.
              self.currentPage = 0;
              $scope.myForm.$setPristine(); //reset Form
          };
      }]);
