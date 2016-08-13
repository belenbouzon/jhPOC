(function() {
    'use strict';

    angular
        .module('pruebaApp')
        .controller('GamaDialogController', GamaDialogController);

    GamaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gama'];

    function GamaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gama) {
        var vm = this;

        vm.gama = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gama.id !== null) {
                Gama.update(vm.gama, onSaveSuccess, onSaveError);
            } else {
                Gama.save(vm.gama, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pruebaApp:gamaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
