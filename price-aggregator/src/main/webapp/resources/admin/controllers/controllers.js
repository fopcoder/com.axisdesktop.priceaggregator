var appCtrl = angular.module('appCtrl', []);

var ctxPath = '/price-aggregator';

appCtrl.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

appCtrl.controller('Index', [ '$scope', function($scope) {
	$scope.message = "admin index dashboard";
} ]);

appCtrl.controller('Category', [ '$scope', '$http', function($scope, $http) {
	$http.get( ctxPath + '/admin/category').then(function(response) {
		if (response.data.success) {
			$scope.categories = response.data.categories;
		} else {
			$scope.categories = [];
			alert(response.data.message);
		}
	}, function(response) {
		alert('network/server error');
	});
	
	$scope.delete = function( id )	{
		$http.delete(ctxPath + '/admin/category/delete/' + id )
		.then(function(response) {
			if (response.data.success) {
				$scope.categories = response.data.categories;
				alert("удалено");
			} else {
				alert(response.data.message);
			}
		}, function(response) {
			alert('network/server error');
		});
	}
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
							$scope.image = null;
						} else {
							$scope.category = {};
							alert(response.data.message);
						}
					}, function(response) {
						alert('network/server error');
					});

			$scope.update = function() {
			
				$http.post(ctxPath + '/admin/category/update', $scope.category )
						.then(function(response) {
							if (response.data.success) {
								$scope.category = response.data.category;
								
								// TODO сделать нормальные бублы
								alert("сохранено");
								
								// TODO сделать одним посылом	
								var formData = new FormData();
						        formData.append("image",$scope.image);
						        formData.append("categoryId",$scope.category.id)
						        
								$http.post(ctxPath + '/admin/category/add/image', formData, { headers: {'Content-Type': undefined} })
								.then( function(response) {
										if (response.data.success) {
											alert("картика сохранена");
										}
										else	{
											alert(response.data.message);
										}
									},
									function( response){
										alert('network/server error');
									}
								)
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