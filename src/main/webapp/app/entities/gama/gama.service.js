(function() {
    'use strict';
    angular
        .module('pruebaApp')
        .factory('Gama', Gama);

    Gama.$inject = ['$resource'];

    function Gama ($resource) {
        var resourceUrl =  'api/gamas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
