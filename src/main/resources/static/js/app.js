'use strict';

/* App Module */

var badmintonApp = angular.module('badmintonApp', [
    'ngRoute',
    'Badminton.Common',
]);


badmintonApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'rangliste.html',
                controller: 'RanglisteCtrl',
            }).
            when('/rangliste', {
                templateUrl: 'rangliste.html',
                controller: 'RanglisteCtrl',
            }).
            when('/forderungen', {
                templateUrl: 'forderungen.html',
                controller: 'ForderungenCtrl',
            }).
            when('/forderung/:id', {
                templateUrl: 'forderung.html',
                controller: 'ForderungCtrl',
            }).
            when('/spiele', {
                templateUrl: 'spiele.html',
                controller: 'SpieleCtrl',
            }).
            when('/ergebnis/:spielId', {
                templateUrl: 'ergebnis.html',
                controller: 'ErgebnisCtrl',
            }).
            when('/user', {
                templateUrl: 'user.html',
                controller: 'UserCtrl',
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);


