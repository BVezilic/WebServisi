(function(angular) {
	var app = angular.module('app');
	app.controller('korpaCtrl', ['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
		/*
		 * variables 
		 */
		$scope.hasRacun = false;
		$scope.racun = {};
		
		
		/*
		 * functions
		 */
		
		var getKorpa = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/korpa/get'
			}).then(function successCallback(response) {
				$scope.korpa = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kod GET racuni");
			  });
		};
		getKorpa();
		
		$scope.removeFromKorpa = function(artikalUKorpi) {
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/korpa/remove',
				  data: artikalUKorpi.artikal
				  
				}).then(function successCallback(response) {
					getKorpa();
				}, function errorCallback(response) {
					  console.log("Greska kod removeFromoKorpa");
				  });
		};
		
		$scope.pregledRacuna = function(){
			console.log($rootScope.korisnik);
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/pregled',
				  data: $rootScope.korisnik
				}).then(function successCallback(response) {
					$scope.hasRacun = true;
					$scope.racun = response.data;
				}, function errorCallback(response) {
					  console.log("Greska kod removeFromoKorpa");
				  });
		};
		
		$scope.ponistiRacun = function(){
			$scope.racun={};
			$scope.hasRacun = false;
			$scope.korpa = [];
		};
		
		$scope.potvrdiRacun = function(racun){
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/potvrda',
				  data: racun,
				  params: {"bodovi":0}
				}).then(function successCallback(response) {
					$scope.racun={};
					$scope.hasRacun = false;
					$scope.korpa = [];
				  }, function errorCallback(response) {
					  console.log("Greska kod addToKorpa");
				  });
			
		};
	}]);
})(angular);