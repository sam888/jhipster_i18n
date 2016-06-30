(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ModuleDetailController', ModuleDetailController);

    ModuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Module'];

    function ModuleDetailController($scope, $rootScope, $stateParams, entity, Module) {
        var vm = this;

        vm.module = entity;

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:moduleUpdate', function(event, result) {
            vm.module = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
