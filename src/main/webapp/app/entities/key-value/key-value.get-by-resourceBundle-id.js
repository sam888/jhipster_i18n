(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('KeyValueSearchBy_RB_Id', KeyValueSearchBy_RB_Id);

    KeyValueSearchBy_RB_Id.$inject = ['$resource'];

    function KeyValueSearchBy_RB_Id( $resource ) {
        var resourceUrl =  'api/key-values/rb/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
