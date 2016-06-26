(function(angular) {
	var app = angular.module('app');
	app.controller('kategorijeKupacaCtrl', ['$scope', '$http', 'validator', function($scope, $http, validator) {
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
			$scope.procenatKonverzije = kategorija.pragPotrosnje.procenatKonverzije;
		};
		
		$scope.sacuvajKategorijuKupca = function(kolicina) {
			if (!validator.inRange($scope.opsegOd, $scope.opsegDo)) {
				alert("Pogresan opseg");
				return;
			}
			if (!validator.inRangeProcenti($scope.procenatKonverzije)) {
				alert("Pogresan procenat");
				return;
			}
			var azuriranaKategorija = $scope.selectedKategorijaKupca;
			azuriranaKategorija.pragPotrosnje.opsegPotrosnjeOd = $scope.opsegOd;
			azuriranaKategorija.pragPotrosnje.opsegPotrosnjeDo = $scope.opsegDo;
			azuriranaKategorija.pragPotrosnje.procenatKonverzije = $scope.procenatKonverzije;
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