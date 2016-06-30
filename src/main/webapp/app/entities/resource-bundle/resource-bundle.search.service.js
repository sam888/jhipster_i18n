(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('ResourceBundleSearch', ResourceBundleSearch);

    ResourceBundleSearch.$inject = ['$resource'];

    function ResourceBundleSearch($resource) {
        var resourceUrl =  'api/_search/resource-bundles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
