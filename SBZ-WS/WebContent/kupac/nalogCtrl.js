(function(angular) {
	var app = angular.module('app');
	app.controller('nalogCtrl', ['$scope', '$http', function($scope, $http) {
		var getRacuni = function(){
			$http({
				  method: 'GET',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/all'
				}).then(function successCallback(response) {
					$scope.racuni = response.data;
				  }, function errorCallback(response) {
					  console.log("Greska kod GET racuni");
				  });
		}
		getRacuni();
			
		$scope.checkRacun = function(racun){
			$scope.racun = racun;
			$scope.showSelectedRacun = true;
		};
		
		$scope.racuni = {};
		$scope.showSelectedRacun = false;
	}]);
})(angular);