(function() {
    'use strict';

    angular.module('jhipsterI18NApp').factory('ResourceBundleUtil', ResourceBundleUtil);

    function ResourceBundleUtil() {

        var factory  = {};

        factory.printLocale = function( locale ) {
            if ( !locale ) return '';
            return 'Name = ' + locale.name + ', Language Code = ' + locale.languageCode + ', Country Code = ' + locale.countryCode;
        }

        return factory;
    }
})();

