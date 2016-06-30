'use strict';

describe('Controller Tests', function() {

    describe('KeyValue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKeyValue, MockResourceBundle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKeyValue = jasmine.createSpy('MockKeyValue');
            MockResourceBundle = jasmine.createSpy('MockResourceBundle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KeyValue': MockKeyValue,
                'ResourceBundle': MockResourceBundle
            };
            createController = function() {
                $injector.get('$controller')("KeyValueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterI18NApp:keyValueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
