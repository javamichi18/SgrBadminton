'use strict';

/* Controllers */

var badmintonModule = angular.module('Badminton.Common', []);

//badmintonModule.filter('date', function ($filter) {
//    return function (input) {
//        if (input == null) {
//            return "";
//        }
//
//        var _date = $filter('date')(new Date(input), 'dd-MM-yyyy');
//
//        return _date.toUpperCase();
//
//    };
//});

badmintonModule.controller('RanglisteCtrl', ['$scope', '$http', function ($scope, $http) {
    $http.get('rest/rangliste').success(function (data) {
        $scope.rangliste = data;
        $scope.userName = data.userName;
    });

    $scope.reihe = function (row) {

        var array = [];
        if ($scope.rangliste) {
            if (row == 1)
                array.push($scope.rangliste.spieler[0]);

            else if (row == 2) {
                array.push($scope.rangliste.spieler[1]);
                array.push($scope.rangliste.spieler[2]);

            }
            else if (row == 3) {
                array.push($scope.rangliste.spieler[3]);
                array.push($scope.rangliste.spieler[4]);
                array.push($scope.rangliste.spieler[5]);
            }
            else if (row == 4) {
                array.push($scope.rangliste.spieler[6]);
                array.push($scope.rangliste.spieler[7]);
                array.push($scope.rangliste.spieler[8]);
                array.push($scope.rangliste.spieler[9]);
            } else if (row == 5) {
                array.push($scope.rangliste.spieler[10]);
                array.push($scope.rangliste.spieler[11]);
                array.push($scope.rangliste.spieler[12]);
                array.push($scope.rangliste.spieler[13]);
                array.push($scope.rangliste.spieler[14]);
            }
            else if (row == 6) {
                array.push($scope.rangliste.spieler[15]);
                array.push($scope.rangliste.spieler[16]);
                array.push($scope.rangliste.spieler[17]);
                array.push($scope.rangliste.spieler[18]);
                array.push($scope.rangliste.spieler[19]);
                array.push($scope.rangliste.spieler[20]);
            }
        }

        return array;
    }


    $scope.kannFordern = function (rang) {
        return $scope.rangliste.kannFordern.indexOf(rang) >= 0;
    }

    $scope.istRangAktuellerSpieler = function (rang) {

        return rang != undefined && rang == $scope.rangliste.meinRang;
    }

    $scope.orderProp = 'rang';
}])
    .
    directive('spieler', function () {
        return {
            templateUrl: 'rangliste-spieler.html'
        }
    });


badmintonModule.controller('ForderungenCtrl', ['$scope', '$http', function ($scope, $http) {
    $http.get('rest/forderungen').success(function (data) {
        $scope.forderungen = data;
    });
}]);

badmintonModule.controller('SpieleCtrl', ['$scope', '$http', function ($scope, $http) {
    $http.get('rest/spiele').success(function (data) {
        $scope.spiele = data;
    });
}]);

badmintonModule.controller('ErgebnisCtrl', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {

    var spielId = $routeParams.spielId;

    $scope.master = {};

    $http.get('rest/spiel/' + spielId).success(function (data) {

        $scope.master = data;

        var spielServer = data;
        //spielServer.gespieltAm = new Date(2013, 9, 22);

        $scope.spiel = spielServer;

        $scope.input = spielServer.input;
        $scope.spieler1 = spielServer.spieler1;
        $scope.spieler2 = spielServer.spieler2;
    });

    $scope.speichern = function (spiel) {

        $scope.master = angular.copy(spiel);


        //$scope.reset = function () {
        //    // Example with 2 arguments
        //    angular.copy($scope.master, $scope.user);
        //};

        //$scope.reset();

        console.log('speichere spiel: ' + spiel.id);
        if ($scope.spiel.gespieltAm) {

            //var filterdatetime = $filter('date')($scope.spiel.gespieltAm);
            var filterdatetime = formatDate($scope.spiel.gespieltAm);
            console.log('date = ' + filterdatetime);
            spiel.gespieltAm = filterdatetime;
        }

        $http.post('rest/ergebnis', spiel)
            .success(function (data) {
                $scope.spiel = data;
                $scope.input = data.input;
                $scope.spieler1 = data.spieler1;
                $scope.spieler2 = data.spieler2;
            })
            .error(function (data, status, headers, config) {
                alert('uups: ' + status + " " + data);
                // TODO:

                //$log.error(data);
            })
        ;
    }

    $scope.nichtSpeicherbar = function () {
        return $scope.spiel.gespielt;
    }
}]);

badmintonModule.controller('UserCtrl', ['$scope', '$http', function ($scope, $http) {
    $http.get('rest/user').success(function (data) {
        $scope.user = data;
    });
}]);


badmintonModule.controller('ForderungCtrl', ['$scope', '$routeParams', '$http', MeldungService, function ($scope, $routeParams, $http, MeldungService) {

    var id = $routeParams.id;
    $http.get('rest/fordere/' + id)
        .success(function (data) {
            $scope.forderung = data;
            MeldungService.aktualisiereMeldungen('fordere', data.meldungen);
        })
        .error(function (data, status, headers, config) {
            xhrFehler('fordere', data.meldungen, status, headers);
        });

    $scope.kannSpielSpeichern = function () {
        return $scope.forderung.id != undefined;
    }

    $scope.meldungLoeschen = function (uuid) {
        loescheMeldung($scope.meldungen, uuid);
    }
}])
    .directive('meldung', function () {
        return {
            templateUrl: 'meldung.html'
        }
    });


function formatDate(isoDateString) {
    var newDate = new Date(isoDateString);
    return ('0' + newDate.getDate()).slice(-2) + "." + ('0' + (newDate.getMonth() + 1)).slice(-2) + "." + newDate.getFullYear();
}