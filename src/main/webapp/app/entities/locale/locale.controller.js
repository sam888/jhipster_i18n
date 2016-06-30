(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('LocaleController', LocaleController);

    LocaleController.$inject = ['$scope', '$state', 'Locale', 'LocaleSearch'];

    function LocaleController ($scope, $state, Locale, LocaleSearch) {
        var vm = this;
        
        vm.locales = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Locale.query(function(result) {
                vm.locales = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LocaleSearch.query({query: vm.searchQuery}, function(result) {
                vm.locales = result;
            });
        }    }
})();
