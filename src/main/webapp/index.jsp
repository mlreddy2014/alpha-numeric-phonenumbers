<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Phone Number to Alphanumeric application </title>  
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/phonenumbersweb/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="PhoneApp" class="ng-cloak">
      <div class="generic-container" ng-controller="PhoneController as ctrl">
          <div  ng-hide="ctrl.loginSuccess"  class="panel panel-default">
              <div class="panel-heading"><span class="lead">Phone Number to Alphanumeric application </span></div>
               <div class="panel-heading"><span class="lead">Message(validpassword:test123 and  mailid: finra.es.test@gmail.com) {{ctrl.loginFailureMessage}} </span></div>
           
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <input type="hidden" ng-model="ctrl.user.id" />
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Name</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.user.username" name="uname" class="username form-control input-sm" placeholder="Enter your name" required ng-minlength="8"   max="16"     />
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.uname.$error.required">This is a required field</span>
                                      <span ng-show="myForm.uname.$error.minlength">Minimum length required is 8</span>
                                      <span ng-show="myForm.uname.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Phone</label>
                              <div class="col-md-7">
                                  <input type="text" ng-pattern="onlyNumbers"   name="phone" ng-model="ctrl.user.phone" class="phone form-control input-sm" placeholder="Enter your Phone Number(7 or 10 digits only)"  required ng-minlength="7"  max="12"  />
                              
                                 <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.phone.$error.required">This is a required field</span>
                                      <span ng-show="myForm.phone.$error.minlength">Minimum length 7 </span>
                                      <span ng-show="myForm.phone.$invalid">This field is invalid </span>
                                      <span ng-show="!(ctrl.user.phone.length  ==7 || ctrl.user.phone.length  ==  10 )"> Phone number 7 or 10 digits only </span>
                                  
                                  </div>
                              
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Email</label>
                              <div class="col-md-7">
                                  <input type="email" ng-model="ctrl.user.email" name="email" class="email form-control input-sm" placeholder="Enter your Email" required  max="20" />
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.email.$error.required">This is a required field</span>
                                      <span ng-show="myForm.email.$invalid">This field is invalid </span>
                                      
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Password</label>
                              <div class="col-md-7">
                                  <input type="password" ng-model="ctrl.user.password" name="password" class="password form-control input-sm" placeholder="Enter Password" required ng-minlength="7"   max="16"     />
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.password.$error.required">This is a required field</span>
                                      <span ng-show="myForm.password.$error.minlength">Minimum length required is 8</span>
                                      <span ng-show="myForm.password.$invalid">invalid password </span>
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                     
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Login" class="btn btn-primary btn-sm" ng-disabled="(!(ctrl.user.phone.length  ==7 || ctrl.user.phone.length  ==10 ))   || (myForm.$invalid )   ">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div ng-show="ctrl.loginSuccess"  class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">Phone Numbers</span></div>
              <div class="panel-heading"><span class="lead">{{ctrl.countmessage}}</span></div>
              <div class="tablecontainer">
               <button  ng-click="ctrl.reset();ctrl.loginSuccess=false"> Back to Login  </button>
        	        
                <button ng-disabled="ctrl.currentPage == 0" ng-click="ctrl.getSubPhoneNumberList(ctrl.user.phone,ctrl.currentPage-1);ctrl.currentPage=ctrl.currentPage-1">
        		Previous  </button>
    		{{ctrl.currentPage+1}}/{{ctrl.numberOfPages()}}
   			 <button ng-disabled="ctrl.currentPage >= ctrl.totalPhoneCount/ctrl.pageSize - 1" ng-click="ctrl.getSubPhoneNumberList(ctrl.user.phone,ctrl.currentPage+1);ctrl.currentPage=ctrl.currentPage+1">
       		 Next	 </button>
                  <table class="table table-hover">
                      <thead>
                          <tr>
                             <th>Sno.</th>
                              <th>Phone</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                         <!--   <tr ng-repeat="phoneData in ctrl.phones | paginationFrom:ctrl.currentPage*ctrl.pageSize | limitTo:ctrl.pageSize">
                            <td><span> {{1+$index +(ctrl.currentPage)*ctrl.pageSize }} </span></td>
                              <td> {{phoneData}} </td>
                               <td>
                              </td>
                          </tr> -->
                             <tr ng-repeat="phoneData in ctrl.phones ">
                              <td><span> {{1+$index +(ctrl.currentPage)*ctrl.pageSize }} </span></td>
                              <td> {{phoneData}} </td>
                               <td>
                              </td>
                          </tr>
                       </tbody>
                  </table>
              </div>
          </div>
      </div>
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/phonenumbersweb/js/app.js' />"></script>
      <script src="<c:url value='/phonenumbersweb/js/service/phoneService.js' />"></script>
      <script src="<c:url value='/phonenumbersweb/js/controller/phoneController.js' />"></script>
  </body>
</html>