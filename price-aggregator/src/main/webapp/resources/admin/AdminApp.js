var app = angular.module('AdminApp', [ 'ngRoute', 'appCtrl' ]);

var resPath = 'resources/admin';

app.config( [ '$routeProvider', function($routeProvider) {
	$routeProvider
	// default page
	.when('/', {
		templateUrl : resPath + '/views/index.html',
		controller : 'Index'
	})
	// about page
	.when('/category', {
		templateUrl : resPath + '/views/category/index.html',
		controller : 'Category'
	})
	.when('/category/new', {
		templateUrl : resPath + '/views/category/edit.html',
		controller : 'CategoryEdit'
	})
	.when('/category/:id', {
		templateUrl : resPath + '/views/category/edit.html',
		controller : 'CategoryEdit'
	})
	// date page
//	.when('/date', {
//		templateUrl : 'pages/date.html',
//		controller : 'Date'
//	});
}]);