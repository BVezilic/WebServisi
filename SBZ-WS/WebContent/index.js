(function(angular) {
  var app = angular.module('app',['ui.router', 'checklist-model', 'authentication']);
  app
  	.config(config)
  	.run(run);
		  
  function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
    $stateProvider
    .state('kupac', {//naziv stanja!
      url: '/kupac',
      templateUrl: 'kupac/kupac.html'
    })
    .state('kupac.proizvodi', {
      url: '/proizvodi', //url je #/main/search
      templateUrl: 'kupac/proizvodi.html',
      controller: 'proizvodiCtrl'
    })
    .state('kupac.nalog', {
      url: '/nalog', 
      templateUrl: 'kupac/nalog.html',
      controller: 'nalogCtrl'
    })
    .state('kupac.korpa', {
      url: '/korpa',
      templateUrl: 'kupac/korpa.html',
      controller: 'korpaCtrl'
    })
    .state('prodavac', {
        url: '/prodavac', 
        templateUrl: 'prodavac/prodavac.html'
      })
    .state('prodavac.porucivanje', {
      url: '/porucivanje', 
      templateUrl: 'prodavac/porucivanje.html',
      controller: 'porucivanjeCtrl'
    })
    .state('prodavac.obradaRacuna', {
      url: '/obradaRacuna', 
      templateUrl: 'prodavac/obradaRacuna.html',
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
  }
  function run($rootScope, $http, $location, $localStorage, AuthenticationService, $state) {
      // postavljanje tokena nakon refresh
      if ($localStorage.currentUser) {
          $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
      }

      // ukoliko pokušamo da odemo na stranicu za koju nemamo prava, redirektujemo se na login
      $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        // lista javnih stanja
        var publicStates = ['login'];
        var restrictedState = publicStates.indexOf(toState.name) === -1;
        
        console.log("ROLE: "+ $rootScope.getCurrentUserRole());
        console.log("toState: "+ toState.toString());
        
       /* if(restrictedState && !AuthenticationService.getCurrentUser()){
        	$state.go('login');
        }else if(!$rootScope.getCurrentUserRole() == 'KUPAC' && toState.toString() == 'kupac')
    	{
        	$state.go('login');
    	}*/
      });
      
      $rootScope.logout = function () {
          AuthenticationService.logout();
      }
      $rootScope.getCurrentUserRole = function () {
          if (!AuthenticationService.getCurrentUser()){
            return undefined;
          }
          else{
            return AuthenticationService.getCurrentUser().role;
          }
      }
      $rootScope.isLoggedIn = function () {
          if (AuthenticationService.getCurrentUser()){
            return true;
          }
          else{
            return false;
          }
      }
      $rootScope.getCurrentState = function () {
        return $state.current.name;
      }
  }
})(angular);
