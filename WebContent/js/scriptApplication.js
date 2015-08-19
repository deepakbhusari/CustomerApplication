var app = angular.module('applWeb', [ 'ngRoute', 'ngResource' ]);

app.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider, $routeParams) {
			$routeProvider.when('/CustomerForm', {
				redirectTo : '/CustomerForm/0'
			}).when('/CustomerForm/:id', {
				templateUrl : 'customerForm.html',
				controller : "customerController"
			}).when('/CustomerList', {
				templateUrl : 'customerList.html'
			}).otherwise({
				redirectTo : '/'
			});
		} ]);

app.controller('customerController', [ '$scope',
		function($scope, $routeParams) {
			$scope.heading = 'Add/Edit Customer';

		} ]);

app.controller('customerListController', [ '$scope', '$resource',
		'$routeParams', function($scope, $resource, $routeParams, customers) {
			var resourceUrl = $resource('/App1/rest/customers/all');
			$scope.master = {};
			$scope.heading = 'Customer List';

			// resourceUrl.query(function(data) {
			// $scope.customers = data;
			// });

			resourceUrl.query({}, // params
			function(data) { // success
				console.log(data);
				$scope.customers = data;
			}, function(data) { // failure
				// error handling goes here
				console.log("error : " + data);

			});
		} ]);

app.controller('customerFormCtrl', [ '$scope', '$resource', '$window',
		'$routeParams',
		function($scope, $resource, $window, $routeParams, customerData) {
			$scope.master = {};
			// console.log("hi cus form ctrl");

			if ($routeParams.id > 0) {
				// console.log($routeParams.id);
				// fetch customer details
				var urlString = '/App1/rest/customers/id/' + $routeParams.id;
				var resourceUrl = $resource(urlString);

				resourceUrl.get({}, // params
				function(data) { // success
					console.log(data);
					$scope.customer = data;
				}, function(data) { // failure
					// error handling goes here
					console.log("error : " + data);

				});
      };

			$scope.create = function(customerData) {
				var resourceUrl = $resource('/App1/rest/customers/add');

				$scope.master = angular.copy(customerData);
				$scope.submitted = false;
				// console.log(customer);
				// console.log(customerData.name);

				// resourceUrl.save(JSON.stringify(customerData));
				resourceUrl.save(JSON.stringify(customerData), function(data) { // success
					console.log(data);
					$window.location.href = '/App1/#/Home';
					$window.location.reload();
					$window.location.href = '#/CustomerList';
					$window.location.reload();

				}, function(data) { // failure
					// error handling goes here
					console.log("error : " + data);
					$window.location.href = '/App1/#/Home';
				});

			};

			// $scope.edit = function(customerId) {
			// console.log("hi");
			// }
		} ]);
