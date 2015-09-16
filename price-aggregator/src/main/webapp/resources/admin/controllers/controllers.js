var appCtrl = angular.module('appCtrl', []);

var ctxPath = '/price-aggregator';

appCtrl.controller('Index', [ '$scope', function($scope) {
	$scope.message = "admin index dashboard";
} ]);

appCtrl.controller('Category', [ '$scope', '$http', function($scope, $http) {
	$http.get("/price-aggregator/admin/category").success(function(data) {
		$scope.categories = data;
	});
} ]);

appCtrl.controller('CategoryEdit', [
		'$scope',
		'$routeParams',
		'$http',
		function($scope, $routeParams, $http) {
			$http.get(ctxPath + "/admin/category/" + $routeParams.id).then(
					function(response) {
						$scope.category = response.data.category;
						$scope.statuses = response.data.statuses;
					});

			$scope.update = function() {
				console.log($scope.category);

				$http.post(ctxPath + '/admin/category/update', $scope.category)
						.then(function(response) {
							$scope.category = response.data;
						});

			}
		} ]);

// app.controller('Date', [ '$scope', function($scope) {
// $scope.now = new Date();
// } ]);
