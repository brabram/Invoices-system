var app = angular.module('Invoices', []);

app.controller('InvoicesController', function ($scope, $http, $window) {

    getAllInvoices()

    $scope.remove = function (id) {
        $http.delete(getBaseApiAddress() + id).then(function () {
            getAllInvoices()
        });
    };

    $scope.pdf = function (id) {
        $window.open(getBaseApiAddress() + 'pdf/' + id, '_blank');
    };

    function getAllInvoices()
    {
        $http.get(getBaseApiAddress()).then(function (response) {
            $scope.Invoices = response.data;
            $scope.empty = $scope.Invoices.length;
        });
    }
});

function getBaseApiAddress(){
    return 'http://localhost:8080/invoices/'
}
