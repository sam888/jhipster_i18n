(function() {
    'use strict';

    angular
        .module('jhipsterI18NApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('locale', {
            parent: 'entity',
            url: '/locale',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.locale.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/locale/locales.html',
                    controller: 'LocaleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('locale');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('locale-detail', {
            parent: 'entity',
            url: '/locale/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterI18NApp.locale.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/locale/locale-detail.html',
                    controller: 'LocaleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('locale');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Locale', function($stateParams, Locale) {
                    return Locale.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('locale.new', {
            parent: 'locale',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/locale/locale-dialog.html',
                    controller: 'LocaleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                languageCode: null,
                                countryCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('locale', null, { reload: true });
                }, function() {
                    $state.go('locale');
                });
            }]
        })
        .state('locale.edit', {
            parent: 'locale',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/locale/locale-dialog.html',
                    controller: 'LocaleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Locale', function(Locale) {
                            return Locale.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('locale', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('locale.delete', {
            parent: 'locale',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/locale/locale-delete-dialog.html',
                    controller: 'LocaleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Locale', function(Locale) {
                            return Locale.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('locale', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
