import forEach from 'lodash/forEach';

export default class AuthorityService {
    constructor($http, buildOperations) {
        this._http = $http;
        this._buildOperations = buildOperations;
    }

    getAuthoritiesPromise() {
        return this._http.get('api/authorities').then((result) => {
            forEach(result.data.authorities, this._buildOperations);
            return result.data.authorities;
        });
    }

}
AuthorityService.$inject = ['$http', 'buildOperations'];