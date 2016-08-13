(function() {
    'use strict';

    angular
        .module('pruebaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gama', {
            parent: 'entity',
            url: '/gama',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gamas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gama/gamas.html',
                    controller: 'GamaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gama-detail', {
            parent: 'entity',
            url: '/gama/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gama'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gama/gama-detail.html',
                    controller: 'GamaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gama', function($stateParams, Gama) {
                    return Gama.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gama',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gama-detail.edit', {
            parent: 'gama-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gama/gama-dialog.html',
                    controller: 'GamaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gama', function(Gama) {
                            return Gama.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gama.new', {
            parent: 'gama',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gama/gama-dialog.html',
                    controller: 'GamaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cliente: null,
                                tono: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gama', null, { reload: true });
                }, function() {
                    $state.go('gama');
                });
            }]
        })
        .state('gama.edit', {
            parent: 'gama',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gama/gama-dialog.html',
                    controller: 'GamaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gama', function(Gama) {
                            return Gama.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gama', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gama.delete', {
            parent: 'gama',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gama/gama-delete-dialog.html',
                    controller: 'GamaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gama', function(Gama) {
                            return Gama.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gama', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
