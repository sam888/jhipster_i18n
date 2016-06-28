(function () {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
