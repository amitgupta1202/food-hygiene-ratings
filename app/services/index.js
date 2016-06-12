import angular from 'angular';

import LoadingService from './loading.service';
import AuthorityService from './authority.service';
import buildOperations from './link.service';

export default angular.module('app.services', [])
    .service('loadingService', LoadingService)
    .service('authorityService', AuthorityService)
    .factory('buildOperations', buildOperations)
    .name;