(function () {
    angular
        .module('obradaRacuna',[])
        .factory('ObradaRacunaService', Service);

    function Service($http) {
        var service = {};

        service.getRacuni = getRacuni;
        service.obradiRacun = obradiRacun;
        service.otkaziRacun = otkaziRacun;

        return service;

        function getRacuni(callback) {
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8080/SBZ/rest/services/racun/all'
  			}).then(function successCallback(response) {
  				callback(response.data);
  			  }, function errorCallback(response) {
  				  console.log("Greska kog GET racuni");
  			  });
        }

        function obradiRacun(racun, callback) {
        	$http({
  			  method: 'POST',
  			  url: 'http://localhost:8080/SBZ/rest/services/racun/apply',
  			  data: racun
  			}).then(function successCallback(response) {
  				callback(response.data);
  			  }, function errorCallback(response) {
  				  console.log("Greska kog APPLY racuna");
  			  });
        }
        
        function otkaziRacun(racun, callback) {
        	$http({
  			  method: 'POST',
  			  url: 'http://localhost:8080/SBZ/rest/services/racun/cancel',
  			  data: racun
  			}).then(function successCallback(response) {
  				callback(response.data);
  			  }, function errorCallback(response) {
  				  console.log("Greska kog CANCEL racuna");
  			  });
        }
    }
})();