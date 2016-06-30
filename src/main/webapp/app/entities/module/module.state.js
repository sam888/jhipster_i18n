(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('module', {
            parent: 'entity',
            url: '/module',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.module.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module/modules.html',
                    controller: 'ModuleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('module');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('module-detail', {
            parent: 'entity',
            url: '/module/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.module.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module/module-detail.html',
                    controller: 'ModuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('module');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Module', function($stateParams, Module) {
                    return Module.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('module.new', {
            parent: 'module',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module/module-dialog.html',
                    controller: 'ModuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('module', null, { reload: true });
                }, function() {
                    $state.go('module');
                });
            }]
        })
        .state('module.edit', {
            parent: 'module',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module/module-dialog.html',
                    controller: 'ModuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Module', function(Module) {
                            return Module.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('module.delete', {
            parent: 'module',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module/module-delete-dialog.html',
                    controller: 'ModuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Module', function(Module) {
                            return Module.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
