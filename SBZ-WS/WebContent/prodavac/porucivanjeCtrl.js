(function(angular) {
	var app = angular.module('app');
	app.controller('porucivanjeCtrl', ['$scope', '$http', function($scope, $http) {
		var getArtikli = function(){
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/artikal/all'
			}).then(function successCallback(response) {
				$scope.artikli = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kog GET artikli");
			  });
		};
		getArtikli();
		 
		$scope.selectArtikal = function(artikal) {
			$scope.selectedArtikal = artikal;
		};
		
		$scope.azurirajArtikal = function(kolicina) {
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/artikal/update',
			  params: {"kolicina":kolicina},
			  data: $scope.selectedArtikal
			}).then(function successCallback(response) {
				$scope.kolicina = "";
				getArtikli();
			  }, function errorCallback(response) {
				  console.log("Greska kog UPDATE artikal");
			  });
		};
	}]);
})(angular);