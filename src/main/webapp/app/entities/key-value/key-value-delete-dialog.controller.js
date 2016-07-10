(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('KeyValueDeleteController',KeyValueDeleteController);

    KeyValueDeleteController.$inject = ['$scope','$uibModalInstance', 'entity', 'KeyValue'];

    function KeyValueDeleteController($scope, $uibModalInstance, entity, KeyValue) {
        var vm = this;

        vm.keyValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            KeyValue.delete({id: id},
                function () {
                    $scope.$emit('jhipsterI18NApp:keyValueDeleted', vm.keyValue);
                    $uibModalInstance.close(true);
                });
        }
    }
})();
