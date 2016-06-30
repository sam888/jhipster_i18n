(function() {
    'use strict';
    angular
        .module('jhipsterI18NApp')
        .factory('ResourceBundle', ResourceBundle);

    ResourceBundle.$inject = ['$resource'];

    function ResourceBundle ($resource) {
        var resourceUrl =  'api/resource-bundles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
