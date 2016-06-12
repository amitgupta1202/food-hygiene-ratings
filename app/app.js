import 'bootstrap/dist/css/bootstrap.css';
import 'font-awesome/css/font-awesome.css';
import './css/app-center.css';

import angular from 'angular';
import uirouter from 'angular-ui-router';

import services from './services';
import directives from './directives';
import views from './views';

import routing from './app.config';

var app = angular.module('app', [
    uirouter,
    services,
    directives,
    views
]).config(routing);

