import forEach from 'lodash/forEach';
import camelCase from 'lodash/camelCase';

export default function buildOperations($http) {
    return (object) => {
        forEach(object._link, (link, operation) => {
            if (operation !== 'self') {
                object[camelCase('get-' + operation + '-promise')] = () => {
                    return $http.get('api/' + link.href).then((result) => {
                        return result.data;
                    });
                };
            }
        });
    }
}
buildOperations.$inject = ['$http'];