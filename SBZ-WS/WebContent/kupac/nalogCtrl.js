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
		
		$scope.getPopusti = function(stavka){
			var popusti = stavka.primenjeniPopusti;
			var ispis = "";
			
			for(var i = 0; i < popusti.length; i++){
				ispis += popusti[i].sifra + '\r\n';
			}
			
			return ispis;
		};
		
		$scope.checkRacun = function(racun){
			$scope.racun = racun;
			$scope.showSelectedRacun = true;
		};
		
		$scope.racuni = {};
		$scope.showSelectedRacun = false;
		$scope.korisnik = $rootScope.getCurrentUser();
		
	}]);
})(angular);