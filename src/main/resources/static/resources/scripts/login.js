var mainApp = angular.module('mainApp', ['ngCookies']),
	LoginCtrl = function ($scope, $window, $location, $http, $cookies) {

		/**
		 * Initialize
		 */
		(function () {

			/* Set remember user name */
			(function () {
				var rememberUserName = $cookies.get('rememberUserName') == 'true' ? true : false,
					userName = $cookies.get('userName'),
					password = $cookies.get('password');
				$scope.rememberUserName = rememberUserName;
				if (rememberUserName) {
					$scope.userName = userName;
					$scope.password = password;
				}
			})();

		})();

		/**
		 * Events
		 */
		(function () {

			/* button[name="login"] onclick */
			$('button[name="login"]').on('click', function () {
				var userName = $scope.userName,
					password = $scope.password,
					rememberUserName = $('input[name="rememberUserName"]').prop('checked');
				$http({
					'url': '/login',
					'method': 'post',
					'params': {
						'userName': userName,
						'password': password
					}
				}).then(function (response) {
					var data = response.data,
						code = data.loginMessageCode,
						message = data.message;
					if ('SUCCESS' == code) {
						if (rememberUserName) {
							$cookies.put('rememberUserName', 'true');
							$cookies.put('userName', userName);
						} else {
							$cookies.remove('rememberUserName');
							$cookies.remove('userName');
						}
						$window.location.href = '/';
					} else {
						bsAlert(message);
					}
				});
			});

		})();

	};
mainApp.controller('LoginCtrl', LoginCtrl);