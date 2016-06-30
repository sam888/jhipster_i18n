(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleDetailController', ResourceBundleDetailController);

    ResourceBundleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ResourceBundle', 'Locale', 'Module'];

    function ResourceBundleDetailController($scope, $rootScope, $stateParams, entity, ResourceBundle, Locale, Module) {
        var vm = this;

        vm.resourceBundle = entity;

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:resourceBundleUpdate', function(event, result) {
            vm.resourceBundle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
