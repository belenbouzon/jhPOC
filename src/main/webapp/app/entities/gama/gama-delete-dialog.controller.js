(function() {
    'use strict';

    angular
        .module('pruebaApp')
        .controller('GamaDeleteController',GamaDeleteController);

    GamaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gama'];

    function GamaDeleteController($uibModalInstance, entity, Gama) {
        var vm = this;

        vm.gama = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gama.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
