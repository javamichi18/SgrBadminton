'use strict';

/* Services */

var badmintonModule = angular.module('Badminton.Common', ['ngResource']);



//badmintonModule.factory('Phone', ['$resource',
//  function($resource){
//    return $resource('phones/:phoneId.json', {}, {
//      query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
//    });
//  }]);


/*
 badmintonModule.service('MeldungService', function ($scope, $log) {

 this.aktualisiereMeldungen = function (service, meldungen) {

 $scope.meldungen = meldungen;
 if (meldungen.length > 0) {
 $log.info('XHR Error: ' + service, status);
 for (var i = 0; i < meldungen.length; i++) {
 var msg = meldungen[i];
 $log.info('Meldung: ' + msg.text + ' | ' + msg.type);
 }
 }
 }

 this.logError = function (service, meldungen, status, headers) {

 $scope.meldungen = meldungen;
 $log.error('XHR Error: ' + service, status);
 for (var i = 0; i < meldungen.length; i++) {
 var msg = meldungen[i];
 $log.warn('Meldung: ' + msg.text + ' | ' + msg.type);
 }
 for (var h = 0; h < headers.length; i++) {
 var header = headers[i];
 $log.info('HTTP Header: ' + header);
 }
 }
 });
 */

/*
 badmintonModule.factory('xhrFehler', ['$log', function ($log) {



 return function (service, meldungen, status, headers) {
 logError(service, meldungen, status, headers);
 }
 }]);
 */


badmintonModule.factory('MeldungService', ['$scope', '$log', function ($scope, $log) {

    var factory = {};

    function aktualisiereMeldungen(service, meldungen) {

        $scope.meldungen = meldungen;
        if (meldungen.length > 0) {
            $log.info('XHR Error: ' + service, status);
            for (var i = 0; i < meldungen.length; i++) {
                var msg = meldungen[i];
                $log.info('Meldung: ' + msg.text + ' | ' + msg.type);
            }
        }
    }

    return factory;
}]);


/*
 badmintonModule.factory('loescheMeldung', ['$scope', function ($scope) {

 function loescheMeldung(uuid) {

 var meldungen = $scope.meldungen;
 if (meldungen && uuid) {
 for (var index = 0; index < meldungen.length; index++) {
 var meldung = meldungen[index];
 if (meldung.uuid == uuid) {
 meldungen.splice(index, 1);
 return
 }
 }
 }
 }

 return function (uuid) {
 loescheMeldung(uuid);
 }
 }]);



 */