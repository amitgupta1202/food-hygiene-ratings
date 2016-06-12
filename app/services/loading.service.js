export default class LoadingService {
    constructor($http) {
        this._http = $http;
    }

    isLoading() {
        return !!this._http.pendingRequests.length;
    }
}
LoadingService.$inject = ['$http'];