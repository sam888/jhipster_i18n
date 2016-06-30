(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('resource-bundle', {
            parent: 'entity',
            url: '/resource-bundle',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.resourceBundle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resource-bundle/resource-bundles.html',
                    controller: 'ResourceBundleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resourceBundle');
                    $translatePartialLoader.addPart('resourceBundleStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('resource-bundle-detail', {
            parent: 'entity',
            url: '/resource-bundle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.resourceBundle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/resource-bundle/resource-bundle-detail.html',
                    controller: 'ResourceBundleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('resourceBundle');
                    $translatePartialLoader.addPart('resourceBundleStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ResourceBundle', function($stateParams, ResourceBundle) {
                    return ResourceBundle.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('resource-bundle.new', {
            parent: 'resource-bundle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resource-bundle/resource-bundle-dialog.html',
                    controller: 'ResourceBundleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                resourceBundleName: null,
                                description: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('resource-bundle', null, { reload: true });
                }, function() {
                    $state.go('resource-bundle');
                });
            }]
        })
        .state('resource-bundle.edit', {
            parent: 'resource-bundle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resource-bundle/resource-bundle-dialog.html',
                    controller: 'ResourceBundleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ResourceBundle', function(ResourceBundle) {
                            return ResourceBundle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('resource-bundle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('resource-bundle.delete', {
            parent: 'resource-bundle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/resource-bundle/resource-bundle-delete-dialog.html',
                    controller: 'ResourceBundleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ResourceBundle', function(ResourceBundle) {
                            return ResourceBundle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('resource-bundle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
