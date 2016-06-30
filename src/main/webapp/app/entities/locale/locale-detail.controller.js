(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('LocaleDetailController', LocaleDetailController);

    LocaleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Locale'];

    function LocaleDetailController($scope, $rootScope, $stateParams, entity, Locale) {
        var vm = this;

        vm.locale = entity;

        var unsubscribe = $rootScope.$on('jhipsterI18NApp:localeUpdate', function(event, result) {
            vm.locale = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
