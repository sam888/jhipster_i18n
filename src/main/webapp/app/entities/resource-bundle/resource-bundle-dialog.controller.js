(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDialogController', ResourceBundleDialogController);

    ResourceBundleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ResourceBundle', 'Locale', 'Module'];

    function ResourceBundleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResourceBundle, Locale, Module) {
        var vm = this;

        vm.resourceBundle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locales = Locale.query();
        vm.modules = Module.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.resourceBundle.id !== null) {
                ResourceBundle.update(vm.resourceBundle, onSaveSuccess, onSaveError);
            } else {
                ResourceBundle.save(vm.resourceBundle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterI18NApp:resourceBundleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
