(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDeleteController',ResourceBundleDeleteController);

    ResourceBundleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ResourceBundle'];

    function ResourceBundleDeleteController($uibModalInstance, entity, ResourceBundle) {
        var vm = this;

        vm.resourceBundle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ResourceBundle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
