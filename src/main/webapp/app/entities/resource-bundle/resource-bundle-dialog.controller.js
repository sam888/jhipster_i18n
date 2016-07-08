(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDialogController', ResourceBundleDialogController);

    ResourceBundleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
        'ResourceBundle', 'Locale', 'Module', 'KeyValueSearchBy_RB_Id'];

    function ResourceBundleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ResourceBundle,
        Locale, Module, KeyValueSearchBy_RB_Id) {

        var vm = this;

        vm.resourceBundle = entity;
        vm.clear = clear;
        vm.save = save;

        if ( vm.resourceBundle.id === null ) {
            vm.locales = Locale.query();
            vm.modules = Module.query();
        } else {
            vm.keyValues = KeyValueSearchBy_RB_Id.query( {id : $stateParams.id} );
        }

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

        // add an empty 'key value' table row
        vm.addKeyValueRow = function( ) {
            vm.keyValues.push( {} );
        }

        // remove a 'key value' table row
        vm.removeKeyValueRow = function( keyValue ) {
            var removeIndex = vm.keyValues.indexOf( keyValue );
            vm.keyValues.splice( removeIndex, 1);
        }
    }
})();
