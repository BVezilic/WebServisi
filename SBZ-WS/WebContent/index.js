(function(angular) {
  var app = angular.module('app',['ui.router', 'checklist-model', 'authentication', 'porucivanje']);
  app
  	.config(config)
  	.run(run);
		  
  function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/login');
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
        var publicStates = ['login', 'kupac', 'menadzer', 'prodavac'];
        var restrictedState = publicStates.indexOf(toState.name) === -1;
        
        console.log("ROLE: "+ $rootScope.getCurrentUserRole());
        console.log("toState: "+ toState.name);
        
        var checkKupac = toState.name == 'kupac' || toState.name == 'kupac.proizvodi' || toState.name == 'kupac.korpa' || toState.name == 'kupac.nalog';
        var checkMenadzer = toState.name == 'menadzer' || toState.name == 'menadzer.kategorijeKupaca' || toState.name == 'menadzer.kategorijeArtikala' || toState.name == 'menadzer.akcijskiDogadjaji';
        var checkProdavac = toState.name == 'prodavac' || toState.name == 'prodavac.porucivanje' || toState.name == 'prodavac.obradaRacuna';
        
        if(restrictedState && !AuthenticationService.getCurrentUser())
        {
        	$state.go('login');
        }
        else if($rootScope.getCurrentUserRole() != 'KUPAC' && checkKupac)
    	{
        	$state.go(fromState.name);
    	}
        else if($rootScope.getCurrentUserRole() != 'MENADZER' && checkMenadzer)
    	{
        	$state.go(fromState.name);
    	}
        else if($rootScope.getCurrentUserRole() != 'PRODAVAC' && checkProdavac)
    	{
        	$state.go(fromState.name);
    	}
        	
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
      
      $rootScope.getCurrentUser = function () {
          if (!AuthenticationService.getCurrentUser()){
            return undefined;
          }
          else{
            return AuthenticationService.getCurrentUser();
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
