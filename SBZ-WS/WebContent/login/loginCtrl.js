(function(angular) {
	var app = angular.module('app');
	app.controller('loginCtrl', ['AuthenticationService', '$scope', function(AuthenticationService, $scope) {
		$scope.login = function(korisnickoIme, lozinka) {
			AuthenticationService.login(korisnickoIme, lozinka,
				function(uspeo) {
					console.log(uspeo);
				});
		};
	}]);
})(angular);