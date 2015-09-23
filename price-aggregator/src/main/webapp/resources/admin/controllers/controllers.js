var appCtrl = angular.module('appCtrl', []);

var ctxPath = '/price-aggregator';

appCtrl.controller('Index', [ '$scope', function($scope) {
	$scope.message = "admin index dashboard";
} ]);

appCtrl.controller('Category', [ '$scope', '$http', function($scope, $http) {
	$http.get("/price-aggregator/admin/category").then(function(response) {
		if (response.data.success) {
			$scope.categories = response.data.categories;
		} else {
			$scope.categories = [];
			alert(response.data.message);
		}
	}, function(response) {
		alert('network/server error');
	});
	
	//$scope.delete
} ]);

appCtrl.controller('CategoryEdit', [
		'$scope',
		'$routeParams',
		'$http',
		function($scope, $routeParams, $http) {
			$http.get(ctxPath + "/admin/category/" + $routeParams.id).then(
					function(response) {
						if (response.data.success) {
							$scope.category = response.data.category;
							$scope.parent = response.data.parent;
							$scope.statuses = response.data.statuses;
						} else {
							$scope.category = {};
							alert(response.data.message);
						}
					}, function(response) {
						alert('network/server error');
					});

			$scope.update = function() {
				$http.post(ctxPath + '/admin/category/update', $scope.category)
						.then(function(response) {
							if (response.data.success) {
								$scope.category = response.data.category;
								alert("сохранено");
							} else {
								$scope.category = {};
								alert(response.data.message);
							}
						}, function(response) {
							alert('network/server error');
						});
			}

		} ]);

appCtrl.controller('CategoryNew', [
		'$scope',
		'$routeParams',
		'$http',
		function($scope, $routeParams, $http) {
			$http.get(ctxPath + '/admin/category/new/' + $routeParams.parentId)
					.then(function(response) {
						if (response.data.success) {
							$scope.parent = response.data.parent;
							$scope.category = {
								parentId : response.data.parent.id,
								statusId : response.data.parent.statusId
							};
							$scope.statuses = response.data.statuses;
						} else {
							alert(response.data.message);
						}
					}, function(response) {
						alert('network/server error');
					})
					
			$scope.create = function() {
				console.log($scope.category);

				$http.post(ctxPath + '/admin/category/create', $scope.category)
						.then(function(response) {
							if (response.data.success) {
								$scope.category = response.data.category;
								alert("сохранено");
							} else {
								$scope.category = {};
								alert(response.data.message);
							}
						}, function(response) {
							alert('network/server error');
						});
			}
		} ])