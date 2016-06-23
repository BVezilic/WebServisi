(function(angular) {
  var app = angular.module('app',['ui.router']);

  app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/prodavac');
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
      templateUrl: 'kupac/nalog.html'
    })
    .state('kupac.korpa', {
      url: '/korpa',
      templateUrl: 'kupac/korpa.html'
    })
    .state('prodavac', {
        url: '/prodavac', 
        templateUrl: 'prodavac/prodavac.html'
      })
    .state('prodavac.porucivanje', {
      url: '/porucivanje', 
      templateUrl: 'prodavac/porucivanje.html'
    })
    .state('prodavac.obradaRacuna', {
      url: '/obradaRacuna', 
      templateUrl: 'prodavac/obradaRacuna.html'
    });
  });
})(angular);
