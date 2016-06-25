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
		
		$scope.selectKategorijuArtikla = function(kategorija) {
			$scope.selectedKategorijuArtikla = kategorija;
			$scope.azuriranjeNaziv = kategorija.naziv;
			$scope.azuriranjeNadkategorija = kategorija.nadkategorija;
			$scope.azuriranjePopust = kategorija.maksimalniDozvoljeniPopust*100;
		};
		
		$scope.dodajKategorijuArtikla = function() {
			var kategorija = {};
			kategorija.sifraKategorije = $scope.dodavanjeSifra;
			kategorija.nadkategorija = $scope.dodavanjeNadkategorija;
			kategorija.naziv = $scope.dodavanjeNaziv;
			kategorija.maksimalniDozvoljeniPopust = $scope.dodavanjPopust/100;
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/kategorija/artikal/add',
			  data: kategorija
			}).then(function successCallback(response) {
				getKategorijeArtikala();
			  }, function errorCallback(response) {
				  console.log("Greska kog ADD kategorijeArtikla");
			  });
		}
		
		$scope.azurirajKategorijuArtikla = function(kolicina) {
			var azuriranaKategorija = $scope.selectedKategorijuArtikla;
			azuriranaKategorija.nadkategorija = $scope.azuriranjeNadkategorija;
			azuriranaKategorija.naziv = $scope.azuriranjeNaziv;
			azuriranaKategorija.maksimalniDozvoljeniPopust = $scope.azuriranjePopust/100;
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/kategorija/artikal/update',
			  data: azuriranaKategorija
			}).then(function successCallback(response) {
				getKategorijeArtikala();
			  }, function errorCallback(response) {
				  console.log("Greska kog UPDATE kategorijeKupaca");
			  });
		};
	}]);
})(angular);