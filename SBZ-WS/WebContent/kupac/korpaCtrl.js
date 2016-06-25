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
					
					if(response.data != "")
					{
						$scope.hasRacun = true;
						$scope.racun = response.data;
					}else
					{
						window.alert("Nema dovoljno odredjenih artikala na lageru da bi se formirala ova porudzbina.");
					}
				}, function errorCallback(response) {
					  console.log("Greska kod removeFromoKorpa");
				  });
		};
		
		$scope.ponistiRacun = function(){
			$scope.racun={};
			$scope.hasRacun = false;
			$scope.korpa = [];
		};
		
		$scope.potvrdiRacun = function(racun, ulozeniBodovi){
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/potvrda',
				  data: racun,
				  params: {"bodovi":ulozeniBodovi}
				}).then(function successCallback(response) {
					if(response.data == true){
						$scope.racun={};
						$scope.hasRacun = false;
						$scope.korpa = [];
						console.log($rootScope.korisnik.profilKupca.nagradniBodovi);
						$rootScope.korisnik.profilKupca.nagradniBodovi -= ulozeniBodovi;
						console.log($rootScope.korisnik.profilKupca.nagradniBodovi);
					}else
					{
						window.alert("Nemate dovoljno bodova da biste ostvarili ovu kupovinu");
					}
				  }, function errorCallback(response) {
					  console.log("Greska kod addToKorpa");
				  });
			
		};
	}]);
})(angular);