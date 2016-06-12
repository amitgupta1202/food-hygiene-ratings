export default class HomeController {
    constructor(authorityService, loadingService) {
        authorityService.getAuthoritiesPromise().then((result) => {
            this.authorities = result;
        });
        this._loadingService = loadingService;
    }
    
    isLoading() {
        return this._loadingService.isLoading();
    }
}

HomeController.$inject = ['authorityService', 'loadingService'];
