(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('KeyValueDialogController', KeyValueDialogController);

    KeyValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KeyValue', 'ResourceBundle'];

    function KeyValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KeyValue, ResourceBundle) {
        var vm = this;

        vm.keyValue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.resourcebundles = ResourceBundle.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.keyValue.id !== null) {
                KeyValue.update(vm.keyValue, onSaveSuccess, onSaveError);
            } else {
                KeyValue.save(vm.keyValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterI18NApp:keyValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
