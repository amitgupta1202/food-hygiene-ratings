import angular from 'angular';

import services from '../../services';

import ratingDistribution from './rating-distribution.directive';

export default angular.module('app.directives.rating-distribution', [services])
    .directive('ratingDistribution', ratingDistribution)
    .name;