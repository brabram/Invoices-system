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

         $scope.pdf = function(id){
          $window.open('http://localhost:8080/invoices/pdf/' + id, '_blank');
                };

//$scope.IsVisible = false;
//             $http.get('http://localhost:8080/invoices').
//            $scope.details = function (response) {
//                           $scope.IsVisible = $scope.IsVisible true;
//            }
});
//        app.controller('FindById', function ($scope, $http) {
//        $http.get('http://localhost:8080/invoices').
//        then(function(response) {
//            $scope.invoices = response.data;
//                   };
//                    });




