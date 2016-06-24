(function(angular) {
  var app = angular.module('app',['ui.router']);

  app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/login');
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
      templateUrl: 'prodavac/porucivanje1.html',
      controller: 'porucivanjeCtrl'
    })
    .state('prodavac.obradaRacuna', {
      url: '/obradaRacuna', 
      templateUrl: 'prodavac/obradaRacuna1.html',
      controller: 'obradaRacunaCtrl'
    })
    .state('menadzer', {
      url: '/menadzer', 
      templateUrl: 'menadzer/menadzer.html'
    })
    .state('menadzer.kategorijeKupaca', {
      url: '/kategorijeKupaca', 
      templateUrl: 'menadzer/kategorijeKupaca.html',
      controller: 'kategorijeKupacaCtrl'
    })
    .state('menadzer.kategorijeArtikala', {
      url: '/kategorijeArtikala', 
      templateUrl: 'menadzer/kategorijeArtikala.html',
      controller: 'kategorijeArtikalaCtrl'
    })
    .state('menadzer.akcijskiDogadjaji', {
      url: '/akcijskiDogadjaji', 
      templateUrl: 'menadzer/akcijskiDogadjaji.html',
      controller: 'akcijskiDogadjajiCtrl'
    })
    .state('login', {
      url: '/login', 
      templateUrl: 'login/login.html',
      controller: 'loginCtrl'
    });
  });
})(angular);
