(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDetailController', ResourceBundleDetailController);

    ResourceBundleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResourceBundle',
        'Locale', 'Module', 'ResourceBundleUtil', 'KeyValueSearchBy_RB_Id'];

    function ResourceBundleDetailController($scope, $rootScope, $stateParams, entity, ResourceBundle, Locale, Module,
                                            ResourceBundleUtil, KeyValueSearchBy_RB_Id) {
        var vm = this;

        vm.resourceBundle = entity;
        vm.keyValues = KeyValueSearchBy_RB_Id.query( {id : $stateParams.id} );

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:resourceBundleUpdate', function(event, result) {
            vm.resourceBundle = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.printLocale = function(locale) {
            return ResourceBundleUtil.printLocale(locale);
        }

        vm.printLocale = function(locale) {
            return ResourceBundleUtil.printLocale(locale);
        }
    }
})();
