(function(angular) {
	var app = angular.module('app');
	app.controller('loginCtrl', ['AuthenticationService', '$scope', function(AuthenticationService, $scope) {
		$scope.login = function(korisnickoIme, lozinka) {
			AuthenticationService.login(korisnickoIme, lozinka,
				function(uspeo) {
					console.log(uspeo);
				});
		};
		/*$scope.login = function(korisnickoIme, lozinka) {
			$http({
			  method: 'GET',
			  url: 'http://localhost:8080/SBZ/rest/services/login',
			  params: {"korisnickoIme":korisnickoIme, "lozinka":lozinka}
			}).then(function successCallback(response) {
				$rootScope.korisnik = response.data;
				switch ($rootScope.korisnik.ulogaKorisnika) {
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
		};*/
	}]);
})(angular);