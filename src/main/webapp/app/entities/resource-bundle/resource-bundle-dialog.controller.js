(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDialogController', ResourceBundleDialogController);

    ResourceBundleDialogController.$inject = ['$timeout', '$scope', '$rootScope','$stateParams', '$uibModalInstance', 'entity',
        'ResourceBundle', 'Locale', 'Module', 'KeyValue', 'ResourceBundleUtil', 'KeyValueSearchBy_RB_Id'];

    function ResourceBundleDialogController ($timeout, $scope, $rootScope, $stateParams, $uibModalInstance, entity, ResourceBundle,
        Locale, Module, KeyValue, ResourceBundleUtil, KeyValueSearchBy_RB_Id) {

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
            vm.submitted = true;
            vm.alertForResourceBundle = true;
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

        vm.printLocale = function(locale) {
            return ResourceBundleUtil.printLocale(locale);
        }


        // functions for removing Key Value table row below

        // remove 'key value' table row function
        vm.removeKeyValueRow = function( keyValue ) {
            var removeIndex = vm.keyValues.indexOf( keyValue );
            vm.keyValues.splice( removeIndex, 1);
        }

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:keyValueDeleted', function(event, keyValue) {
            vm.removeKeyValueRow( keyValue );
            keyValue.isEditing = false;
        });
        $scope.$on('$destroy', unsubscribe);


        // functions for creating new or updating existing Key Value below

        var onSaveKeyValueError = function () {
        };

        vm.saveKeyValue = function( keyValue ) {
            keyValue.saveBtnClicked = true;
            vm.alertForResourceBundle = false;
            if ( keyValue && keyValue.id !== null) {
                KeyValue.update( getCleanKeyValue( keyValue ), onSaveKeyValueSuccess(keyValue), onSaveKeyValueError);
            } else {
                KeyValue.save( getCleanKeyValue( keyValue ), onSaveKeyValueSuccess, onSaveKeyValueError);
            }
        };

        var onSaveKeyValueSuccess = function( keyValue ) {
            // see https://stackoverflow.com/questions/19116815/how-to-pass-a-value-to-an-angularjs-http-success-callback for
            // why a nested function is used. Basically to get around the problem that keyValue parameter can't be passed in
            // to AngularJS $resource success callback
            return function(result) {
                $scope.$emit('jhipsterI18NApp:keyValueUpdate', result);
                keyValue.isEditing = false;
                keyValue.id = result.id; // required if creating a new KeyValue
            }
        };

        // Create a deep copy of KeyValue then clean up unwanted properties for managing UI so its JSON data can be sent
        // to server for update/save
        var getCleanKeyValue = function( keyValue) {
            var keyValueClone = angular.copy( keyValue );
            delete keyValueClone.isEditing;
            delete keyValueClone.saveBtnClicked;
            keyValueClone.resourceBundle = vm.resourceBundle;
            return keyValueClone;
        }

        vm.backupKeyValue = function( keyValue ) {
            if ( !keyValue || !keyValue.id ) return;
            keyValue.property_backup = keyValue.property;
            keyValue.propertyValue_backup = keyValue.propertyValue;
            keyValue.description_backup = keyValue.description;
        }

        vm.restoreKeyValueBackup = function( keyValue ) {
            if ( !keyValue || !keyValue.id ) return;
            keyValue.property = keyValue.property_backup
            keyValue.propertyValue = keyValue.propertyValue_backup;
            keyValue.description = keyValue.description_backup;
        }

        vm.clearKeyValueCache = function( ) {
            ResourceBundleClearCache.clearCache( vm.resourceBundle );
        }
    }
})();
