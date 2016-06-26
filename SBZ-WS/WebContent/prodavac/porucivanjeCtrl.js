(function(angular) {
	var app = angular.module('app');
	app.controller('porucivanjeCtrl', ['PorucivanjeService', '$scope', '$http', function(PorucivanjeService, $scope, $http) {
		PorucivanjeService.getArtikli(function(data) {
			$scope.artikli = data;
		});

		$scope.selectArtikal = function(artikal) {
			$scope.selectedArtikal = artikal;
		};
		
		$scope.azurirajArtikal = function(kolicina) {
			PorucivanjeService.azurirajArtikal($scope.selectedArtikal, kolicina, function(){
  				$scope.kolicina = "";
  				PorucivanjeService.getArtikli(function(data) {
  					$scope.artikli = data;
  				});
			});
		};
	}]);
})(angular);