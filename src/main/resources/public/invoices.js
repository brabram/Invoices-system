var app = angular.module('Invoices', []);

app.controller('InvoicesController', function ($scope, $http, $window) {
    $http.get('http://localhost:8080/invoices').
        then(function (response) {
            $scope.Invoices = response.data;
            $scope.empty = $scope.Invoices.length;
        });
        $scope.remove = function(id){
            $http.delete('http://localhost:8080/invoices/' + id).
            then(function (response) {
                       $http.get('http://localhost:8080/invoices').
                               then(function (response) {
                                   $scope.Invoices = response.data;
                                   $scope.empty = $scope.Invoices.length;
                               });
                    });
        };

        $scope.get = function(id){
                    $http.get('http://localhost:8080/invoices/' + id).
                    then(function (response) {
                               $http.get('http://localhost:8080/invoices').
                                       then(function (response) {
                                           $scope.Invoices = response.data;
                                           $scope.empty = $scope.Invoices.length;
                                       });
                            });
                };

         $scope.pdf = function(id){
          $window.open('http://localhost:8080/invoices/pdf/' + id, '_blank');
    };
});





