import template from './rating-distribution.html';

export default function ratingDistribution() {
    return {
        restrict: 'E',
        scope: {
            authority: '='
        },
        template: template,
        link: (scope) => {
            let model = {
                ratingDistribution: null
            };
            scope.model = model;

            scope.$watch(() => scope.authority, (newAuthority) => {
                if (newAuthority) {
                    newAuthority.getRatingDistributionPromise().then((result) => {
                        model.ratingDistribution = result;
                    });
                }
            });
        }
    }
}




