(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('LocaleSearch', LocaleSearch);

    LocaleSearch.$inject = ['$resource'];

    function LocaleSearch($resource) {
        var resourceUrl =  'api/_search/locales/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
