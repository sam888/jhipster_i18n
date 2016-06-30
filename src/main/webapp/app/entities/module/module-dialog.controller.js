(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ModuleDialogController', ModuleDialogController);

    ModuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Module'];

    function ModuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Module) {
        var vm = this;

        vm.module = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.module.id !== null) {
                Module.update(vm.module, onSaveSuccess, onSaveError);
            } else {
                Module.save(vm.module, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterI18NApp:moduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
