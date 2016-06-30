(function() {
    'use strict';
    angular
        .module('jhipsterI18NApp')
        .factory('KeyValue', KeyValue);

    KeyValue.$inject = ['$resource'];

    function KeyValue ($resource) {
        var resourceUrl =  'api/key-values/:id';

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
