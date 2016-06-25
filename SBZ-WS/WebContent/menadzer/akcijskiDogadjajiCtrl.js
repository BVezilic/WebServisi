(function(angular) {
	var app = angular.module('app');
	app.controller('akcijskiDogadjajiCtrl', ['$scope', '$http', 'validator', function($scope, $http, validator) {
		$scope.azuriranjeKat = [];
		$scope.dodavanjeKat = [];
		
		var getAkcijskeDogadjaje = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/akcija/all'
			}).then(function successCallback(response) {
				$scope.akcijskiDogadjaji = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kod GET akcija");
			  });
		};
		getAkcijskeDogadjaje();
	
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
		
		$scope.selectAkcijskiDogadjaj = function(akcija) {			
			$scope.selectedAkcijskiDogadjaj = akcija;
			$scope.azuriranjeNaziv = akcija.naziv;
			$scope.azuriranjeOd = new Date(akcija.vaziOd);
			$scope.azuriranjeDo = new Date(akcija.vaziDo);
			$scope.azuriranjePopust = akcija.popustZaDogadjaj*100;
			//$scope.azuriranjeKat = akcija.kategorijaArtiklaSaPopustima;
		};
	
		$scope.dodajAkcijskiDogadjaj = function() {
			if (!validator.inRange($scope.dodavanjeOd, $scope.dodavanjeDo)) {
				alert("Datum zavrsetka akcije more biti posle datuma pocetka akcije!");
				return;
			}
			var akcija = {};
			akcija.sifra = $scope.dodavanjeSifra;
			akcija.naziv = $scope.dodavanjeNaziv;
			akcija.vaziOd = $scope.dodavanjeOd;
			akcija.vaziDo = $scope.dodavanjeDo;
			akcija.popustZaDogadjaj = $scope.dodavanjePopust/100;
			akcija.kategorijaArtiklaSaPopustima = $scope.dodavanjeKat;
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/akcija/add',
			  data: akcija
			}).then(function successCallback(response) {
				getAkcijskeDogadjaje();
				resetAddFields();
			  }, function errorCallback(response) {
				  console.log("Greska kog ADD akcije");
			  });
		};
	
		$scope.azurirajAkcijskiDogadjaj = function() {
			if (!validator.inRange($scope.azuriranjeOd, $scope.azuriranjeDo)) {
				alert("Datum zavrsetka akcije more biti posle datuma pocetka akcije!");
				return;
			}
			var azuriranaAkcija = $scope.selectedAkcijskiDogadjaj;
			azuriranaAkcija.naziv = $scope.azuriranjeNaziv;
			azuriranaAkcija.vaziOd = $scope.azuriranjeOd;
			azuriranaAkcija.vaziDo = $scope.azuriranjeDo;
			azuriranaAkcija.popustZaDogadjaj = $scope.azuriranjePopust/100;
			azuriranaAkcija.kategorijaArtiklaSaPopustima = $scope.azuriranjeKat;
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/SBZ/rest/services/akcija/update',
			  data: azuriranaAkcija
			}).then(function successCallback(response) {
				getAkcijskeDogadjaje();
				resetUpdateFields();
			  }, function errorCallback(response) {
				  console.log("Greska kog UPDATE akcije");
			  });
		};
		
		var resetUpdateFields = function() {
			$scope.azuriranjeNaziv = null;
			$scope.azuriranjeOd = null;
			$scope.azuriranjeDo = null;
			$scope.azuriranjePopust = null;
			$scope.azuriranjeKat = null;
		};
		
		var resetAddFields = function() {
			$scope.dodavanjeSifra = null;
			$scope.dodavanjeNaziv = null;
			$scope.dodavanjeOd = null;
			$scope.dodavanjeDo = null;
			$scope.dodavanjePopust = null;
			$scope.dodavanjeKat = null;
		};
	}]);
})(angular);