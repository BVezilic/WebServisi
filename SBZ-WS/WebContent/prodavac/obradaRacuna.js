(function(angular) {
	var app = angular.module('app');
	app.controller('obradaRacunaCtrl', ['$scope', '$http', function($scope, $http) {
		var getRacuni = function(){
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/racun/all'
			}).then(function successCallback(response) {
				$scope.racuni = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kog GET racuni");
			  });
		}();
		
		$scope.selectRacun = function(racun) {
			$scope.selectedRacun = racun;
		};
		
	}]);
})(angular);