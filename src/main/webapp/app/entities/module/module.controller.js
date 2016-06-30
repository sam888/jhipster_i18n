(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ModuleController', ModuleController);

    ModuleController.$inject = ['$scope', '$state', 'Module', 'ModuleSearch'];

    function ModuleController ($scope, $state, Module, ModuleSearch) {
        var vm = this;
        
        vm.modules = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Module.query(function(result) {
                vm.modules = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ModuleSearch.query({query: vm.searchQuery}, function(result) {
                vm.modules = result;
            });
        }    }
})();
