(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('key-value', {
            parent: 'entity',
            url: '/key-value',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.keyValue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/key-value/key-values.html',
                    controller: 'KeyValueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keyValue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('key-value-detail', {
            parent: 'entity',
            url: '/key-value/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.keyValue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/key-value/key-value-detail.html',
                    controller: 'KeyValueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('keyValue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'KeyValue', function($stateParams, KeyValue) {
                    return KeyValue.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('key-value.new', {
            parent: 'key-value',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-value/key-value-dialog.html',
                    controller: 'KeyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                property: null,
                                propertyValue: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('key-value', null, { reload: true });
                }, function() {
                    $state.go('key-value');
                });
            }]
        })
        .state('key-value.edit', {
            parent: 'key-value',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-value/key-value-dialog.html',
                    controller: 'KeyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KeyValue', function(KeyValue) {
                            return KeyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('key-value', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('key-value.delete', {
            parent: 'key-value',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-value/key-value-delete-dialog.html',
                    controller: 'KeyValueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KeyValue', function(KeyValue) {
                            return KeyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('key-value', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
