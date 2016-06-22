(function(angular) {
  var app = angular.module('app',['ui.router']);

  app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/kupac');
    $stateProvider
    .state('kupac', {//naziv stanja!
      url: '/kupac',
      templateUrl: 'kupac/kupac.html'
    })
    .state('kupac.proizvodi', {
      url: '/proizvodi', //url je #/main/search
      templateUrl: 'kupac/proizvodi.html'
    })
    .state('kupac.nalog', {
      url: '/nalog', 
      templateUrl: 'kupac/nalog1.html'
    })
    .state('kupac.korpa', {
      url: '/korpa',
      templateUrl: 'kupac/korpa.html'
    });
  });
})(angular);
