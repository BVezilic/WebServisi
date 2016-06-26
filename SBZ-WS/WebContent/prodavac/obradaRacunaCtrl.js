(function(angular) {
	var app = angular.module('app');
	app.controller('obradaRacunaCtrl', ['$scope', '$http', 'ObradaRacunaService', function($scope, $http, ObradaRacunaService) {
		ObradaRacunaService.getRacuni(function(data){
			$scope.racuni = data;
		});
		
		$scope.selectRacun = function(racun) {
			$scope.selectedRacun = racun;
		};
		
		$scope.obradiRacun = function() {
			ObradaRacunaService.obradiRacun($scope.selectedRacun, function(reply){
				ObradaRacunaService.getRacuni(function(data){
					$scope.racuni = data;
				});
  				if(!reply) {
  					alert("Neuspesno obradjen racun!");
  				}
			});
		};
		
		$scope.otkaziRacun = function() {
			ObradaRacunaService.otkaziRacun($scope.selectedRacun, function(reply) {
				ObradaRacunaService.getRacuni(function(data){
					$scope.racuni = data;
				});
			});
		};
	}]);
})(angular);