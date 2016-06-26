(function(angular) {
	var app = angular.module('app');
	app.controller('nalogCtrl', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
		var getRacuni = function(){
			console.log($rootScope.korisnik);
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/getForKupac',
				  data: $rootScope.getCurrentUser().korisnickoIme
				}).then(function successCallback(response) {
					$scope.racuni = response.data;
				  }, function errorCallback(response) {
					  console.log("Greska kod GET racuni");
				  });
		};
		
		getRacuni();
			
		$scope.checkRacun = function(racun){
			$scope.racun = racun;
			$scope.showSelectedRacun = true;
		};
		
		$scope.racuni = {};
		$scope.showSelectedRacun = false;
		$scope.korisnik = $rootScope.getCurrentUser();
		
		
	}]);
})(angular);