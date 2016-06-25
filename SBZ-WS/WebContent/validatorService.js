(function(angular) {
	var app = angular.module('app');
	app.service('validator', function() {
	    this.inRange = function(one, two) {
	        return two>one;
	    };
	});
})(angular);