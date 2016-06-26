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
		
		$scope.getPopusti = function(stavka){
			var popusti = stavka.primenjeniPopusti;
			var ispis = "";
			
			for(var i = 0; i < popusti.length; i++){
				ispis += popusti[i].sifra + '\r\n';
			}
			
			return ispis;
		};
		
		$scope.removeFromKorpa = function(artikalUKorpi) {
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/korpa/remove',
				  data: artikalUKorpi.artikal
				  
				}).then(function successCallback(response) {
					$scope.korpa = response.data;
					$scope.racun={};
					$scope.hasRacun = false;
				}, function errorCallback(response) {
					  console.log("Greska kod removeFromoKorpa");
				  });
		};
		
		$scope.pregledRacuna = function(){
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/racun/pregled',
				  data: $rootScope.getCurrentUser().korisnickoIme
				}).then(function successCallback(response) {
					
					if(response.data != "")
					{
						$scope.hasRacun = true;
						$scope.racun = response.data;
					}else
					{
						window.alert("Nije moguce kreirati ovakav racun.");
					}
				}, function errorCallback(response) {
					  console.log("Greska kod removeFromoKorpa");
				  });
		};
		
		$scope.ponistiRacun = function(){
			$scope.racun={};
			$scope.hasRacun = false;
			
		};
		
		$scope.potvrdiRacun = function(racun, ulozeniBodovi){
			if(typeof ulozeniBodovi == 'undefined'){
				ulozeniBodovi = 0;
			}
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
						$rootScope.getCurrentUser().profilKupca.nagradniBodovi -= ulozeniBodovi;					
					}else
					{
						window.alert("Nemate dovoljno bodova da biste ostvarili ovu kupovinu");
					}
				  }, function errorCallback(response) {
					  console.log("Greska kod addToKorpa");
				  });
		};
		
		$scope.getPopusti = function(stavka){
			var popusti = stavka.primenjeniPopusti;
			var ispis = "";
			
			for(var i = 0; i < popusti.length; i++){
				ispis += popusti[i].sifra + '\r\n';
			}
			
			return ispis;
		};
	}]);
})(angular);