(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('ModuleSearch', ModuleSearch);

    ModuleSearch.$inject = ['$resource'];

    function ModuleSearch($resource) {
        var resourceUrl =  'api/_search/modules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
