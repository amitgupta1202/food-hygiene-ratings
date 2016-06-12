export default ['$urlRouterProvider', '$locationProvider', routing];

function routing($urlRouterProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise('/');
}
