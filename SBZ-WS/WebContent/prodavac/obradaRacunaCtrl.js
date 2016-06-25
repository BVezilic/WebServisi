(function(angular) {
	var app = angular.module('app');
	app.controller('obradaRacunaCtrl', ['$scope', '$http', function($scope, $http) {
		var getRacuni = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/racun/all'
			}).then(function successCallback(response) {
				$scope.racuni = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kog GET racuni");
			  });
		};
		getRacuni();
		
		$scope.selectRacun = function(racun) {
			$scope.selectedRacun = racun;
		};
		
		$scope.obradiRacun = function(kolicina) {
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/racun/apply',
			  data: $scope.selectedRacun
			}).then(function successCallback(response) {
				getRacuni();
				console.log(response.data);
			  }, function errorCallback(response) {
				  console.log("Greska kog APPLY racuna");
			  });
		};
		
		$scope.otkaziRacun = function(kolicina) {
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/racun/cancel',
			  data: $scope.selectedRacun
			}).then(function successCallback(response) {
				getRacuni();
			  }, function errorCallback(response) {
				  console.log("Greska kog CANCEL racuna");
			  });
		};
	}]);
})(angular);