(function(angular) {
	var app = angular.module('app');
	app.service('validator', function() {
	    this.inRange = function(first, second) {
	        return second>first;
	    };
	    this.inRangeProcenti = function(procenat) {
	    	return (procenat >= 0 && procenat <= 100);
	    };
	});
})(angular);