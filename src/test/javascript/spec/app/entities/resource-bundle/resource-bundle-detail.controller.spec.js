'use strict';

describe('Controller Tests', function() {

    describe('ResourceBundle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockResourceBundle, MockLocale, MockModule;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockResourceBundle = jasmine.createSpy('MockResourceBundle');
            MockLocale = jasmine.createSpy('MockLocale');
            MockModule = jasmine.createSpy('MockModule');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ResourceBundle': MockResourceBundle,
                'Locale': MockLocale,
                'Module': MockModule
            };
            createController = function() {
                $injector.get('$controller')("ResourceBundleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterI18NApp:resourceBundleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
