(function(angular) {
	var app = angular.module('app');
	app.controller('proizvodiCtrl', ['$scope', '$http', function($scope, $http) {
		var getArtikli = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/artikal/all'
			}).then(function successCallback(response) {
				$scope.artikli = response.data;
			  }, function errorCallback(response) {
				  console.log("Greska kod GET racuni");
			  });
		};
		getArtikli();
		
		var getKategorijeArtikla = function(){
			$http({
				  method: 'GET',
				  url: 'http://localhost:8080/SBZ/rest/services/kategorijeArtikala/all'
				}).then(function successCallback(response) {
					$scope.kategorijeArtikala = response.data;
				  }, function errorCallback(response) {
					  console.log("Greska kod GET racuni");
				  });
		};
		getKategorijeArtikla();
		
		$scope.addToKorpa = function(artikal, kolicina) {
			$http({
				  method: 'POST',
				  url: 'http://localhost:8080/SBZ/rest/services/korpa/add',
				  data: artikal,
				  params: {"kolicina":kolicina}
				}).then(function successCallback(response) {
				  }, function errorCallback(response) {
					  console.log("Greska kod addToKorpa");
				  });
		};
		
		var getAkcijskeDogadjaje = function() {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/akcija/all'
			}).then(function successCallback(response) {
				$scope.akcijskiDogadjaji = response.data;
				console.log($scope.akcijskiDogadjaji);
			  }, function errorCallback(response) {
				  console.log("Greska kod GET akcija");
			  });
		};
		getAkcijskeDogadjaje();
		
		/*
		 * VARIABLES
		 */
		
		$scope.naziv = "";
		$scope.sifra = "";
		$scope.kategorija = "";
		$scope.cenaOd = null;
		$scope.cenaDo = null;
	}]);
	
	app.filter('artikliFilter', function () {
		  return function (items, sifra, naziv, kategorija, cenaOd, cenaDo) {
			  	var filtered = [];
			  	
				if(items !== undefined){
					
				    for (var i = 0; i < items.length; i++) {
				    	var item = items[i];
				    	if(kategorija === undefined){
				    		kategorija = null;
				    	}

				    	var nazivBool = item.naziv.indexOf(naziv) > -1;
				    	var sifraBool = item.sifra.indexOf(sifra) > -1;
				    	var kategorijaBool = (kategorija!==null?item.kategorijaArtikla.naziv.indexOf(kategorija.naziv) > -1:true);
				    	var cenaOdBool = (cenaOd!=null?(cenaOd <= item.cena):true);
				    	var cenaDoBool = (cenaDo!=null?(cenaDo >= item.cena):true);
				    	
				        if(nazivBool && sifraBool && kategorijaBool && cenaOdBool && cenaDoBool){
				        	filtered.push(item);
				        }
				    }
				    return filtered;
		  		}
			  };
			});
})(angular);