(function () {
    angular
        .module('porucivanje',[])
        .factory('PorucivanjeService', Service);

    function Service($http) {
        var service = {};

        service.getArtikli = getArtikli;
        service.azurirajArtikal = azurirajArtikal;

        return service;

        function getArtikli(callback) {
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8080/SBZ/rest/services/artikal/all'
  			}).then(function successCallback(response) {
  				callback(response.data);
  			  }, function errorCallback(response) {
  				  console.log("Greska kog GET artikli");
  			  });
        }

        function azurirajArtikal(artikal, kolicina, callback) {
        	$http({
  			  method: 'POST',
  			  url: 'http://localhost:8080/SBZ/rest/services/artikal/update',
  			  params: {"kolicina":kolicina},
  			  data: artikal
  			}).then(function successCallback(response) {
  				callback();
  			  }, function errorCallback(response) {
  				  console.log("Greska kog UPDATE artikal");
  			  });
        }
    }
})();