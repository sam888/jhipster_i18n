(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('KeyValueDetailController', KeyValueDetailController);

    KeyValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'KeyValue', 'ResourceBundle'];

    function KeyValueDetailController($scope, $rootScope, $stateParams, entity, KeyValue, ResourceBundle) {
        var vm = this;

        vm.keyValue = entity;

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:keyValueUpdate', function(event, result) {
            vm.keyValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
