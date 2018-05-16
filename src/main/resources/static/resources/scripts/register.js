var mainApp = angular.module('mainApp', []),
	RegisterCtrl = function ($scope, $window, $location, $http) {

		/**
		 * Initialize
		 */
		(function () {

		})();

		/**
		 * Events
		 */
		(function () {

			/* button[name="register"] onclick */
			$('button[name="register"]').on('click', function () {
				$http({
					'url': '/api/auth/register',
					'method': 'post',
					'params': {
						'userName': $scope.userName,
						'email': $scope.email,
						'password': $scope.password,
						'confirmPassword': $scope.confirmPassword,
						'firstName': $scope.firstName,
						'lastName': $scope.lastName
					}
				}).then(function (response) {
					var data = response.data,
						code = data.registerMessageCode,
						message = data.message;
					bsAlert(message, function () {
						if ('SUCCESS' == code) {
							$window.location.href = '/login';
						} else if ('ERROR_EXISTS_USER_NAME' == code) {

						} else if ('ERROR_EXISTS_EMAIL' == code) {

						} else if ('ERROR_MISMATCH_CONFIRM_PASSWORD' == code) {

						}
					});
				}, function (response) {
					bsAlert('Error!');
				});
			});

		})();

	};
mainApp.controller('RegisterCtrl', RegisterCtrl);