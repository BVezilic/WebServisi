(function(angular) {
	var app = angular.module('app');
	app.controller('kategorijeArtikalaCtrl', ['$scope', '$http', function($scope, $http) {
		var getKategorijeArtikala = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/kategorija/artikal/all'
			}).then(function successCallback(response) {
				$scope.kategorijaArtikala = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kod GET kategorijeArtikala");
			  });
		};
		getKategorijeArtikala();
		
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