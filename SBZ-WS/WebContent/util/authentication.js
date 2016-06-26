(function () {
    angular
        .module('authentication',['ngStorage', 'ui.router', 'angular-jwt'])
        .factory('AuthenticationService', Service);

    function Service($http, $localStorage, $log, $state, jwtHelper) {
        var service = {};

        service.login = login;
        service.logout = logout;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(korisnickoIme, lozinka, callback) {
        	$http({
  			  method: 'GET',
  			  url: 'http://localhost:8080/SBZ/rest/services/login',
  			  params: {"korisnickoIme":korisnickoIme, "lozinka":lozinka}
  			}).then(function successCallback(response) {
                // ukoliko postoji token, prijava je uspecna
                if (response.data.token) {
                    // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu
                    var currentUser = response.data.user;
                    currentUser.token = response.data.token;
                    var tokenPayload = jwtHelper.decodeToken(response.data.token);
                    if(tokenPayload.role){
                        currentUser.role = tokenPayload.role;
                    }
                    // prijavljenog korisnika cuva u lokalnom skladistu
                    $localStorage.currentUser = currentUser;
                    // jwt token dodajemo u to auth header za sve $http zahteve
                    $http.defaults.headers.common.Authorization = response.data.token;
                    // callback za uspesan login
                    callback(true);
                    switch (currentUser.role) {
    				case "KUPAC":
    					$state.go("kupac.proizvodi");
    					break;
    				case "PRODAVAC":
    					$state.go("prodavac.porucivanje");
    					break;
    				case "MENADZER":
    					$state.go("menadzer.kategorijeKupaca");
    					break;
    				}
                }
            }, function errorCallback(response) {
            	callback(false);
              });
        }

        function logout() {
            // uklonimo korisnika iz lokalnog skladišta
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
            $http({
    			  method: 'POST',
    			  url: 'http://localhost:8080/SBZ/rest/services/korpa/clear',
    			  
    			});
            $state.go('login');
        }

        function getCurrentUser() {
            return $localStorage.currentUser;
        }
    }
})();