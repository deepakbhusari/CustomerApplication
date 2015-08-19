var app = angular.module('applWeb', ['ngRoute' , 'ngResource' ]);

app.config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider) {
            $routeProvider.
            when('/CustomerForm',{
                templateUrl: 'customerForm.html',
                controller: "customerController"
            }).
            when('/CustomerList',{
                templateUrl: 'customerList.html' }).
            otherwise({
                redirectTo: '/'             
            });
        }]);

app.controller('customerController', ['$scope', function($scope,$routeParams) {
    $scope.heading = 'Add/Edit Customer';

}]);

app.controller('customerListController', ['$scope', '$resource' ,function($scope, $resource, customers) {
	var resourceUrl = $resource('/App1/rest/customers/all');
   $scope.heading = 'Customer List';
 
    resourceUrl.query(function(data) {
    	   $scope.customers=data;
    });
    
    $scope.editCustomer = function(customerId) {
    	
    }
}]);

app.controller('customerFormCtrl', ['$scope',  '$resource' ,'$window' , function($scope, $resource, $window, customerData) {
    $scope.master = {};    
    $scope.create = function(customerData) {
    	var resourceUrl = $resource('/App1/rest/customers/add');

        $scope.master = angular.copy(customerData);
        $scope.submitted = false;

    	//console.log(customer);
//    	console.log(customerData.name);

    	
    	resourceUrl.save(JSON.stringify(customerData));
    	
        $window.location.href = '/App1';

//        $routeProvider.
//        otherwise({
//            redirectTo: 'customerList.html'             
//        });
    	
    	
//    	resourceUrl = $resource('/App1/rest/customers/all');
//    	resourceUrl.query(function(data) {
//        	//console.log(data);
//        	   $scope.customers=data;
//        });
      };
    }]);



