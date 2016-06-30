(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .controller('KeyValueController', KeyValueController);

    KeyValueController.$inject = ['$scope', '$state', 'KeyValue', 'KeyValueSearch'];

    function KeyValueController ($scope, $state, KeyValue, KeyValueSearch) {
        var vm = this;
        
        vm.keyValues = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            KeyValue.query(function(result) {
                vm.keyValues = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            KeyValueSearch.query({query: vm.searchQuery}, function(result) {
                vm.keyValues = result;
            });
        }    }
})();
