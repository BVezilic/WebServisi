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
		
		
	}]);
})(angular);