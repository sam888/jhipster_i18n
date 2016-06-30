(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('ResourceBundleController', ResourceBundleController);

    ResourceBundleController.$inject = ['$scope', '$state', 'ResourceBundle', 'ResourceBundleSearch'];

    function ResourceBundleController ($scope, $state, ResourceBundle, ResourceBundleSearch) {
        var vm = this;
        
        vm.resourceBundles = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ResourceBundle.query(function(result) {
                vm.resourceBundles = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResourceBundleSearch.query({query: vm.searchQuery}, function(result) {
                vm.resourceBundles = result;
            });
        }    }
})();
