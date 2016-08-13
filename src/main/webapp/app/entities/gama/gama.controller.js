(function() {
    'use strict';

    angular
        .module('pruebaApp')
        .controller('GamaController', GamaController);

    GamaController.$inject = ['$scope', '$state', 'Gama'];

    function GamaController ($scope, $state, Gama) {
        var vm = this;
        
        vm.gamas = [];

        loadAll();

        function loadAll() {
            Gama.query(function(result) {
                vm.gamas = result;
            });
        }
    }
})();
