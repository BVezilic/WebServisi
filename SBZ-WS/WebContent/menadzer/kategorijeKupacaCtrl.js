(function(angular) {
	var app = angular.module('app');
	app.controller('kategorijeKupacaCtrl', ['$scope', '$http', function($scope, $http) {
		var getKategorijeKupaca = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/kategorija/kupac/all'
			}).then(function successCallback(response) {
				$scope.kategorijaKupaca = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kod GET kategorijeKupaca");
			  });
		};
		getKategorijeKupaca();
		
		$scope.selectKategorijuKupca = function(kategorija) {
			$scope.selectedKategorijaKupca = kategorija;
			$scope.opsegOd = kategorija.pragPotrosnje.opsegPotrosnjeOd;
			$scope.opsegDo = kategorija.pragPotrosnje.opsegPotrosnjeDo;
		};
		
		$scope.sacuvajKategorijuKupca = function(kolicina) {
			var azuriranaKategorija = $scope.selectedKategorijaKupca;
			azuriranaKategorija.pragPotrosnje.opsegPotrosnjeOd = $scope.opsegOd;
			azuriranaKategorija.pragPotrosnje.opsegPotrosnjeDo = $scope.opsegDo;
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/kategorija/kupac/update',
			  data: azuriranaKategorija
			}).then(function successCallback(response) {
				getKategorijeKupaca();
			  }, function errorCallback(response) {
				  console.log("Greska kog UPDATE kategorijeKupaca");
			  });
		};
	}]);
})(angular);