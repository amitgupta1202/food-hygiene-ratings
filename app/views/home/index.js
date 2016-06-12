import angular from 'angular';
import uirouter from 'angular-ui-router';

import routing from './home.routes';
import HomeController from './home.controller';
import services from '../../services';

export default angular.module('app.home', [uirouter, services])
    .config(routing)
    .controller('homeController', HomeController)
    .name;