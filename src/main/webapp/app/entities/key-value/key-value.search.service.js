(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('KeyValueSearch', KeyValueSearch);

    KeyValueSearch.$inject = ['$resource'];

    function KeyValueSearch($resource) {
        var resourceUrl =  'api/_search/key-values/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
