/**
 * This a group module from all directives
 */

import angular from 'angular';

import ratingDistribution from './rating-distribution';

export default angular.module('app.directives', [ratingDistribution])
    .name;