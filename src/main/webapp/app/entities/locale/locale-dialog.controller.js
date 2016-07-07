(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('LocaleDialogController', LocaleDialogController);

    LocaleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Locale'];

    function LocaleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Locale) {
        var vm = this;

        vm.locale = entity;
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
            vm.submitted = true;
            if (vm.locale.id !== null) {
                Locale.update(vm.locale, onSaveSuccess, onSaveError);
            } else {
                Locale.save(vm.locale, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterI18NApp:localeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            vm.submitted = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
