var appCtrl = angular.module('appCtrl', []);

appCtrl.controller('Index', [ '$scope', function($scope) {
	$scope.message = "admin index dashboard";
} ]);

appCtrl.controller('Category', [ '$scope', '$http', function($scope, $http) {
	$http.get("/price-aggregator/admin/category").success( 
		function(data)	{
			$scope.categories = data;
		}
	);
} ] );

appCtrl.controller('CategoryView', [ '$scope', '$routeParams', '$http', function($scope, $routeParams, $http) {
	$scope.message = "category edit " + $routeParams.id;
	$http.get("/price-aggregator/admin/category/"+$routeParams.id).success(
		function(data)	{
			$scope.category = data;
		}
	);
} ] );

//app.controller('Date', [ '$scope', function($scope) {
//	$scope.now = new Date();
//} ]);