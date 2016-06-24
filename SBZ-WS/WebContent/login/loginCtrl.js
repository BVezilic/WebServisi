(function(angular) {
	var app = angular.module('app');
	app.controller('loginCtrl', ['$scope', '$http', '$state', function($scope, $http, $state) {
		$scope.login = function(korisnickoIme, lozinka) {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/login',
			  params: {"korisnickoIme":korisnickoIme, "lozinka":lozinka}
			}).then(function successCallback(response) {
				$scope.korisnik = response.data;
				switch ($scope.korisnik.ulogaKorisnika) {
				case "KUPAC":
					$state.go("kupac.proizvodi");
					break;
				case "PRODAVAC":
					$state.go("prodavac.porucivanje");
					break;
				case "MENADZER":
					$state.go("menadzer.kategorijeKupaca");
					break;
				}
			  }, function errorCallback(response) {
				  console.log("Greska kog login-a");
			  });
		};
	}]);
})(angular);